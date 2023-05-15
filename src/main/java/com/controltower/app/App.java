package com.controltower.app;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Properties;
import com.flightmanager.app.utils.FileUtils;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.infra.integration.kafka.KafkaAdminClient;
import com.controltower.infra.producers.TowerReportsProducer;
import com.controltower.infra.consumers.FlightLogisticConsumer;

public class App {
	private FileUtils utils = new FileUtils();
	private Properties producersProps;
	private Properties consumerProps;
	private KafkaAdminClient kafkaClient;
	private TowerReportsProducer towerReportsProducer;
	private FlightLogisticConsumer flightLogisticConsumer;

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

		this.towerReportsProducer = new TowerReportsProducer(this.kafkaClient);
		this.flightLogisticConsumer = new FlightLogisticConsumer(this.kafkaClient);
	}

	public void start() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		Scanner input = new Scanner(System.in);

		while (input.hasNextLine()) {
			this.flightLogisticConsumer.run();

			System.out.println("\n\n Generate new flight event: [FLIGHT_CODE] [FLIGHT_STATUS] [LOGISTIC_STATUS]");
			String[] data = input.nextLine().split(" ");

			Flight flight = new Flight(data[0]);
			flight.setFlightStatus(data[1]);
			flight.setLogisticStatus(data[2]);

			String msgKey = flight.getFlightCode() + "#" + (calendar.getTime()).getTime();
			HashMap<String, Object> message = flight.toHashMap();

			this.towerReportsProducer.sendMessage(2, msgKey, message);
		}

		input.close();
	}
}
