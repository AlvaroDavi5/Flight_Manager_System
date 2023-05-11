package com.flightmanager.infra.integration.queue.producers;

import com.flightmanager.infra.integration.kafka.KafkaAdminClient;

public class FlightNotificationsProducer extends AbstractProducer {

	public FlightNotificationsProducer(KafkaAdminClient kafkaClient) {
		super(kafkaClient, System.getenv("FLIGHT_NOTIFICATIONS_TOPIC"));
	}
}