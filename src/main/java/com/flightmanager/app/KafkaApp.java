package com.flightmanager.app;

import java.util.Properties;
import com.flightmanager.app.utils.FileUtils;
import com.flightmanager.app.services.FlightManagerService;
import com.flightmanager.infra.integration.kafka.KafkaAdminClient;
import com.flightmanager.infra.integration.queue.producers.FlightLogisticProducer;
import com.flightmanager.infra.integration.queue.producers.FlightNotificationsProducer;
import com.flightmanager.infra.integration.queue.consumers.TowerReportsConsumer;
import com.flightmanager.infra.integration.queue.consumers.AirTrafficConsumer;

public class KafkaApp extends Thread {
	private Properties producersProps;
	private Properties consumerProps;
	private KafkaAdminClient kafkaClient;
	private TowerReportsConsumer towerReportsConsumer;
	private AirTrafficConsumer airTrafficConsumer;
	private FlightManagerService flightManagerService;

	public KafkaApp() {
		FileUtils utils = new FileUtils();
		try {
			this.producersProps = utils.readPropertiesFile("./src/main/resources/producer.properties");
			this.consumerProps = utils.readPropertiesFile("./src/main/resources/consumer.properties");
		} catch (Exception exception) {
			System.out.print("Read properties error, exiting...");
			exception.printStackTrace();
			System.exit(1);
		}

		this.kafkaClient = new KafkaAdminClient(this.producersProps, this.consumerProps);

		FlightLogisticProducer flightLogisticProducer = new FlightLogisticProducer(this.kafkaClient);
		FlightNotificationsProducer flightNotificationsProducer = new FlightNotificationsProducer(this.kafkaClient);
		this.flightManagerService = new FlightManagerService(flightLogisticProducer, flightNotificationsProducer);

		this.towerReportsConsumer = new TowerReportsConsumer(this.kafkaClient, this.flightManagerService);
		this.airTrafficConsumer = new AirTrafficConsumer(this.kafkaClient, this.flightManagerService);
	}

	@Override
	public void run() {
		this.flightManagerService.start();
		this.startConsumers();
	}

	private void startConsumers() {
		while (true) {
			this.towerReportsConsumer.run();
			this.airTrafficConsumer.run();
		}
	}
}
