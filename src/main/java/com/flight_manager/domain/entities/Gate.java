package com.flight_manager.domain.entities;

import java.util.HashMap;

public class Gate {
	private int number;
	private boolean isFreeToDock;
	private boolean isOpenToBoarding;
	private int boardingDuration;
	private String flightCode;
	private Flight flight;

	public Gate(int number) {
		this.number = number;
		this.isFreeToDock = true;
		this.isOpenToBoarding = false;
		this.boardingDuration = 2 * 60;
		this.flightCode = null;
		this.flight = null;
	}

	public int getGateNumber() {
		return this.number;
	}

	private void setGateNumber(int gateNumber) {
		this.number = gateNumber;
	}

	public boolean isFreeToDock() {
		return this.isFreeToDock;
	}

	public void openDocking() {
		this.isFreeToDock = true;
	}

	public void closeDocking() {
		this.isFreeToDock = false;
	}

	public boolean isOpenToBoarding() {
		return this.isOpenToBoarding;
	}

	public void openBoarding() {
		this.isOpenToBoarding = true;
		this.boardingDuration = 2 * 60;
	}

	public void closeBoarding() {
		this.isOpenToBoarding = false;
	}

	public int getBoardingDurationInMinutes() {
		return this.boardingDuration;
	}

	public void setBoardingDurationInMinutes(int durationInMinutes) {
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

	public void fromHashMap(HashMap<String, Object> map) {
		this.setGateNumber((int) map.get("number"));
		if ((boolean) map.get("isFreeToDock"))
			this.openDocking();
		else
			this.closeDocking();
		if ((boolean) map.get("isOpenToBoarding"))
			this.openBoarding();
		else
			this.closeBoarding();
		this.setBoardingDurationInMinutes((int) map.get("boardingDurationInMinutes"));
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
