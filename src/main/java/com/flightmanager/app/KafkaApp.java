package com.flightmanager.app;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.apache.kafka.clients.admin.NewTopic;
import com.flightmanager.infra.logging.AppLogger;

@Component
public class KafkaApp {
	String reportsTopic = System.getenv("TOWER_REPORTS_TOPIC");
	String logisticTopic = System.getenv("FLIGHT_LOGISTIC_TOPIC");
	String notificationsTopic = System.getenv("FLIGHT_NOTIFICATIONS_TOPIC");
	String monitoringTopic = System.getenv("FLIGHT_MONITORING_TOPIC");

	@Bean
	public RecordMessageConverter converter() {
		return new JsonMessageConverter();
	}

	@Bean
	public NewTopic createTowerReportsTopic() {
		return new NewTopic(this.reportsTopic, 3, (short) 1);
	}

	@Bean
	public NewTopic createFlightLogisticTopic() {
		return new NewTopic(this.logisticTopic, 3, (short) 1);
	}

	@Bean
	public NewTopic createFlightNotificationTopic() {
		return new NewTopic(this.notificationsTopic, 3, (short) 1);
	}

	@Bean
	public NewTopic createFlightMonitoringTopic() {
		return new NewTopic(this.monitoringTopic, 3, (short) 1);
	}

	public void start() {
		AppLogger logger = new AppLogger(this.getClass().getName());
		logger.getLogger().info("Kafka App Started");
	}
}
