package com.controltower.infra.integration.stream;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import com.controltower.infra.integration.kafka.KafkaStreamsClient;
import com.controltower.infra.integration.kafka.KafkaStreamsClient.StreamsHandlerInterface;

public abstract class StreamsHandler implements StreamsHandlerInterface {
	private KafkaStreamsClient kafkaClient;

	public StreamsHandler(String sourceTopic, String targetTopic) {
		this.kafkaClient = new KafkaStreamsClient(
				this.getClass().getName(),
				sourceTopic, targetTopic);
	}

	public void start() {
		StreamsHandler handler = this;
		this.kafkaClient.start(handler);
	}

	public KStream<String, String> handleStreams(KStream<String, String> sourceKStream) {

		// sourceKStream.flatMapValues((key, value) ->
		// Arrays.asList(value.split("\\W+")));

		return sourceKStream;
	}

	public KTable<String, String> handleTables(KTable<String, String> sourceKTable) {

		// sourceKTable.filter((key, value) -> value.contains("status"));

		return sourceKTable;
	}
}
