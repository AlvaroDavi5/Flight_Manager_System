package com.controltower.app;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Properties;
import com.flightmanager.app.utils.FileUtils;
import com.flightmanager.domain.entities.Flight;
import com.controltower.infra.integration.kafka.*;
import com.controltower.infra.integration.queue.consumers.FlightLogisticConsumer;
import com.controltower.infra.integration.queue.producers.TowerReportsProducer;

public class App {
	private FileUtils utils = new FileUtils();
	private Properties producersProps;
	private Properties consumersProps;
	private KafkaAdminClient kafkaAdminClient;
	private TowerReportsProducer towerReportsProducer;
	private FlightLogisticConsumer flightLogisticConsumer;

	public App() {
		try {
			this.producersProps = this.utils.readPropertiesFile("./src/main/resources/producer.properties");
			this.consumersProps = this.utils.readPropertiesFile("./src/main/resources/consumer.properties");
		} catch (Exception exception) {
			System.out.print("Read properties error, exiting...");
			exception.printStackTrace();
			System.exit(1);
		}
		this.kafkaAdminClient = new KafkaAdminClient(this.producersProps, this.consumersProps);
		this.towerReportsProducer = new TowerReportsProducer(this.kafkaAdminClient);
		this.flightLogisticConsumer = new FlightLogisticConsumer(this.kafkaAdminClient);
	}

	public void start() {
		Scanner input = new Scanner(System.in);
		while (true) {
			this.runConsumers();
			System.out
					.println("\n\n Generate new flight event: [FLIGHT_CODE] [GATE_NUMBER] [FLIGHT_STATUS] [LOGISTIC_STATUS]");

			String line = input.nextLine();
			if (line.equals("") || line == null) {
				break;
			}

			String[] data = line.split(" ");
			if (data.length >= 4)
				this.createEvent(data);
			this.runConsumers();
		}

		input.close();
		System.exit(0);
	}

	private void runConsumers() {
		this.flightLogisticConsumer.runPolling();
	}

	private void createEvent(String[] data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		Flight flight = new Flight(data[0]);
		flight.setGateNumber(Integer.parseInt(data[1]));
		flight.setFlightStatus(data[2]);
		flight.setLogisticStatus(data[3]);

		String msgKey = flight.getFlightCode();
		HashMap<String, Object> message = flight.toHashMap();

		this.towerReportsProducer.sendMessage(2, msgKey, message);
	}
}
