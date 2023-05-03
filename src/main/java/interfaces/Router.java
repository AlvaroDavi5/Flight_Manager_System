package interfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import interfaces.controllers.*;

@RestController
@SpringBootApplication
public class Router {
	FlightController flightController;

	public Router() {
		this.flightController = new FlightController();
	}

	public void start() {
		SpringApplication.run(Router.class);
	}

	@RequestMapping("/api/healthCheck")
	public String healthCheck() {
		return "OK";
	}
}
