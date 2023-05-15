package com.flightmanager.app.services;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.logging.log4j.Logger;
import com.flightmanager.domain.entities.Airport;
import com.flightmanager.domain.entities.Gate;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.app.strategies.FlightManagerFluxStrategy;
import com.flightmanager.infra.logging.AppLogger;
import com.flightmanager.infra.integration.queue.producers.FlightLogisticProducer;
import com.flightmanager.infra.integration.queue.producers.FlightNotificationsProducer;

public class FlightManagerService {
	private Airport airport;
	private Logger logger;
	private FlightManagerFluxStrategy flightManagerFluxStrategy;
	private FlightLogisticProducer flightLogisticProducer;
	private FlightNotificationsProducer flightNotificationsProducer;

	public FlightManagerService(String[] data) {
		String airportICAO = System.getenv("AIRPORT_ICAO");
		airportICAO = airportICAO == null
				? data[0]
				: airportICAO;
		String airportIATA = System.getenv("AIRPORT_IATA");
		airportIATA = airportIATA == null
				? data[1]
				: airportIATA;
		Integer airportGatesAmount = Integer.parseInt(System.getenv("AIRPORT_GATES"));
		airportGatesAmount = airportGatesAmount == null || airportGatesAmount == 0
				? Integer.parseInt(data[2])
				: airportGatesAmount;

		this.airport = new Airport(airportICAO, airportIATA, airportGatesAmount);
		this.flightManagerFluxStrategy = new FlightManagerFluxStrategy(this);

		AppLogger logger = new AppLogger(this.getClass().getName());
		this.logger = logger.getLogger();
	}

	public void setFlightLogisticProducer(FlightLogisticProducer flightLogisticProducer) {
		this.flightLogisticProducer = flightLogisticProducer;
	}

	public void setFlightNotificationsProducer(FlightNotificationsProducer flightNotificationsProducer) {
		this.flightNotificationsProducer = flightNotificationsProducer;
	}

	public void start() {
		this.logger.info("Flight Manager App Started");
	}

	public void handleTowerReportMessage(HashMap<String, Object> message) {
		Flight flight = new Flight(null);
		flight.fromHashMap(message);

		this.logger.info(
				"New report event to flight: " + flight.getFlightCode()
						+ " with status: " + flight.getFlightStatus());

		this.flightManagerFluxStrategy.manageFlux(flight);

		this.dispatchFlightLogisticMessage(flight);
		this.dispatchFlightNotificationMessage(flight);
	}

	public void handleAirTrafficMessage(HashMap<String, Object> message) {
		// TODO - create flux
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

	public Boolean isRegistered(Flight flight) {
		return flight.getId() != 0;
		// TODO - validate it on database and OpenSky
	}

	public Gate getFreeGate() {
		Gate freeGate = this.airport.getLastFreeGate();

		// TODO - validate it on database

		return freeGate;
	}

	public Gate findGateByNumber(Integer gateNumber) {
		Gate gate = this.airport.findGateByNumber(gateNumber);

		return gate;
	}

	public String getAirportICAO() {
		String airportICAO = this.airport.getICAO();

		return airportICAO;
	}

	public Boolean isAirstripFreeToLand() {
		Airport airport = this.airport;

		return airport.isAirstripFree();
	}

	public void closeAirstrip() {
		this.airport.closeAirstrip();
	}

	public void openAirstrip() {
		this.airport.openAirstrip();
	}
}
