package com.controltower.infra.integration.queue.consumers;

import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.flightmanager.app.utils.ParserUtils;
import com.controltower.infra.integration.kafka.KafkaAdminClient;

public class FlightLogisticConsumer extends AbstractConsumer {
	public FlightLogisticConsumer(KafkaAdminClient kafkaClient) {
		super(kafkaClient, "FlightLogisticConsumerGroup", System.getenv("FLIGHT_LOGISTIC_TOPIC"));
	}

	@Override
	public void handleMessage(ConsumerRecord<String, String> record) {
		ParserUtils parser = new ParserUtils();

		HashMap<String, Object> value = parser.stringfiedJsonToHashMap(record.value());

		System.out.println(record.key() + "\n" + parser.hashMapToStringfiedJson(value, true));
	}
}
