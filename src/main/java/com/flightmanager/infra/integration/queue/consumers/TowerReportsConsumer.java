package com.flightmanager.infra.integration.queue.consumers;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.flightmanager.app.utils.ParserUtils;
import com.flightmanager.app.services.FlightManagerService;

@Component
public class TowerReportsConsumer {
	@Autowired
	private FlightManagerService flightManagerService;

	@KafkaListener(id = "TowerReportsConsumer", topics = "towerReports", groupId = "TowerReportsConsumerGroup")
	public void listen(String msg) {
		ParserUtils parser = new ParserUtils();

		HashMap<String, Object> value = parser.stringfiedJsonToHashMap(msg);

		this.flightManagerService.handleTowerReportMessage(value);
	}
}
