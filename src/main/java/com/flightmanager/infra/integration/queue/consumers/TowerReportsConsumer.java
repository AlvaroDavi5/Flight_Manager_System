package com.flightmanager.infra.integration.queue.consumers;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.flightmanager.infra.integration.kafka.KafkaAdminClient;
import com.flightmanager.app.utils.ParserUtils;
import com.flightmanager.app.services.FlightManagerService;

public class TowerReportsConsumer extends AbstractConsumer {
	private FlightManagerService flightManagerService;

	public TowerReportsConsumer(KafkaAdminClient kafkaClient, FlightManagerService flightManagerService) {
		super(kafkaClient, "TowerReportsConsumerGroup", System.getenv("TOWER_REPORTS_TOPIC"));
		this.flightManagerService = flightManagerService;
	}

	@Override
	public void handleMessage(ConsumerRecord<String, String> record) {
		ParserUtils parser = new ParserUtils();

		HashMap<String, Object> value = parser.stringfiedJsonToHashMap(record.value());

		this.flightManagerService.handleTowerReportMessage(value);
	}
}
