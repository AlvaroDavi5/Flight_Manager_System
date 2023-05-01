package app.utils;

import java.util.HashMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import domain.entities.Flight;

public class ParserUtils {
	public ParserUtils() {
	}

	public HashMap<String, Object> stringfiedJsonToHashMap(String strJSON) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			map = mapper.readValue(strJSON, HashMap.class);
		} catch (JsonMappingException exception) {
			return null;
		} catch (JsonProcessingException exception) {
			return null;
		}
		return map;
	}

	public String hashMapToStringfiedJson(HashMap<String, Object> map, Boolean pretty) {
		String strJSON = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter prettyMapper = mapper.writer().withDefaultPrettyPrinter();
			if (pretty)
				strJSON = prettyMapper.writeValueAsString(map);
			else
				strJSON = mapper.writeValueAsString(map);
		} catch (JsonMappingException exception) {
			return null;
		} catch (JsonProcessingException exception) {
			return null;
		}
		return strJSON;
	}

	public Flight mapHashMapOnFlight(HashMap<String, Object> map) {
		Flight flight = new Flight((String) map.get("flightCode"));

		flight.setGateNumber((int) map.get("gateNumber"));
		flight.setFlightStatus((String) map.get("flightStatus"));
		flight.setLogisticStatus((String) map.get("logisticStatus"));
		flight.setDepartureAirportCode((String) map.get("departureAirportCode"));
		flight.setDepartureAirportCandidates((int) map.get("departureAirportCandidates"));
		flight.setDepartureDistanceInMeters((HashMap<String, Integer>) map.get("departureDistanceInMeters"));
		flight.setDepartureTime((int) map.get("departureTime"));
		flight.setArrivalAirportCode((String) map.get("arrivalAirportCode"));
		flight.setArrivalAirportCandidates((int) map.get("arrivalAirportCandidates"));
		flight.setArrivalDistanceInMeters((HashMap<String, Integer>) map.get("arrivalDistanceInMeters"));
		flight.setArrivalTime((int) map.get("arrivalTime"));

		return flight;
	}

	public HashMap<String, Object> mapFlightOnHashMap(Flight flight) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("flightCode", flight.getFlightCode());
		map.put("gateNumber", flight.getGateNumber());
		map.put("flightStatus", flight.getFlightStatus());
		map.put("logisticStatus", flight.getLogisticStatus());
		map.put("departureAirportCode", flight.getDepartureAirportCode());
		map.put("departureAirportCandidates", flight.getDepartureAirportCandidates());
		map.put("departureDistanceInMeters", flight.getDepartureDistanceInMeters());
		map.put("departureTime", flight.getDepartureTime());
		map.put("arrivalAirportCode", flight.getArrivalAirportCode());
		map.put("arrivalAirportCandidates", flight.getArrivalAirportCandidates());
		map.put("arrivalDistanceInMeters", flight.getArrivalDistanceInMeters());
		map.put("arrivalTime", flight.getArrivalTime());

		return map;
	}
}
