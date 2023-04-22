package app;

import java.util.Properties;
import org.slf4j.Logger;
import infra.logging.AppLogger;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.queue.producers.FlightLogisticProducer;
import infra.integration.queue.producers.FlightNotificationsProducer;
import infra.integration.queue.consumers.TowerReportsConsumer;
import infra.integration.queue.consumers.AirTrafficConsumer;
import app.services.FlightManagerService;

public class App {
	private KafkaAdminClient kafkaClient;
	private Logger logger;
	private TowerReportsConsumer towerReportsConsumer;
	private AirTrafficConsumer airTrafficConsumer;
	private FlightManagerService flightManagerService;

	public App(Properties producersProps, Properties consumerProps) {
		AppLogger logger = new AppLogger(this.getClass().getName());
		this.logger = logger.getLogger();

		this.kafkaClient = new KafkaAdminClient(producersProps, consumerProps);

		FlightLogisticProducer flightLogisticProducer = new FlightLogisticProducer(this.kafkaClient);
		FlightNotificationsProducer flightNotificationsProducer = new FlightNotificationsProducer(this.kafkaClient);

		this.flightManagerService = new FlightManagerService(flightLogisticProducer, flightNotificationsProducer);

		this.towerReportsConsumer = new TowerReportsConsumer(this.kafkaClient, this.flightManagerService);
		this.airTrafficConsumer = new AirTrafficConsumer(this.kafkaClient, this.flightManagerService);
	}

	public void start() {
		this.logger.info("App Started");
		this.startConsumers();
	}

	private void startConsumers() {
		while (true) {
			this.towerReportsConsumer.runPolling();
			this.airTrafficConsumer.runPolling();
		}
	}
}
