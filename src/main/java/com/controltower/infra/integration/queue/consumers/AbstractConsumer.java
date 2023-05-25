package com.controltower.infra.integration.queue.consumers;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.controltower.infra.integration.kafka.KafkaAdminClient;
import com.controltower.infra.integration.kafka.KafkaAdminClient.ConsumerHandler;

public abstract class AbstractConsumer extends Thread {
	private String topicName;
	private KafkaAdminClient kafkaClient;
	private KafkaConsumer<String, String> consumer;

	public AbstractConsumer(KafkaAdminClient kafkaClient, String consumerGroup, String topicName) {
		this.kafkaClient = kafkaClient;
		this.topicName = topicName;
		this.consumer = this.kafkaClient.createConsumer(
				this.kafkaClient.getConsumersProperties(),
				consumerGroup);
		this.kafkaClient.subscribe(this.consumer, this.topicName);
	}

	@Override
	public void run() {
		while (true) {
			ConsumerHandler handler = (record) -> this.handleMessage(record);
			this.kafkaClient.runPolling(this.consumer, handler);
		}
	}

	public KafkaConsumer<String, String> getConsumer() {
		return this.consumer;
	}

	public void handleMessage(ConsumerRecord<String, String> record) {
	}
}
