package com.flightmanager.interfaces.controllers;

import java.util.LinkedList;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.app.services.FlightService;
import com.flightmanager.infra.database.models.FlightsModel;
import com.flightmanager.infra.integration.rest.OpenSkyRestClient;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
	private FlightService flightService = new FlightService();
	private OpenSkyRestClient openSkyRestClient = new OpenSkyRestClient();

	@PostMapping
	public @ResponseBody ResponseEntity<Flight> create(@RequestBody @Validated FlightsModel flightData) {
		try {
			Flight flight = this.flightService.create(flightData);

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Flight> read(@RequestParam(value = "id", defaultValue = "0") long id) {
		try {
			Flight flight = this.flightService.read(id);

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Flight> update(@RequestBody @Validated FlightsModel flightData) {
		try {
			Flight flight = this.flightService.update(flightData);

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Boolean> delete(@RequestParam(value = "id", defaultValue = "0") long id) {
		try {
			Boolean deleted = this.flightService.delete(id);
			return ResponseEntity.ok(deleted);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/list")
	public @ResponseBody ResponseEntity<LinkedList<Flight>> list(
			@RequestParam(value = "startDate", defaultValue = "") long startDate,
			@RequestParam(value = "endDate", defaultValue = "") long endDate) {
		if (startDate != 0 && endDate != 0)
			try {
				if (startDate != 0 && endDate != 0) {
					return ResponseEntity.badRequest().build();
				}
				LinkedList<Flight> flights = this.flightService.list(startDate, endDate);

				return ResponseEntity.ok(flights);
			} catch (Exception exception) {
				return ResponseEntity.badRequest().build();
			}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/test")
	public @ResponseBody ResponseEntity<String> test() {
		try {
			System.out.println("HealthCheck Requested");
			return this.openSkyRestClient.getHealthCheck();
		} catch (Exception exception) {
			System.out.println("HealthCheck Exception");
			return ResponseEntity.badRequest().body("{}");
		}
	}
}
