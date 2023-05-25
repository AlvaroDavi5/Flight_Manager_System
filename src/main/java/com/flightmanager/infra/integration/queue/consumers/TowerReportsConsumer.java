package com.flightmanager.infra.integration.queue.consumers;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.flightmanager.app.services.FlightManagerService;

@Service
public class TowerReportsConsumer {
	@Autowired
	private FlightManagerService flightManagerService;

	@KafkaListener(id = "TowerReportsConsumer", topics = "towerReports", groupId = "TowerReportsConsumerGroup")
	public void consume(ConsumerRecord<String, String> msg) {
		this.flightManagerService.handleTowerReportMessage(msg.value());
	}
}
