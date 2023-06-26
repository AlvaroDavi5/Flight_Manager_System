package com.controltower.infra.integration.kafka;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.common.serialization.Serdes;

public class KafkaStreamsClient {
	private Properties streamsProperties;
	private String sourceTopic;
	private String targetTopic;
	private final StreamsBuilder builder;

	public interface StreamsHandlerInterface {
		KStream<String, String> handleStreams(KStream<String, String> sourceKStream);

		KTable<String, String> handleTables(KTable<String, String> sourceKTable);
	}

	public KafkaStreamsClient(String applicationId, String sourceTopic, String targetTopic) {
		String bootstrapServer = System.getenv("KAFKA_BOOTSTRAP_SERVER");
		bootstrapServer = (bootstrapServer == null) ? "localhost:9092" : bootstrapServer;

		this.streamsProperties = new Properties();
		streamsProperties.setProperty(
				StreamsConfig.APPLICATION_ID_CONFIG,
				applicationId);
		streamsProperties.setProperty(
				StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
				bootstrapServer);
		streamsProperties.setProperty(
				StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
				Serdes.class.getName());
		streamsProperties.setProperty(
				StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
				Serdes.class.getName());

		this.sourceTopic = sourceTopic;
		this.targetTopic = targetTopic;
		this.builder = new StreamsBuilder();
	}

	public StreamsBuilder getStreamsBuilder() {
		return this.builder;
	}

	public void start(StreamsHandlerInterface handler) {
		final KStream<String, String> sourceKStream = this.builder.stream(this.sourceTopic); // data stream, as a log
		final KTable<String, String> sourceKTable = this.builder.table(this.sourceTopic); // stream snapshot (last register)
		handler.handleStreams(sourceKStream).to(this.targetTopic);
		handler.handleTables(sourceKTable).toStream().to(this.targetTopic);

		final Topology topology = this.builder.build();
		final KafkaStreams streams = new KafkaStreams(topology, this.streamsProperties);
		final CountDownLatch latch = new CountDownLatch(1);

		Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
			@Override
			public void run() {
				streams.close();
				latch.countDown();
			}
		});

		try {
			streams.start();
			latch.await();
		} catch (Throwable throwable) {
			System.out.println("Error to start stream: " + throwable.getMessage());
		}
	}
}
