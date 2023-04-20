package infra.integration.queue.producers;

import java.util.HashMap;
import org.apache.kafka.clients.producer.KafkaProducer;
import infra.integration.kafka.KafkaAdminClient;
import app.utils.ParserUtils;

public class NotificationsProducer {
	private KafkaAdminClient kafkaClient;
	private KafkaProducer<String, String> producer;

	public NotificationsProducer(KafkaAdminClient kafkaClient) {
		this.kafkaClient = kafkaClient;
		this.producer = this.kafkaClient.createProducer(
				this.kafkaClient.getProducersProperties());
	}

	public KafkaProducer<String, String> getProducer() {
		return this.producer;
	}

	public Boolean sendMessage(String topicName, int partitionSize, String key, String value) {
		return this.kafkaClient.sendMessage(this.producer, topicName, partitionSize, key, value);
	}

	public Boolean sendMessage(String topicName, int partitionSize, String key, HashMap<String, Object> value) {
		ParserUtils parser = new ParserUtils();
		return this.kafkaClient.sendMessage(this.producer, topicName, partitionSize, key,
				parser.hashMapToStringfiedJson(value, false));
	}
}
