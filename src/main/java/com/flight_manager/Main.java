package com.flight_manager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import com.flight_manager.app.KafkaApp;
import com.flight_manager.infra.database.models.*;
import com.flight_manager.app.services.*;
import com.flight_manager.interfaces.*;
import com.flight_manager.interfaces.controllers.*;
import com.flight_manager.infra.database.repositories.*;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { FlightsRepositoryInterface.class })
@ComponentScan(basePackageClasses = { Router.class, FlightController.class, FlightService.class })
@EntityScan(basePackageClasses = { FlightsModel.class })
public class Main {
	public static void main(String[] args) {
		try {
			KafkaApp kafkaApp = new KafkaApp();

			kafkaApp.start();
			SpringApplication.run(Main.class);
		} catch (Error error) {
			System.out.println("Main.Error → " + error.getMessage());
			error.printStackTrace();
			System.exit(0);
		} catch (Exception exception) {
			System.out.println("Main.Exception → " + exception.getMessage());
			exception.printStackTrace();
			System.exit(1);
		}
	}
}
