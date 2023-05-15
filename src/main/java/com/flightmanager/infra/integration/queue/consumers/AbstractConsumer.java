package com.flightmanager.infra.integration.queue.consumers;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.flightmanager.infra.integration.kafka.KafkaAdminClient;
import com.flightmanager.infra.integration.kafka.KafkaAdminClient.ConsumerHandler;

public abstract class AbstractConsumer {
	private KafkaAdminClient kafkaClient;
	private KafkaConsumer<String, String> consumer;

	public AbstractConsumer(KafkaAdminClient kafkaClient, String consumerGroup, String topicName) {
		this.kafkaClient = kafkaClient;
		this.consumer = this.kafkaClient.createConsumer(
				this.kafkaClient.getConsumersProperties(),
				consumerGroup);
		this.kafkaClient.subscribe(this.consumer, topicName);
	}

	public KafkaConsumer<String, String> getConsumer() {
		return this.consumer;
	}

	public void run() {
		ConsumerHandler handler = (record) -> this.handleMessage(record);
		this.kafkaClient.runPolling(this.consumer, handler);
	}

	public void handleMessage(ConsumerRecord<String, String> record) {
	}
}
