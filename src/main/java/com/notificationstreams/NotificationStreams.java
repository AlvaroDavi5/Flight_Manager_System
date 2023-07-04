package com.notificationstreams;

import java.util.Arrays;
import java.util.Properties;
import java.util.HashMap;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import com.flightmanager.app.utils.ParserUtils;
import com.flightmanager.domain.enums.FlightStatusEnum;

public class NotificationStreams {

	public static void main(String[] args) {
		String bootstrapServer = System.getenv("KAFKA_BOOTSTRAP_SERVER");
		bootstrapServer = (bootstrapServer == null) ? "localhost:9092" : bootstrapServer;

		Properties streamsProperties = new Properties();
		streamsProperties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "notificationStreams");
		streamsProperties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		streamsProperties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		streamsProperties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		streamsProperties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

		String logisticTopic = System.getenv("FLIGHT_LOGISTIC_TOPIC");
		String notificationsTopic = System.getenv("FLIGHT_NOTIFICATIONS_TOPIC");
		StreamsBuilder builder = new StreamsBuilder();

		Duration windowDuration = Duration.ofMinutes(5);
		Duration windowAdvance = Duration.ofMinutes(1);
		ParserUtils parser = new ParserUtils();

		/*
		 * implementado:
		 * agrupar por chave
		 * agrupar em janela temporal
		 * filtrar por conteudo (status de voo e status logistico)
		 * enviar para outro tópico
		 */

		KStream<String, String> logisticsKStream = builder.stream(logisticTopic); // data stream, as a log
		logisticsKStream.peek((key, value) -> System.out.println("logisticsKStream - Key: " + key + ", Value: " + value));

		KTable<Windowed<String>, String> windowedKTable = logisticsKStream
				.groupBy((key, value) -> key) // group by key
				.windowedBy(TimeWindows.ofSizeWithNoGrace(windowDuration).advanceBy(windowAdvance)) // group by time windows
				.reduce((value1, value2) -> value2);

		KStream<String, String> windowedKStream = windowedKTable.toStream()
				.map((key, value) -> new KeyValue<>(
						key.key() + "@" + key.window().start() + "-" + key.window().end(), // new key with the window interval
						value));
		windowedKStream.peek((key, value) -> System.out.println("windowedKStream - Key: " + key + ", Value: " + value));

		// filter the grouped stream based on the values in the message content
		KStream<String, String> filteredKStream = windowedKStream.filter((String key, String value) -> {
			HashMap<String, Object> message = parser.stringfiedJsonToHashMap(value);
			return Arrays
					.asList(FlightStatusEnum.CANCELLED.toString(), FlightStatusEnum.ARRIVED.toString(),
							FlightStatusEnum.DEPARTED.toString())
					.contains(message.get("flightStatus").toString());
		});
		filteredKStream.peek((key, value) -> System.out.println("filteredKStream - Key: " + key + ", Value: " + value));

		filteredKStream.to(notificationsTopic);

		KTable<String, String> notificationsKTable = builder.table(notificationsTopic); // stream snapshot

		KTable<String, Long> notificationsCount = notificationsKTable
				.groupBy((id, status) -> new KeyValue<>(id, status))
				.count();

		notificationsCount.toStream()
				.peek((key, value) -> System.out.println("Depois de virar ktable Key:valor " + key + ":" + value))
				.to("notificationsCount", Produced.with(Serdes.String(), Serdes.Long()));

		// saidas para teste
		KStream<String, Long> notificationsOutput = builder.stream("notificationsCount",
				Consumed.with(Serdes.String(), Serdes.Long()));
		notificationsOutput.peek((key, value) -> System.out.println("SAIDA Key:" + key + ":" + value));

		final Topology topology = builder.build();
		final KafkaStreams streams = new KafkaStreams(topology, streamsProperties);
		final CountDownLatch latch = new CountDownLatch(1);

		Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
			@Override
			public void run() {
				streams.close();
				latch.countDown();
			}
		});

		try {
			System.out.println("Starting...");
			streams.start();
			latch.await();
			System.out.println("Done.");
		} catch (Throwable throwable) {
			System.out.println("Error to start stream: " + throwable.getMessage());
		}

		streams.setUncaughtExceptionHandler(ex -> {
			System.out.println("Kafka-Streams uncaught exception occurred. Stream will be replaced with new thread" + ex);
			return StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse.REPLACE_THREAD;
		});
	}
}
