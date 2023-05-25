package com.flightmanager.infra.integration.queue.producers;

import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;

@Component
public class FlightLogisticProducer {
	public ApplicationRunner sender(String msg) {
		KafkaTemplate<Object, Object> template = new KafkaTemplate<Object, Object>(null, false);
		return args -> {
			template.send("flightLogistic", msg);
		};
	}
}
