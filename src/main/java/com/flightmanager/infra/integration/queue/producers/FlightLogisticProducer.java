package com.flightmanager.infra.integration.queue.producers;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class FlightLogisticProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String payload) {
		kafkaTemplate.send("flightLogistic", payload);
	}
}
