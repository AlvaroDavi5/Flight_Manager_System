package domain.entities;

import java.util.HashMap;
import domain.enums.FlightStatusEnum;
import domain.enums.LogisticStatusEnum;

public class Flight {
	private String code;
	private int gateNumber;
	private FlightStatusEnum flightStatus;
	private LogisticStatusEnum logisticStatus;
	private String departureAirportCode;
	private String arrivalAirportCode;
	private int departureAirportCandidates;
	private int arrivalAirportCandidates;
	private HashMap<String, Integer> departureDistanceInMeters;
	private HashMap<String, Integer> arrivalDistanceInMeters;
	private int departureTime;
	private int arrivalTime;

	public Flight(String code) {
		this.code = code;
		this.departureDistanceInMeters = new HashMap<String, Integer>();
		this.arrivalDistanceInMeters = new HashMap<String, Integer>();
	}

	public String getFlightCode() {
		return this.code;
	}

	public String getDepartureAirportCode() {
		return this.departureAirportCode;
	}

	public void setDepartureAirportCode(String airportCode) {
		this.departureAirportCode = airportCode;
	}

	public String getArrivalAirportCode() {
		return this.arrivalAirportCode;
	}

	public void setArrivalAirportCode(String airportCode) {
		this.arrivalAirportCode = airportCode;
	}

	public int getDepartureAirportCandidates() {
		return this.departureAirportCandidates;
	}

	public void setDepartureAirportCandidates(int count) {
		this.departureAirportCandidates = count;
	}

	public int getArrivalAirportCandidates() {
		return this.arrivalAirportCandidates;
	}

	public void setArrivalAirportCandidates(int count) {
		this.arrivalAirportCandidates = count;
	}

	public int getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(int timeInEpoch) {
		this.departureTime = timeInEpoch;
	}

	public int getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(int timeInEpoch) {
		this.arrivalTime = timeInEpoch;
	}

	public HashMap<String, Integer> getDepartureDistanceInMeters() {
		return this.departureDistanceInMeters;
	}

	public void setDepartureDistanceInMeters(HashMap<String, Integer> distance) {
		this.departureDistanceInMeters = distance;
	}

	public void setDepartureDistanceInMeters(Integer vertical, Integer horizontal) {
		this.departureDistanceInMeters.put("vertical", vertical);
		this.departureDistanceInMeters.put("horizontal", horizontal);
	}

	public HashMap<String, Integer> getArrivalDistanceInMeters() {
		return this.arrivalDistanceInMeters;
	}

	public void setArrivalDistanceInMeters(HashMap<String, Integer> distance) {
		this.arrivalDistanceInMeters = distance;
	}

	public void setArrivalDistanceInMeters(Integer vertical, Integer horizontal) {
		this.arrivalDistanceInMeters.put("vertical", vertical);
		this.arrivalDistanceInMeters.put("horizontal", horizontal);
	}

	public FlightStatusEnum getFlightStatus() {
		return this.flightStatus;
	}

	public void setFlightStatus(FlightStatusEnum status) {
		this.flightStatus = status;
	}

	public void setFlightStatus(String status) {
		FlightStatusEnum flightStatusValue = FlightStatusEnum.valueOf(status);

		if (flightStatusValue != null)
			this.flightStatus = flightStatusValue;
	}

	public LogisticStatusEnum getLogisticStatus() {
		return this.logisticStatus;
	}

	public void setLogisticStatus(LogisticStatusEnum status) {
		this.logisticStatus = status;
	}

	public void setLogisticStatus(String status) {
		LogisticStatusEnum logisticStatusValue = LogisticStatusEnum.valueOf(status);

		if (logisticStatusValue != null)
			this.logisticStatus = logisticStatusValue;
	}

	public int getGateNumber() {
		return this.gateNumber;
	}

	public void setGateNumber(int gateNumber) {
		this.gateNumber = gateNumber;
	}
}
