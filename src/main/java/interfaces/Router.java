package interfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import interfaces.controllers.*;

@RestController
@SpringBootApplication
public class Router {
	FlightController flightController;

	public Router() {
		this.flightController = new FlightController();
	}

	@RequestMapping("/api")
	public void start() {
		SpringApplication.run(Router.class);
	}

	@GetMapping("/healthCheck")
	public String healthCheck() {
		return "{ \"status\": \"OK\" }";
	}
}
