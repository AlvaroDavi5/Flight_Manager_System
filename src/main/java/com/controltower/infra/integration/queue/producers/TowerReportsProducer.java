package com.controltower.infra.integration.queue.producers;

import com.controltower.infra.integration.kafka.KafkaAdminClient;

public class TowerReportsProducer extends AbstractProducer {

	public TowerReportsProducer(KafkaAdminClient kafkaClient) {
		super(kafkaClient, System.getenv("TOWER_REPORTS_TOPIC"));
	}
}
