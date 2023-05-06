package infra.integration.queue.consumers;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.kafka.KafkaAdminClient.ConsumerHandler;
import app.utils.ParserUtils;

public abstract class AbstractConsumer extends Thread {
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

	@Override
	public void run() {
		while (true) {
			ConsumerHandler handler = (record) -> this.handleMessage(record);
			this.kafkaClient.runPolling(this.consumer, handler);
		}
	}

	public void handleMessage(ConsumerRecord<String, String> record) {
		ParserUtils parser = new ParserUtils();

		String key = record.key();
		HashMap<String, Object> value = parser.stringfiedJsonToHashMap(record.value());

		System.out.println(
				"Message Key: " + key +
						"\nMessage Value: " + parser.hashMapToStringfiedJson(value, true));
	}
}
