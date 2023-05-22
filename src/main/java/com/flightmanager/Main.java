package com.flightmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import com.flightmanager.app.KafkaApp;

@SpringBootApplication
@EnableJpaRepositories("com.flightmanager.*")
@ComponentScan("com.flightmanager.*")
@EntityScan("com.flightmanager.*")
public class Main {
	public static void main(String[] args) {
		try {
			if (args.length < 3)
				throw new Error("Invalid number of arguments (expected 3 arguments)", null);

			KafkaApp kafkaApp = new KafkaApp(args);
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
