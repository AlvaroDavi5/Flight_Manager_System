package interfaces.controllers;

import java.util.Date;
import java.util.Calendar;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import domain.entities.Flight;

@RestController
@RequestMapping("/api/flights")
public class FlightController {
	long defaultStartDate;
	long defaultEndDate;

	public FlightController() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		this.defaultStartDate = (calendar.getTime()).getTime();
		calendar.add(Calendar.HOUR_OF_DAY, 2);
		this.defaultEndDate = (calendar.getTime()).getTime();
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
}
