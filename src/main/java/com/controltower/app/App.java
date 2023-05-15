package com.controltower.app;

import java.util.Properties;
import com.flightmanager.app.utils.FileUtils;
import com.flightmanager.infra.integration.kafka.KafkaAdminClient;
import com.flightmanager.infra.integration.queue.consumers.TowerReportsConsumer;
import com.flightmanager.infra.integration.queue.producers.FlightLogisticProducer;

public class App {
	private FileUtils utils = new FileUtils();
	private Properties producersProps;
	private Properties consumerProps;
	private KafkaAdminClient kafkaClient;
	private TowerReportsConsumer towerReportsConsumer;

	public App(String[] args) {
		try {
			this.producersProps = this.utils.readPropertiesFile("./src/main/resources/producer.properties");
			this.consumerProps = this.utils.readPropertiesFile("./src/main/resources/consumer.properties");
		} catch (Exception exception) {
			System.out.print("Read properties error, exiting...");
			exception.printStackTrace();
			System.exit(1);
		}

		this.kafkaClient = new KafkaAdminClient(this.producersProps, this.consumerProps);

		new FlightLogisticProducer(this.kafkaClient);
		new TowerReportsConsumer(this.kafkaClient, null);
	}

	public void start() {
		while (true) {
			// TODO - read stdin and generate flight to producer
			this.utils.getInput();

			// TODO - consume messages and display on stdout
			this.towerReportsConsumer.run();
		}
	}
}
