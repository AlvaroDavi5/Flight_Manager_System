package com.notificationstreams;

import java.util.Arrays;
import java.util.Properties;
import java.util.HashMap;
import java.util.List;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.TimeWindowedKStream;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler;
import com.flightmanager.app.utils.ParserUtils;
import com.flightmanager.domain.enums.LogisticStatusEnum;
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

		ParserUtils parser = new ParserUtils();
		String reportsTopic = System.getenv("TOWER_REPORTS_TOPIC");
		String logisticTopic = System.getenv("FLIGHT_LOGISTIC_TOPIC");
		String notificationsTopic = System.getenv("FLIGHT_NOTIFICATIONS_TOPIC");
		String monitoringTopic = System.getenv("FLIGHT_MONITORING_TOPIC");

		StreamsBuilder builder = new StreamsBuilder();
		Duration windowDuration = Duration.ofMinutes(5);
		Duration windowAdvance = Duration.ofMinutes(1);

		KStream<String, String> logisticStream = builder.stream(logisticTopic); // stream sequence, as a log (stateless)
		logisticStream.peek((key, value) -> System.out.println("logisticStream - Key: " + key + ", Value: " + value));

		KStream<String, String> reportsStream = builder.stream(reportsTopic);
		reportsStream.peek((key, value) -> System.out.println("reportsStream - Key: " + key + ", Value: " + value));

		// ! ---------------- NOTIFICATIONS FLUX ----------------
		// joining reports and logistic streams
		KStream<String, String> joinedReportsAndLogisticStream = reportsStream.join(
				logisticStream,
				(reportValue, logisticValue) -> {
					HashMap<String, Object> reportMessage = parser.stringfiedJsonToHashMap(reportValue);
					HashMap<String, Object> logisticMessage = parser.stringfiedJsonToHashMap(logisticValue);

					logisticMessage.put("towerFlightStatus", (String) reportMessage.get("flightStatus"));
					logisticMessage.put("towerLogisticStatus", (String) reportMessage.get("logisticStatus"));

					return parser.hashMapToStringfiedJson(logisticMessage, false);
				},
				JoinWindows.ofTimeDifferenceWithNoGrace(windowDuration))
				.peek((key, value) -> System.out.println("joinedReportsAndLogisticStream - Key: " + key + ", Value: " + value));

		List<String> failEventsList = Arrays.asList(
				FlightStatusEnum.CANCELLED.toString(),
				LogisticStatusEnum.DIVERTED.toString(),
				LogisticStatusEnum.REJECTED_TO_LAND.toString(),
				LogisticStatusEnum.REJECTED_TO_TAKEOFF.toString());

		// filter the grouped stream based on the values in the message content
		KStream<String, String> filteredFailedEventsStream = joinedReportsAndLogisticStream
				.filter((String key, String value) -> {
					HashMap<String, Object> message = parser.stringfiedJsonToHashMap(value);
					return failEventsList.contains(message.get("logisticStatus").toString())
							|| failEventsList.contains(message.get("flightStatus").toString())
							|| failEventsList.contains(message.get("towerLogisticStatus").toString())
							|| failEventsList.contains(message.get("towerFlightStatus").toString());
				})
				.peek((key, value) -> System.out.println("filteredFailedEventsStream - Key: " + key + ", Value: " + value));

		filteredFailedEventsStream.to(notificationsTopic);

		// ! ---------------- MONITORING FLUX ----------------
		KGroupedStream<String, String> reportsGrouped = reportsStream
				.groupBy((key, value) -> key); // group by key
		// create a hopping window
		TimeWindowedKStream<String, String> reportsWindowedStream = reportsGrouped // last streams commits (statefull)
				.windowedBy(TimeWindows.ofSizeWithNoGrace(windowDuration) // group by time windows
						.advanceBy(windowAdvance)); // set window advance

		KTable<Windowed<String>, Long> towerReportsCountTable = reportsWindowedStream.count();
		KStream<String, Long> towerReportsCountStream = towerReportsCountTable
				.toStream()
				.map((key, value) -> new KeyValue<>(
						key.key() + "@" +  + key.window().start() + "|" + key.window().end(), value)) // remap with new key
				.peek((key, value) -> System.out.println("towerReportsCountStream - Key: " + key + ", Value: " + value));

		towerReportsCountStream.to(monitoringTopic, Produced.with(Serdes.String(), Serdes.Long()));

		final Topology topology = builder.build();
		final KafkaStreams streams = new KafkaStreams(topology, streamsProperties);
		final CountDownLatch latch = new CountDownLatch(1);

		System.out.println("\nTopology: \n" + topology.describe());

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
