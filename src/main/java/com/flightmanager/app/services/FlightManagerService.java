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
	private GateService gateService;
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
		String msgKey = flight.getFlightCode();
		HashMap<String, Object> message = flight.toHashMap();
		message.put("key", msgKey);

		this.flightLogisticProducer.send(msgKey, this.parser.hashMapToStringfiedJson(message, false));
		this.logger.info("Sended message with key: " + msgKey);
	}

	public void updateRegisteredFlight(Flight flight) {
		Flight findedFlight = null;

		if (this.isRegisteredFlight(flight)) {
			findedFlight = this.flightService.findByCode(flight.getFlightCode());
		} else {
			findedFlight = this.getScheduledFlight(flight);
			if (findedFlight != null) {
				this.flightService.create(findedFlight.toModel());
			}
		}

		if (findedFlight != null) {
			this.logger.info("Flight founded!");
			findedFlight.setFlightStatus(flight.getFlightStatus());
			findedFlight.setLogisticStatus(flight.getLogisticStatus());
			this.flightService.update(findedFlight.getId(), findedFlight.toModel());
			flight.fromModel(findedFlight.toModel());
		}
	}

	public Boolean isRegisteredFlight(Flight flight) {
		this.logger.info("Checking if the flightCode: " + flight.getFlightCode() + " is already registered.");
		Flight findedFlight = this.flightService.findByCode(flight.getFlightCode());
		return findedFlight != null && findedFlight.getId() > 0;
	}

	public Boolean isScheduledFlight(Flight flight) {
		LinkedList<HashMap<String, Object>> airTraffic = this.getCurrentAirTraffic();

		for (HashMap<String, Object> trafficRegister : airTraffic) {
			if (trafficRegister.get("callsign").toString().contains(flight.getFlightCode()))
				return true;
		}

		return false;
	}

	private Flight getScheduledFlight(Flight flight) {
		LinkedList<HashMap<String, Object>> airTraffic = this.getCurrentAirTraffic();
		Flight scheduledFlight = null;

		for (HashMap<String, Object> trafficRegister : airTraffic) {
			Flight buildedFlight = this.buildFlight(trafficRegister);
			if (trafficRegister.get("callsign").toString().contains(flight.getFlightCode())) {
				scheduledFlight = buildedFlight;
				break;
			}
		}

		return scheduledFlight;
	}

	private Flight buildFlight(HashMap<String, Object> trafficRegister) {
		Flight flight = new Flight((String) trafficRegister.get("callsign").toString().trim());

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

		return flight;
	}

	public LinkedList<HashMap<String, Object>> getCurrentAirTraffic() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 10);
		long endDate = (calendar.getTime()).getTime() / 1000;
		calendar.setTime(new Date());
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		if (dayOfMonth <= 0) {
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		}
		;
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		long startDate = (calendar.getTime()).getTime() / 1000;

		this.logger.info("Getting current air traffic for airport: " + this.getAirportICAO() + ". from: "
				+ new Date(startDate * 1000) + " to: " + new Date(endDate * 1000));
		String requestResult = this.openSkyRestClient.getArrivalsByAirport(this.getAirportICAO(), startDate, endDate)
				.getBody();
		return this.parser.stringfiedObjectArrayToHashMapList(requestResult);
	}

	public Gate createGate(Gate newGate) {
		Gate gate = this.gateService.create(newGate.toModel());
		gate.fromModel(newGate.toModel());
		return gate;
	}

	public Gate getFreeGate() {
		Gate freeGate = this.airport.getLastFreeGate();

		Gate gate = this.gateService.findByGateNumber(freeGate.getGateNumber());
		if (gate == null || !gate.isFreeToDock())
			return null;

		return freeGate;
	}

	public void updateGate(Gate updatedGate) {
		Gate findedGate = this.gateService.findByGateNumber(updatedGate.getGateNumber());
		if (findedGate == null)
			findedGate = this.createGate(updatedGate);
		findedGate.fromModel(updatedGate.toModel());
		this.gateService.update(findedGate.getId(), findedGate.toModel());
	}

	public void openGateDocking(Gate gate) {
		gate.openDocking();
		this.updateGate(gate);
	}

	public void closeGateDocking(Gate gate) {
		gate.closeDocking();
		this.updateGate(gate);
	}

	public void openGateBoarding(Gate gate) {
		gate.openBoarding();
		this.updateGate(gate);
	}

	public void closeGateBoarding(Gate gate) {
		gate.closeBoarding();
		this.updateGate(gate);
	}

	public void setGateFlightCode(Gate gate, String flightCode) {
		gate.setFlightCode(flightCode);
		this.updateGate(gate);
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
