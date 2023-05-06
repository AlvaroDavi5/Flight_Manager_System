package app;

import java.util.Properties;
import org.apache.logging.log4j.Logger;
import infra.logging.AppLogger;
import app.services.FlightManagerService;
import interfaces.Router;
import infra.integration.kafka.KafkaAdminClient;
import infra.integration.queue.producers.FlightLogisticProducer;
import infra.integration.queue.producers.FlightNotificationsProducer;
import infra.integration.queue.consumers.TowerReportsConsumer;
import infra.integration.queue.consumers.AirTrafficConsumer;

public class App {
	private Router router;
	private Logger logger;
	private KafkaAdminClient kafkaClient;
	private TowerReportsConsumer towerReportsConsumer;
	private AirTrafficConsumer airTrafficConsumer;
	private FlightManagerService flightManagerService;

	public App(Properties producersProps, Properties consumerProps) {
		AppLogger logger = new AppLogger(this.getClass().getName());
		this.logger = logger.getLogger();

		this.router = new Router();
		this.kafkaClient = new KafkaAdminClient(producersProps, consumerProps);

		FlightLogisticProducer flightLogisticProducer = new FlightLogisticProducer(this.kafkaClient);
		FlightNotificationsProducer flightNotificationsProducer = new FlightNotificationsProducer(this.kafkaClient);
		this.flightManagerService = new FlightManagerService(flightLogisticProducer, flightNotificationsProducer);

		this.towerReportsConsumer = new TowerReportsConsumer(this.kafkaClient, this.flightManagerService);
		this.airTrafficConsumer = new AirTrafficConsumer(this.kafkaClient, this.flightManagerService);
	}

	public void start() {
		this.logger.info("App Started");
		this.router.start();
		this.startConsumers();
	}

	private void startConsumers() {
		this.towerReportsConsumer.start();
		this.airTrafficConsumer.start();
	}
}
