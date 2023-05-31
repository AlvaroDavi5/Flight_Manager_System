package com.flightmanager.interfaces.controllers;

import java.util.LinkedList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.flightmanager.app.utils.ParserUtils;
import com.flightmanager.app.services.FlightService;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.infra.database.models.FlightsModel;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
	@Autowired
	private FlightService flightService;
	private ParserUtils parser = new ParserUtils();

	private FlightsModel stringfiedJsonToFlightsModel(String data) {
		Flight newFlight = new Flight(null);
		newFlight.fromHashMap(this.parser.stringfiedJsonToHashMap(data));
		return newFlight.toModel();
	}

	@PostMapping
	public @ResponseBody ResponseEntity<Flight> create(@RequestBody String data) {
		try {
			Flight flight = this.flightService.create(this.stringfiedJsonToFlightsModel(data));

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Flight> read(@PathVariable long id) {
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
	public @ResponseBody ResponseEntity<Flight> update(@PathVariable long id,
			@RequestBody String data) {
		try {
			FlightsModel flightData = this.stringfiedJsonToFlightsModel(data);
			flightData.setCode(null);
			flightData.setGateNumber(null);
			flightData.setFlightStatus(null);
			flightData.setLogisticStatus(null);
			flightData.setDepartureAirportCode(null);
			flightData.setArrivalAirportCode(null);
			Flight flight = this.flightService.update(id, flightData);

			if (flight == null || flight.getFlightCode() == null) {
				return ResponseEntity.notFound().build();
			}

			return ResponseEntity.ok(flight);
		} catch (Exception exception) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Boolean> delete(@PathVariable long id) {
		try {
			Boolean deleted = this.flightService.delete(id);
			return ResponseEntity.status(HttpStatus.OK).body(deleted);
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
		}
	}

	@GetMapping("/list")
	public @ResponseBody ResponseEntity<LinkedList<Flight>> list(
			@RequestParam(value = "startDate", defaultValue = "") long startDate,
			@RequestParam(value = "endDate", defaultValue = "") long endDate) {
		if (startDate != 0 && endDate != 0)
			try {
				LinkedList<Flight> flights = this.flightService.list(startDate, endDate);

				return ResponseEntity.ok(flights);
			} catch (Exception exception) {
				return ResponseEntity.badRequest().build();
			}
		return ResponseEntity.notFound().build();
	}
}
