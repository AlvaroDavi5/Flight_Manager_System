package com.flightmanager.domain.entities;

import java.util.HashMap;
import com.flightmanager.domain.enums.FlightStatusEnum;
import com.flightmanager.domain.enums.LogisticStatusEnum;
import com.flightmanager.domain.enums.PanelStatusEnum;
import com.flightmanager.infra.database.models.FlightsModel;

public class Flight {
	private long id;
	private String code;
	private int gateNumber;
	private FlightStatusEnum flightStatus;
	private LogisticStatusEnum logisticStatus;
	private PanelStatusEnum panelStatus;
	private String departureAirportCode;
	private Integer departureHorizontalDistance;
	private Integer departureVerticalDistance;
	private int departureAirportCandidates;
	private int departureTime;
	private String arrivalAirportCode;
	private Integer arrivalHorizontalDistance;
	private Integer arrivalVerticalDistance;
	private int arrivalAirportCandidates;
	private int arrivalTime;

	public Flight(String flightCode) {
		this.id = 0;
		this.code = flightCode;
		this.gateNumber = 0;
		this.flightStatus = null;
		this.logisticStatus = null;
		this.departureAirportCode = null;
		this.departureHorizontalDistance = 0;
		this.departureVerticalDistance = 0;
		this.departureAirportCandidates = 0;
		this.departureTime = 0;
		this.arrivalAirportCode = null;
		this.arrivalHorizontalDistance = 0;
		this.arrivalVerticalDistance = 0;
		this.arrivalAirportCandidates = 0;
		this.arrivalTime = 0;
	}

	public long getId() {
		return this.id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public String getFlightCode() {
		return this.code;
	}

	private void setFlightCode(String flightCode) {
		this.code = flightCode;
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

	public int getDepartureTimeInEpoch() {
		return this.departureTime;
	}

	public void setDepartureTimeInEpoch(int timeInEpoch) {
		this.departureTime = timeInEpoch;
	}

	public int getArrivalTimeInEpoch() {
		return this.arrivalTime;
	}

	public void setArrivalTimeInEpoch(int timeInEpoch) {
		this.arrivalTime = timeInEpoch;
	}

	public HashMap<String, Integer> getDepartureDistanceInMeters() {
		HashMap<String, Integer> departureDistanceInMeters = new HashMap<String, Integer>();

		departureDistanceInMeters.put("vertical", this.departureVerticalDistance);
		departureDistanceInMeters.put("horizontal", this.departureHorizontalDistance);

		return departureDistanceInMeters;
	}

	public void setDepartureDistanceInMeters(HashMap<String, Integer> distance) {
		this.departureVerticalDistance = distance.get("vertical");
		this.departureHorizontalDistance = distance.get("horizontal");
	}

	public void setDepartureDistanceInMeters(Integer vertical, Integer horizontal) {
		this.departureVerticalDistance = vertical;
		this.departureHorizontalDistance = horizontal;
	}

	public HashMap<String, Integer> getArrivalDistanceInMeters() {
		HashMap<String, Integer> arrivalDistanceInMeters = new HashMap<String, Integer>();

		arrivalDistanceInMeters.put("vertical", this.arrivalVerticalDistance);
		arrivalDistanceInMeters.put("horizontal", this.arrivalHorizontalDistance);

		return arrivalDistanceInMeters;
	}

	public void setArrivalDistanceInMeters(HashMap<String, Integer> distance) {
		this.arrivalVerticalDistance = distance.get("vertical");
		this.arrivalHorizontalDistance = distance.get("horizontal");
	}

	public void setArrivalDistanceInMeters(Integer vertical, Integer horizontal) {
		this.arrivalVerticalDistance = vertical;
		this.arrivalHorizontalDistance = horizontal;
	}

	public FlightStatusEnum getFlightStatus() {
		return this.flightStatus;
	}

	public String getFlightStatusString() {
		return "" + this.flightStatus;
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

	public String getLogisticStatusString() {
		return "" + this.logisticStatus;
	}

	public void setLogisticStatus(LogisticStatusEnum status) {
		this.logisticStatus = status;
	}

	public void setLogisticStatus(String status) {
		LogisticStatusEnum logisticStatusValue = LogisticStatusEnum.valueOf(status);

		if (logisticStatusValue != null)
			this.logisticStatus = logisticStatusValue;
	}

	public PanelStatusEnum getpanelStatus() {
		return this.panelStatus;
	}

	public String getpanelStatusString() {
		return "" + this.panelStatus;
	}

	public void setpanelStatus(PanelStatusEnum status) {
		this.panelStatus = status;
	}

	public void setpanelStatus(String status) {
		PanelStatusEnum panelStatusValue = PanelStatusEnum.valueOf(status);

		if (panelStatusValue != null)
			this.panelStatus = panelStatusValue;
	}

	public int getGateNumber() {
		return this.gateNumber;
	}

	public void setGateNumber(int gateNumber) {
		this.gateNumber = gateNumber;
	}

	public void fromModel(FlightsModel model) {
		if (model == null)
			return;

		this.setId((long) model.id);
		this.setFlightCode((String) model.getCode());
		this.setGateNumber((int) model.getGateNumber());
		this.setFlightStatus((String) model.getFlightStatus());
		this.setLogisticStatus((String) model.getLogisticStatus());
		this.setDepartureAirportCode((String) model.getDepartureAirportCode());
		this.setDepartureAirportCandidates((int) model.getDepartureAirportCandidates());
		this.setDepartureDistanceInMeters(
				(Integer) model.getDepartureVerticalDistance(),
				(Integer) model.getDepartureHorizontalDistance());
		this.setDepartureTimeInEpoch((int) model.getDepartureTime());
		this.setArrivalAirportCode((String) model.getArrivalAirportCode());
		this.setArrivalAirportCandidates((int) model.getArrivalAirportCandidates());
		this.setArrivalDistanceInMeters(
				(Integer) model.getArrivalVerticalDistance(),
				(Integer) model.getArrivalHorizontalDistance());
		this.setArrivalTimeInEpoch((int) model.getArrivalTime());
	}

	public FlightsModel toModel() {
		FlightsModel model = new FlightsModel();

		model.setCode(this.getFlightCode());
		model.setGateNumber(this.getGateNumber());
		model.setFlightStatus(this.getFlightStatusString());
		model.setLogisticStatus(this.getLogisticStatusString());
		model.setDepartureAirportCode(this.getDepartureAirportCode());
		model.setDepartureAirportCandidates(this.getDepartureAirportCandidates());
		model.setDepartureVerticalDistance(this.getDepartureDistanceInMeters().get("vertical"));
		model.setDepartureHorizontalDistance(this.getDepartureDistanceInMeters().get("horizontal"));
		model.setDepartureTime((int) this.getDepartureTimeInEpoch());
		model.setArrivalAirportCode(this.getArrivalAirportCode());
		model.setArrivalAirportCandidates(this.getArrivalAirportCandidates());
		model.setArrivalVerticalDistance(this.getArrivalDistanceInMeters().get("vertical"));
		model.setArrivalHorizontalDistance(this.getArrivalDistanceInMeters().get("horizontal"));
		model.setArrivalTime((int) this.getArrivalTimeInEpoch());

		return model;
	}

	public void fromHashMap(HashMap<String, Object> map) {
		if (map == null)
			return;

		this.setFlightCode((String) map.get("flightCode"));
		this.setGateNumber((int) map.get("gateNumber"));
		this.setFlightStatus((String) map.get("flightStatus"));
		this.setLogisticStatus((String) map.get("logisticStatus"));
		this.setDepartureAirportCode((String) map.get("departureAirportCode"));
		this.setDepartureAirportCandidates((int) map.get("departureAirportCandidates"));
		this.setDepartureDistanceInMeters(
				(Integer) map.get("departureVerticalDistance"),
				(Integer) map.get("departureHorizontalDistance"));
		this.setDepartureTimeInEpoch((int) map.get("departureTime"));
		this.setArrivalAirportCode((String) map.get("arrivalAirportCode"));
		this.setArrivalAirportCandidates((int) map.get("arrivalAirportCandidates"));
		this.setArrivalDistanceInMeters(
				(Integer) map.get("arrivalVerticalDistance"),
				(Integer) map.get("arrivalHorizontalDistance"));
		this.setArrivalTimeInEpoch((int) map.get("arrivalTime"));
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("flightCode", this.getFlightCode());
		map.put("gateNumber", this.getGateNumber());
		map.put("flightStatus", this.getFlightStatusString());
		map.put("logisticStatus", this.getLogisticStatusString());
		map.put("departureAirportCode", this.getDepartureAirportCode());
		map.put("departureAirportCandidates", this.getDepartureAirportCandidates());
		map.put("departureVerticalDistance", this.getDepartureDistanceInMeters().get("vertical"));
		map.put("departureHorizontalDistance", this.getDepartureDistanceInMeters().get("horizontal"));
		map.put("departureTime", this.getDepartureTimeInEpoch());
		map.put("arrivalAirportCode", this.getArrivalAirportCode());
		map.put("arrivalAirportCandidates", this.getArrivalAirportCandidates());
		map.put("arrivalVerticalDistance", this.getArrivalDistanceInMeters().get("vertical"));
		map.put("arrivalHorizontalDistance", this.getArrivalDistanceInMeters().get("horizontal"));
		map.put("arrivalTime", this.getArrivalTimeInEpoch());

		return map;
	}
}
