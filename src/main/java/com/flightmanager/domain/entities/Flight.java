package com.flightmanager.domain.entities;

import java.util.HashMap;
import com.flightmanager.domain.enums.FlightStatusEnum;
import com.flightmanager.domain.enums.LogisticStatusEnum;
import com.flightmanager.domain.enums.PanelStatusEnum;
import com.flightmanager.infra.database.models.FlightsModel;

public class Flight {
	private long id;
	private String code;
	private Integer gateNumber;
	private FlightStatusEnum flightStatus;
	private LogisticStatusEnum logisticStatus;
	private PanelStatusEnum panelStatus;
	private String departureAirportCode;
	private Integer departureHorizontalDistance;
	private Integer departureVerticalDistance;
	private Integer departureAirportCandidates;
	private Integer departureTime;
	private String arrivalAirportCode;
	private Integer arrivalHorizontalDistance;
	private Integer arrivalVerticalDistance;
	private Integer arrivalAirportCandidates;
	private Integer arrivalTime;

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

	public Integer getDepartureAirportCandidates() {
		return this.departureAirportCandidates;
	}

	public void setDepartureAirportCandidates(Integer count) {
		this.departureAirportCandidates = count;
	}

	public Integer getArrivalAirportCandidates() {
		return this.arrivalAirportCandidates;
	}

	public void setArrivalAirportCandidates(Integer count) {
		this.arrivalAirportCandidates = count;
	}

	public Integer getDepartureTimeInEpoch() {
		return this.departureTime;
	}

	public void setDepartureTimeInEpoch(Integer timeInEpoch) {
		this.departureTime = timeInEpoch;
	}

	public Integer getArrivalTimeInEpoch() {
		return this.arrivalTime;
	}

	public void setArrivalTimeInEpoch(Integer timeInEpoch) {
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
		return this.flightStatus.toString();
	}

	public void setFlightStatus(FlightStatusEnum status) {
		this.flightStatus = status;
	}

	public void setFlightStatus(String status) {
		try {
			FlightStatusEnum flightStatusValue = FlightStatusEnum.valueOf(status);

			if (flightStatusValue != null)
				this.flightStatus = flightStatusValue;
		} catch (Exception exception) {
		}
	}

	public LogisticStatusEnum getLogisticStatus() {
		return this.logisticStatus;
	}

	public String getLogisticStatusString() {
		return this.logisticStatus.toString();
	}

	public void setLogisticStatus(LogisticStatusEnum status) {
		this.logisticStatus = status;
	}

	public void setLogisticStatus(String status) {
		try {
			LogisticStatusEnum logisticStatusValue = LogisticStatusEnum.valueOf(status);

			if (logisticStatusValue != null)
				this.logisticStatus = logisticStatusValue;
		} catch (Exception exception) {
		}
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
		try {
			PanelStatusEnum panelStatusValue = PanelStatusEnum.valueOf(status);

			if (panelStatusValue != null)
				this.panelStatus = panelStatusValue;
		} catch (Exception exception) {
		}
	}

	public Integer getGateNumber() {
		return this.gateNumber;
	}

	public void setGateNumber(Integer gateNumber) {
		this.gateNumber = gateNumber;
	}

	public void fromModel(FlightsModel model) {
		if (model == null)
			return;

		this.setId((long) model.id);
		this.setFlightCode((String) model.getCode());
		this.setGateNumber((Integer) model.getGateNumber());
		if (model.getFlightStatus() != null)
			this.setFlightStatus((String) model.getFlightStatus());
		if (model.getLogisticStatus() != null)
			this.setLogisticStatus((String) model.getLogisticStatus());
		this.setDepartureAirportCode((String) model.getDepartureAirportCode());
		this.setDepartureAirportCandidates((Integer) model.getDepartureAirportCandidates());
		this.setDepartureDistanceInMeters(
				(Integer) model.getDepartureVerticalDistance(),
				(Integer) model.getDepartureHorizontalDistance());
		this.setDepartureTimeInEpoch((Integer) model.getDepartureTime());
		this.setArrivalAirportCode((String) model.getArrivalAirportCode());
		this.setArrivalAirportCandidates((Integer) model.getArrivalAirportCandidates());
		this.setArrivalDistanceInMeters(
				(Integer) model.getArrivalVerticalDistance(),
				(Integer) model.getArrivalHorizontalDistance());
		this.setArrivalTimeInEpoch((Integer) model.getArrivalTime());
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
		model.setDepartureTime((Integer) this.getDepartureTimeInEpoch());
		model.setArrivalAirportCode(this.getArrivalAirportCode());
		model.setArrivalAirportCandidates(this.getArrivalAirportCandidates());
		model.setArrivalVerticalDistance(this.getArrivalDistanceInMeters().get("vertical"));
		model.setArrivalHorizontalDistance(this.getArrivalDistanceInMeters().get("horizontal"));
		model.setArrivalTime((Integer) this.getArrivalTimeInEpoch());

		return model;
	}

	public void fromHashMap(HashMap<String, Object> map) {
		if (map == null)
			return;

		this.setFlightCode((String) map.get("flightCode"));
		this.setGateNumber((Integer) map.get("gateNumber"));
		if (map.get("flightStatus") != null)
			this.setFlightStatus((String) map.get("flightStatus"));
		if (map.get("logisticStatus") != null)
			this.setLogisticStatus((String) map.get("logisticStatus"));
		this.setDepartureAirportCode((String) map.get("departureAirportCode"));
		this.setDepartureAirportCandidates((Integer) map.get("departureAirportCandidates"));
		this.setDepartureDistanceInMeters(
				(Integer) map.get("departureVerticalDistance"),
				(Integer) map.get("departureHorizontalDistance"));
		this.setDepartureTimeInEpoch((Integer) map.get("departureTime"));
		this.setArrivalAirportCode((String) map.get("arrivalAirportCode"));
		this.setArrivalAirportCandidates((Integer) map.get("arrivalAirportCandidates"));
		this.setArrivalDistanceInMeters(
				(Integer) map.get("arrivalVerticalDistance"),
				(Integer) map.get("arrivalHorizontalDistance"));
		this.setArrivalTimeInEpoch((Integer) map.get("arrivalTime"));
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
