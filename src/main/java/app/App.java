package app;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import org.slf4j.Logger;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.queue.producers.NotificationsProducer;
import infra.integration.queue.consumers.TowerReportsConsumer;
import infra.integration.queue.consumers.TrackedFlightsConsumer;
import infra.logging.AppLogger;

public class App {
	private KafkaAdminClient kafkaClient;
	private Logger logger;
	private NotificationsProducer notificationsProducer;
	private TowerReportsConsumer towerReportsConsumer;
	private TrackedFlightsConsumer trackedFlightsConsumer;

	public App(Properties producersProps, Properties consumerProps) {
		this.kafkaClient = new KafkaAdminClient(producersProps, consumerProps);

		AppLogger logger = new AppLogger(this.getClass().getName());
		this.logger = logger.getLogger();

		this.notificationsProducer = new NotificationsProducer(this.kafkaClient);
		this.towerReportsConsumer = new TowerReportsConsumer(this.kafkaClient);
		this.trackedFlightsConsumer = new TrackedFlightsConsumer(this.kafkaClient);
	}

	public void start() {
		this.logger.info("App Started");

		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("v1", 555);
		message.put("v2", "test");
		String msgKey = "msg#" + new Date().getTime();

		this.notificationsProducer.sendMessage(
				"clientSubscriptions", 2,
				msgKey, message);
		this.towerReportsConsumer.subscribe("towerReports");
		this.trackedFlightsConsumer.subscribe("trackedFlights");
		this.startConsumers();
	}

	private void startConsumers() {
		while (true) {
			this.towerReportsConsumer.runPolling();
			this.trackedFlightsConsumer.runPolling();
		}
	}
}
