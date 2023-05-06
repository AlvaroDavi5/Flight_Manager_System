package infra.integration.queue.consumers;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.kafka.KafkaAdminClient.ConsumerHandler;
import app.utils.ParserUtils;
import app.services.FlightManagerService;

public class AirTrafficConsumer extends Thread {
	private String airTrafficTopic;
	private KafkaAdminClient kafkaClient;
	private KafkaConsumer<String, String> consumer;
	private FlightManagerService flightManagerService;

	public AirTrafficConsumer(KafkaAdminClient kafkaClient, FlightManagerService flightManagerService) {
		this.kafkaClient = kafkaClient;
		this.flightManagerService = flightManagerService;
		this.airTrafficTopic = System.getenv("AIR_TRAFFIC_TOPIC");

		this.consumer = this.kafkaClient.createConsumer(
				this.kafkaClient.getConsumersProperties(),
				"AirTrafficConsumerGroup");
		this.kafkaClient.subscribe(this.consumer, this.airTrafficTopic);
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

	private void handleMessage(ConsumerRecord<String, String> record) {
		ParserUtils parser = new ParserUtils();

		String key = record.key();
		HashMap<String, Object> value = parser.stringfiedJsonToHashMap(record.value());

		System.out.println(
				"Message Key: " + key +
						"\nMessage Value: " + parser.hashMapToStringfiedJson(value, true));

		this.flightManagerService.handleAirTrafficMessage(value);
	}
}
