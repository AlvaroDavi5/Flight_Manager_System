package com.flightmanager.interfaces.controllers;

import java.util.LinkedList;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.infra.database.models.FlightsModel;
import com.flightmanager.infra.integration.rest.OpenSkyRestClient;
import com.flightmanager.infra.database.repositories.FlightsRepositoryInterface;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
	@Autowired
	private FlightsRepositoryInterface flightsRepository;
	private OpenSkyRestClient openSkyRestClient = new OpenSkyRestClient();

	@PostMapping
	@Transactional
	public @ResponseBody ResponseEntity<Flight> create(@RequestBody @Validated FlightsModel flightData) {
		try {
			Flight flight = new Flight(null);
			flight.fromModel(this.flightsRepository.save(flightData));

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{id}")
	@Transactional
	public @ResponseBody ResponseEntity<Flight> read(@RequestParam(value = "id", defaultValue = "0") long id) {
		try {
			Flight flight = new Flight(null);
			flight.fromModel(this.flightsRepository.findById(id));

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/{id}")
	@Transactional
	public @ResponseBody ResponseEntity<Flight> update(@RequestBody @Validated FlightsModel flightData) {
		try {
			Flight flight = new Flight(null);
			flight.fromModel(this.flightsRepository.save(flightData));

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	@Transactional
	public @ResponseBody ResponseEntity<Boolean> delete(@RequestParam(value = "id", defaultValue = "0") long id) {
		try {
			this.flightsRepository.deleteById(id);
			return ResponseEntity.ok(true);
		} catch (Exception exception) {
			return ResponseEntity.ok(false);
		}
	}

	@GetMapping("/list")
	@Transactional
	public @ResponseBody ResponseEntity<LinkedList<Flight>> list(
			@RequestParam(value = "startDate", defaultValue = "") long startDate,
			@RequestParam(value = "endDate", defaultValue = "") long endDate) {
		if (startDate != 0 && endDate != 0)
			try {
				LinkedList<FlightsModel> flightModels = this.flightsRepository.findAll();
				LinkedList<Flight> flights = new LinkedList<Flight>();

				for (FlightsModel flightModel : flightModels) {
					Flight flight = new Flight(null);
					flight.fromModel(flightModel);
					flights.addLast(flight);
				}

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
