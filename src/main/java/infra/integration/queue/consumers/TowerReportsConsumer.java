package infra.integration.queue.consumers;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.kafka.KafkaAdminClient.ConsumerHandler;
import app.utils.ParserUtils;
import app.services.FlightManagerService;

public class TowerReportsConsumer {
	private String towerReportsTopic;
	private KafkaAdminClient kafkaClient;
	private KafkaConsumer<String, String> consumer;
	private FlightManagerService flightManagerService;

	public TowerReportsConsumer(KafkaAdminClient kafkaClient, FlightManagerService flightManagerService) {
		this.kafkaClient = kafkaClient;
		this.flightManagerService = flightManagerService;
		this.towerReportsTopic = System.getenv("TOWER_REPORTS_TOPIC");

		this.consumer = this.kafkaClient.createConsumer(
				this.kafkaClient.getConsumersProperties(),
				"TowerReportsConsumerGroup");
		this.kafkaClient.subscribe(this.consumer, this.towerReportsTopic);
	}

	public KafkaConsumer<String, String> getConsumer() {
		return this.consumer;
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

		this.flightManagerService.handleTowerReportMessage(value);
	}
}
