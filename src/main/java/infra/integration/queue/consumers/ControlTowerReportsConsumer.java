package infra.integration.queue.consumers;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.kafka.KafkaAdminClient.ConsumerHandler;

public class ControlTowerReportsConsumer {
	private KafkaAdminClient kafkaClient;
	private KafkaConsumer<String, String> consumer;

	public ControlTowerReportsConsumer(KafkaAdminClient kafkaClient) {
		this.kafkaClient = kafkaClient;
		this.consumer = this.kafkaClient.createConsumer(
				this.kafkaClient.getConsumersProperties(),
				"ControlTowerReportsConsumerGroup");
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
