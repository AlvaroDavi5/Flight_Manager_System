package com.flight_manager.app.services;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.logging.log4j.Logger;
import com.flight_manager.infra.logging.AppLogger;
import com.flight_manager.infra.integration.queue.producers.FlightLogisticProducer;
import com.flight_manager.infra.integration.queue.producers.FlightNotificationsProducer;
import com.flight_manager.domain.entities.Airport;
import com.flight_manager.domain.entities.Gate;
import com.flight_manager.domain.entities.Flight;
import com.flight_manager.domain.enums.FlightStatusEnum;
import com.flight_manager.domain.enums.LogisticStatusEnum;

public class FlightManagerService {
	private Airport airport;
	private Logger logger;
	private FlightLogisticProducer flightLogisticProducer;
	private FlightNotificationsProducer flightNotificationsProducer;

	public FlightManagerService(FlightLogisticProducer flightLogisticProducer,
			FlightNotificationsProducer flightNotificationsProducer) {
		this.airport = new Airport("SBGR", "GRU", 200);

		AppLogger logger = new AppLogger(this.getClass().getName());
		this.logger = logger.getLogger();

		this.flightLogisticProducer = flightLogisticProducer;
		this.flightNotificationsProducer = flightNotificationsProducer;
	}

	public void handleTowerReportMessage(HashMap<String, Object> message) {
		Flight flight = new Flight(null);
		flight.fromHashMap(message);

		this.logger.warn(
				"New report event to flight: " + flight.getFlightCode()
						+ " with status: " + flight.getFlightStatus());

		Gate freeGate = this.airport.getLastFreeGate();

		if (freeGate != null && flight.getLogisticStatus() == LogisticStatusEnum.REQUESTING_LAND) {
			this.airport.assignFlightToGate(flight, freeGate);
		} else {
			flight.setFlightStatus(FlightStatusEnum.HOLDING);
		}

		// TODO: create flux
	}

	public void handleAirTrafficMessage(HashMap<String, Object> message) {
		// TODO: create flux
	}

	public void dispatchFlightLogisticMessage(Flight flight) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		String msgKey = flight.getFlightCode() + "#" + (calendar.getTime()).getTime();
		HashMap<String, Object> message = flight.toHashMap();

		this.flightLogisticProducer.sendMessage(2,
				msgKey, message);
	}

	public void dispatchFlightNotificationMessage(Flight flight) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		String msgKey = flight.getFlightCode() + "#" + (calendar.getTime()).getTime();
		HashMap<String, Object> message = flight.toHashMap();
		message.remove("logisticStatus");
		message.remove("departureAirportCandidates");
		message.remove("departureDistanceInMeters");
		message.remove("arrivalAirportCandidates");
		message.remove("arrivalDistanceInMeters");

		this.flightNotificationsProducer.sendMessage(2,
				msgKey, message);
	}
}
