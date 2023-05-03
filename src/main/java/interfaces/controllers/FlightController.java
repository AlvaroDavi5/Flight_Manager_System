package interfaces.controllers;

import java.util.Date;
import java.util.Calendar;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.http.ResponseEntity;
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

	@GetMapping //("/test")
	public String test(
			@RequestParam(value = "startDate", defaultValue = "") long startDate,
			@RequestParam(value = "endDate", defaultValue = "") long endDate) {
		System.out.println("startDate: " + startDate + " endDate: " + endDate);
		Flight flight = new Flight("test");
		if (flight.getFlightCode() == null) {
			return "error";
			// return ResponseEntity.notFound().build();
		}
		return "oi";
		// return ResponseEntity.ok(flight);
	}
}
