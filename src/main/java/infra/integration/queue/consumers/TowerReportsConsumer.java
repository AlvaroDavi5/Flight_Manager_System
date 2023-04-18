package infra.integration.queue.consumers;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.kafka.KafkaAdminClient.ConsumerHandler;

public class TowerReportsConsumer {
	private KafkaAdminClient kafkaClient;
	private KafkaConsumer<String, String> consumer;

	public TowerReportsConsumer(KafkaAdminClient kafkaClient) {
		this.kafkaClient = kafkaClient;
		this.consumer = this.kafkaClient.createConsumer(
				this.kafkaClient.getConsumersProperties(),
				"TowerReportsConsumerGroup");
	}

	public KafkaConsumer<String, String> getConsumer() {
		return this.consumer;
	}

	public void listenMessages(String topicName) {
		ConsumerHandler handler = (record) -> this.handleMessage(record);
		this.kafkaClient.listenMessages(this.consumer, topicName, handler);
	}

	private void handleMessage(ConsumerRecord<String, String> record) {
		System.out.println(record.key() + " | " + record.value());
	}
}
