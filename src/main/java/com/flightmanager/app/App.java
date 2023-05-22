package com.flightmanager.app;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.boot.ApplicationRunner;
import org.springframework.kafka.core.KafkaTemplate;
import com.flightmanager.app.services.FlightManagerService;
import com.flightmanager.infra.integration.queue.producers.FlightLogisticProducer;
import com.flightmanager.infra.integration.queue.producers.FlightNotificationsProducer;
import com.flightmanager.infra.integration.queue.consumers.TowerReportsConsumer;
import com.flightmanager.infra.integration.queue.consumers.AirTrafficConsumer;

public class App {
	private FlightManagerService flightManagerService;

	public App(String[] args) {
		this.flightManagerService = new FlightManagerService(args);

		this.flightManagerService.setFlightLogisticProducer(new FlightLogisticProducer(null));
		this.flightManagerService.setFlightNotificationsProducer(new FlightNotificationsProducer(null));
		new TowerReportsConsumer(null, this.flightManagerService);
		new AirTrafficConsumer(null, this.flightManagerService);
	}

	@KafkaListener(id = "myId", topics = "topic1")
	public void listen(String in) {
		System.out.println(in);
	}

	@Bean
	public ApplicationRunner runner(KafkaTemplate<String, String> template) {
		return args -> {
			template.send("topic1", "test");
		};
	}
}
