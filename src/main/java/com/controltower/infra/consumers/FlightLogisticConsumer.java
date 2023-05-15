package com.controltower.infra.consumers;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.flightmanager.app.utils.ParserUtils;
import com.flightmanager.infra.integration.kafka.KafkaAdminClient;
import com.flightmanager.infra.integration.queue.consumers.AbstractConsumer;

public class FlightLogisticConsumer extends AbstractConsumer {
	public FlightLogisticConsumer(KafkaAdminClient kafkaClient) {
		super(kafkaClient, "FlightLogisticConsumerGroup", System.getenv("TOWER_REPORTS_TOPIC"));
	}

	@Override
	public void handleMessage(ConsumerRecord<String, String> record) {
		ParserUtils parser = new ParserUtils();

		HashMap<String, Object> value = parser.stringfiedJsonToHashMap(record.value());

		System.out.println(record.key() + "\n" + parser.hashMapToStringfiedJson(value, true));
	}
}
