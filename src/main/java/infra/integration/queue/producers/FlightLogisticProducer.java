package infra.integration.queue.producers;

import java.util.HashMap;
import org.apache.kafka.clients.producer.KafkaProducer;
import infra.integration.kafka.KafkaAdminClient;
import app.utils.ParserUtils;

public class FlightLogisticProducer {
	private String flightLogisticTopic;
	private KafkaAdminClient kafkaClient;
	private KafkaProducer<String, String> producer;

	public FlightLogisticProducer(KafkaAdminClient kafkaClient) {
		this.kafkaClient = kafkaClient;
		this.flightLogisticTopic = System.getenv("FLIGHT_LOGISTIC_TOPIC");

		this.producer = this.kafkaClient.createProducer(
				this.kafkaClient.getProducersProperties());
	}

	public KafkaProducer<String, String> getProducer() {
		return this.producer;
	}

	public Boolean sendMessage(String topicName, int partitionSize, String key, String value) {
		return this.kafkaClient.sendMessage(this.producer, topicName, partitionSize, key, value);
	}

	public Boolean sendMessage(int partitionSize, String key, HashMap<String, Object> value) {
		ParserUtils parser = new ParserUtils();
		return this.kafkaClient.sendMessage(this.producer, this.flightLogisticTopic, partitionSize, key,
				parser.hashMapToStringfiedJson(value, false));
	}
}
