package com.flightmanager.infra.integration.queue.producers;

import com.flightmanager.infra.integration.kafka.KafkaAdminClient;

public class FlightLogisticProducer extends AbstractProducer {

	public FlightLogisticProducer(KafkaAdminClient kafkaClient) {
		super(kafkaClient, System.getenv("FLIGHT_LOGISTIC_TOPIC"));
	}
}
