package interfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import interfaces.controllers.*;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class Router extends Thread {
	FlightController flightController;

	public Router() {
		this.flightController = new FlightController();
	}

	@Override
	public void run() {
		SpringApplication.run(this.getClass());
	}

	@GetMapping("/healthcheck")
	public String healthCheck() {
		return "{ \"status\": \"OK\" }";
	}
}
