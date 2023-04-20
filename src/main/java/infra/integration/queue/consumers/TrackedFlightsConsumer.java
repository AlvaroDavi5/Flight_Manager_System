package infra.integration.queue.consumers;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.kafka.KafkaAdminClient.ConsumerHandler;
import app.utils.ParserUtils;

public class TrackedFlightsConsumer {
	private KafkaAdminClient kafkaClient;
	private KafkaConsumer<String, String> consumer;

	public TrackedFlightsConsumer(KafkaAdminClient kafkaClient) {
		this.kafkaClient = kafkaClient;
		this.consumer = this.kafkaClient.createConsumer(
				this.kafkaClient.getConsumersProperties(),
				"TrackedFlightsConsumerGroup");
	}

	public KafkaConsumer<String, String> getConsumer() {
		return this.consumer;
	}

	public void subscribe(String topicName) {
		this.kafkaClient.subscribe(this.consumer, topicName);
		;
	}

	public void runPolling() {
		ConsumerHandler handler = (record) -> this.handleMessage(record);
		this.kafkaClient.runPolling(this.consumer, handler);
	}

	private void handleMessage(ConsumerRecord<String, String> record) {
		ParserUtils parser = new ParserUtils();

		String key = record.key();
		HashMap<String, Object> value = parser.stringfiedJsonToHashMap(record.value());

		System.out.println(
				"Message Key: " + key +
						"\nMessage Value: " + parser.hashMapToStringfiedJson(value, true));
	}
}
