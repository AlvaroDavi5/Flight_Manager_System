package com.flightmanager.infra.integration.queue.producers;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;

@Component
public class FlightLogisticProducer {
	@Bean
	public ApplicationRunner sender(KafkaTemplate<String, String> template, String msg) {
		return args -> {
			template.send("flightLogistic", msg);
		};
	}
}
