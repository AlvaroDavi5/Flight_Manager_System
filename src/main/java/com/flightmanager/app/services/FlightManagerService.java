package com.flightmanager.app.services;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.flightmanager.domain.entities.Airport;
import com.flightmanager.domain.entities.Gate;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.app.utils.ParserUtils;
import com.flightmanager.app.strategies.FlightManagerFluxStrategy;
import com.flightmanager.infra.logging.AppLogger;
import com.flightmanager.infra.integration.queue.producers.FlightLogisticProducer;
import com.flightmanager.infra.integration.rest.OpenSkyRestClient;

@Service
public class FlightManagerService {
	private ParserUtils parser;
	private Airport airport;
	private Logger logger;
	private FlightManagerFluxStrategy flightManagerFluxStrategy;
	@Autowired
	private FlightLogisticProducer flightLogisticProducer;
	@Autowired
	private FlightService flightService;
	@Autowired
	private OpenSkyRestClient openSkyRestClient;

	public void start(String[] data) {
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
		this.parser = new ParserUtils();

		this.logger.info("Flight Manager App Started");
	}

	public void handleTowerReportMessage(String message) {
		Flight flight = new Flight(null);
		flight.fromHashMap(this.parser.stringfiedJsonToHashMap(message));

		this.logger.info(
				"New report event to flight: " + flight.getFlightCode()
						+ " with status: " + flight.getFlightStatus());

		this.flightManagerFluxStrategy.manageFlux(flight);
		this.updateRegisteredFlight(flight);
		this.dispatchFlightLogisticMessage(flight);
	}

	public void dispatchFlightLogisticMessage(Flight flight) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		String msgKey = flight.getFlightCode() + "#" + (calendar.getTime()).getTime();
		HashMap<String, Object> message = flight.toHashMap();
		message.put("key", msgKey);

		this.flightLogisticProducer.send(this.parser.hashMapToStringfiedJson(message, false));
		this.logger.info("Sended message with key: " + msgKey);
	}

	public void updateRegisteredFlight(Flight flight) {
		if (this.isRegisteredFlight(flight)) {
			Flight findedFlight = this.flightService.findByCode(flight.getFlightCode());
			findedFlight.setFlightStatus(flight.getFlightStatus());
			findedFlight.setLogisticStatus(flight.getLogisticStatus());
			this.flightService.update(findedFlight.getId(), findedFlight.toModel());
			flight.fromModel(findedFlight.toModel());
		} else {
			HashMap<String, Object> trafficRegister = this.getScheduledFlight(flight);
			if (trafficRegister != null) {
				flight.setArrivalAirportCode((String) trafficRegister.get("estArrivalAirport"));
				flight.setArrivalDistanceInMeters(
						(Integer) trafficRegister.get("estArrivalAirportVertDistance"),
						(Integer) trafficRegister.get("estArrivalAirportHorizDistance"));
				flight.setArrivalAirportCandidates((Integer) trafficRegister.get("arrivalAirportCandidatesCount"));
				flight.setArrivalTimeInEpoch((Integer) trafficRegister.get("lastSeen"));
				flight.setDepartureAirportCode((String) trafficRegister.get("estDepartureAirport"));
				flight.setDepartureDistanceInMeters(
						(Integer) trafficRegister.get("estDepartureAirportVertDistance"),
						(Integer) trafficRegister.get("estDepartureAirportHorizDistance"));
				flight.setDepartureAirportCandidates((Integer) trafficRegister.get("departureAirportCandidatesCount"));
				flight.setDepartureTimeInEpoch((Integer) trafficRegister.get("firstSeen"));
				this.flightService.create(flight.toModel());
			}
		}
	}

	public Boolean isRegisteredFlight(Flight flight) {
		Flight findedFlight = this.flightService.findByCode(flight.getFlightCode());
		return findedFlight != null && findedFlight.getId() > 0;
	}

	public Boolean isScheduledFlight(Flight flight) {
		LinkedList<HashMap<String, Object>> airTraffic = this.getCurrentAirTraffic();

		for (HashMap<String, Object> trafficRegister : airTraffic) {
			if (trafficRegister.get("callsign") == flight.getFlightCode())
				return true;
		}

		return false;
	}

	private HashMap<String, Object> getScheduledFlight(Flight flight) {
		LinkedList<HashMap<String, Object>> airTraffic = this.getCurrentAirTraffic();

		for (HashMap<String, Object> trafficRegister : airTraffic) {
			if (trafficRegister.get("callsign") == flight.getFlightCode())
				return trafficRegister;
		}

		return null;
	}

	public LinkedList<HashMap<String, Object>> getCurrentAirTraffic() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 10);
		long endDate = (calendar.getTime()).getTime() / 1000;
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long startDate = (calendar.getTime()).getTime() / 1000;

		String requestResult = this.openSkyRestClient.getArrivalsByAirport(this.getAirportICAO(), startDate, endDate)
				.getBody();
		return this.parser.stringfiedObjectArrayToHashMapList(requestResult);
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
