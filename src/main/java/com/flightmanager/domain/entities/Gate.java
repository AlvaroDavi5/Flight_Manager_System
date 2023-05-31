package com.flightmanager.domain.entities;

import java.util.HashMap;
import com.flightmanager.infra.database.models.GatesModel;

public class Gate {
	private long id;
	private Integer number;
	private Boolean isFreeToDock;
	private Boolean isOpenToBoarding;
	private Integer boardingDuration;
	private String flightCode;
	private Flight flight;

	public Gate(Integer number) {
		this.id = 0;
		this.number = number;
		this.isFreeToDock = true;
		this.isOpenToBoarding = false;
		this.boardingDuration = 2 * 60;
		this.flightCode = null;
		this.flight = null;
	}

	public long getId() {
		return this.id;
	}

	private void setId(long id) {
		this.id = id;
	}

	public Integer getGateNumber() {
		return this.number;
	}

	private void setGateNumber(Integer gateNumber) {
		this.number = gateNumber;
	}

	public Boolean isFreeToDock() {
		return this.isFreeToDock;
	}

	public void openDocking() {
		this.isFreeToDock = true;
	}

	public void closeDocking() {
		this.isFreeToDock = false;
	}

	public Boolean isOpenToBoarding() {
		return this.isOpenToBoarding;
	}

	public void openBoarding() {
		this.isOpenToBoarding = true;
		this.boardingDuration = 2 * 60;
	}

	public void closeBoarding() {
		this.isOpenToBoarding = false;
	}

	public Integer getBoardingDurationInMinutes() {
		return this.boardingDuration;
	}

	public void setBoardingDurationInMinutes(Integer durationInMinutes) {
		this.boardingDuration = durationInMinutes;
	}

	public String getFlightCode() {
		return this.flightCode;
	}

	public void setFlightCode(String code) {
		this.flightCode = code;
	}

	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public void fromModel(GatesModel model) {
		this.setId((long) model.id);
		this.setGateNumber((Integer) model.getGateNumber());
		if ((Boolean) model.getIsFreeToDock())
			this.openDocking();
		else
			this.closeDocking();
		if ((Boolean) model.getIsOpenToBoarding())
			this.openBoarding();
		else
			this.closeBoarding();
		this.setBoardingDurationInMinutes((Integer) model.getBoardingDuration());
		this.setFlightCode((String) model.getFlightCode());
		if (this.getFlightCode() != this.getFlight().getFlightCode())
			this.setFlight(null);
	}

	public GatesModel toModel() {
		GatesModel model = new GatesModel();

		model.setGateNumber(this.getGateNumber());
		model.setIsFreeToDock(this.isFreeToDock());
		model.setIsOpenToBoarding(this.isOpenToBoarding());
		model.setBoardingDuration(this.getBoardingDurationInMinutes());
		model.setFlightCode(this.getFlightCode());

		return model;
	}

	public void fromHashMap(HashMap<String, Object> map) {
		this.setGateNumber((Integer) map.get("number"));
		if ((Boolean) map.get("isFreeToDock"))
			this.openDocking();
		else
			this.closeDocking();
		if ((Boolean) map.get("isOpenToBoarding"))
			this.openBoarding();
		else
			this.closeBoarding();
		this.setBoardingDurationInMinutes((Integer) map.get("boardingDurationInMinutes"));
		this.setFlightCode((String) map.get("flightCode"));
		if (this.getFlightCode() != this.getFlight().getFlightCode())
			this.setFlight(null);
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("gateNumber", this.getGateNumber());
		map.put("isFreeToDock", this.getFlightCode());
		map.put("isOpenToBoarding", this.getFlightCode());
		map.put("boardingDuration", this.getFlightCode());
		map.put("flightCode", this.getFlightCode());

		return map;
	}
}
