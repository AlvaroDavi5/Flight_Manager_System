package com.controltower.infra.producers;

import com.flightmanager.infra.integration.kafka.KafkaAdminClient;
import com.flightmanager.infra.integration.queue.producers.AbstractProducer;

public class TowerReportsProducer extends AbstractProducer {

	public TowerReportsProducer(KafkaAdminClient kafkaClient) {
		super(kafkaClient, System.getenv("TOWER_REPORTS_TOPIC"));
	}
}
