package interfaces.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import domain.entities.Flight;
import infra.integration.rest.OpenSkyRestClient;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
	private OpenSkyRestClient openSkyRestClient;

	public FlightController() {
		this.openSkyRestClient = new OpenSkyRestClient();
	}

	@GetMapping("/list")
	public ResponseEntity<Flight> list(
			@RequestParam(value = "startDate", defaultValue = "") long startDate,
			@RequestParam(value = "endDate", defaultValue = "") long endDate) {
		Flight flight = null;

		if (startDate != 0 && endDate != 0)
			flight = new Flight("test");

		if (flight != null && flight.getFlightCode() == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(flight);
	}

	@GetMapping("/test")
	public ResponseEntity<String> test() {
		try {
			System.out.println("HealthCheck Requested");
			return this.openSkyRestClient.getHealthCheck();
		} catch (Exception e) {
			System.out.println("HealthCheck Exception");
			return ResponseEntity.badRequest().body("{}");
		}
	}
}
