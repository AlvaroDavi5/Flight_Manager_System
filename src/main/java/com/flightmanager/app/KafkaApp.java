package com.flightmanager.app;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.apache.kafka.clients.admin.NewTopic;
import com.flightmanager.infra.logging.AppLogger;

@Component
public class KafkaApp {
	@Bean
	public RecordMessageConverter converter() {
		return new JsonMessageConverter();
	}

	@Bean
	private NewTopic createTowerReportsTopic() {
		return new NewTopic("towerReports", 3, (short) 1);
	}

	@Bean
	private NewTopic createFlightLogisticTopic() {
		return new NewTopic("flightLogistic", 3, (short) 1);
	}

	@Bean
	private NewTopic createFlightNotificationTopic() {
		return new NewTopic("flightNotification", 3, (short) 1);
	}

	public void start() {
		AppLogger logger = new AppLogger(this.getClass().getName());
		logger.getLogger().info("Kafka App Started");
	}
}
