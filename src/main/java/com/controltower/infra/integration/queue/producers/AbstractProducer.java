package com.controltower.infra.integration.queue.producers;

import java.util.HashMap;
import org.apache.kafka.clients.producer.KafkaProducer;
import com.controltower.infra.integration.kafka.KafkaAdminClient;
import com.flightmanager.app.utils.ParserUtils;

public abstract class AbstractProducer {
	private String topicName;
	private KafkaAdminClient kafkaClient;
	private KafkaProducer<String, String> producer;

	public AbstractProducer(KafkaAdminClient kafkaClient, String topicName) {
		this.kafkaClient = kafkaClient;
		this.topicName = topicName;
		this.producer = this.kafkaClient.createProducer(
				this.kafkaClient.getProducersProperties());
	}

	public KafkaProducer<String, String> getProducer() {
		return this.producer;
	}

	public Boolean sendMessage(Integer partitionSize, String key, String value) {
		return this.kafkaClient.sendMessage(this.producer, this.topicName, partitionSize, key, value);
	}

	// @Overload
	public Boolean sendMessage(Integer partitionSize, String key, HashMap<String, Object> value) {
		ParserUtils parser = new ParserUtils();
		return this.kafkaClient.sendMessage(this.producer, this.topicName, partitionSize, key,
				parser.hashMapToStringfiedJson(value, false));
	}
}
