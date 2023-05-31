package com.flightmanager.domain.entities;

import java.util.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.HashMap;

public class Airport {
	private String ICAO;
	private String IATA;
	private Boolean isAirstripFree;
	private long airstripCloseTime;
	private Boolean isSafeToFlight;
	private Integer gatesAmount;
	private LinkedList<Gate> gatesList;

	public Airport(String ICAO, String IATA, Integer gatesAmount) {
		this.IATA = IATA;
		this.ICAO = ICAO;
		this.isAirstripFree = true;
		this.isSafeToFlight = true;
		this.gatesAmount = gatesAmount;
		this.gatesList = new LinkedList<Gate>();

		for (Integer i = 1; i < gatesAmount + 1; i++) {
			this.gatesList.addLast(new Gate(i));
			// TODO - save on database
		}
	}

	public String getICAO() {
		return this.ICAO;
	}

	private void setICAO(String ICAO) {
		this.ICAO = ICAO;
	}

	public String getIATA() {
		return this.IATA;
	}

	public void setIATA(String IATA) {
		this.IATA = IATA;
	}

	public Boolean isAirstripFree() {
		return this.isAirstripFree;
	}

	public void openAirstrip() {
		this.isAirstripFree = true;
		this.airstripCloseTime = 0;
	}

	public void closeAirstrip() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		this.isAirstripFree = false;
		this.airstripCloseTime = (calendar.getTime()).getTime();
	}

	public long getAirstripCloseTime() {
		return this.airstripCloseTime;
	}

	public Boolean isSafeToFlight() {
		return this.isSafeToFlight;
	}

	public void safeToFlight() {
		this.isSafeToFlight = true;
	}

	public void unsafeToFlight() {
		this.isSafeToFlight = false;
	}

	public Integer getGatesAmount() {
		return this.gatesAmount;
	}

	private void setGatesAmount(Integer amount) {
		this.gatesAmount = amount;
	}

	public LinkedList<Gate> getGatesList() {
		return this.gatesList;
	}

	public Gate getGate(Integer gateNumber) {
		return this.gatesList.get(gateNumber - 1);
	}

	public Gate getLastFreeGate() {
		Gate lastGate = null;
		LinkedList<Gate> gates = this.getGatesList();

		for (Integer i = 0; i < gates.size(); i++) {
			Gate gate = this.gatesList.get(i);
			if (gate.isFreeToDock())
				lastGate = gate;
		}

		return lastGate;
	}

	public Gate findGateByNumber(Integer gateNumber) {
		Gate findedGate = null;
		LinkedList<Gate> gates = this.getGatesList();

		for (Integer i = 0; i < gates.size(); i++) {
			Gate gate = this.gatesList.get(i);
			if (gate.getGateNumber() == gateNumber)
				findedGate = gate;
		}

		return findedGate;
	}

	public void updateGateDocking(Gate gate, Boolean isFreeToDock) {
		if (gate == null)
			return;

		if (isFreeToDock == true)
			gate.openDocking();
		else if (isFreeToDock == false)
			gate.closeDocking();
	}

	public void assignFlightToGate(Flight flight, Gate gate) {
		if (gate != null && flight != null) {
			flight.setGateNumber(gate.getGateNumber());
			gate.setFlightCode(flight.getFlightCode());
			gate.setFlight(flight);
		}
	}

	public void fromHashMap(HashMap<String, Object> map) {
		this.setICAO((String) map.get("ICAO"));
		this.setIATA((String) map.get("IATA"));
		if ((Boolean) map.get("airstripFree"))
			this.openAirstrip();
		else
			this.closeAirstrip();
		this.setGatesAmount((Integer) map.get("gatesAmount"));
	}

	public HashMap<String, Object> toHashMap() {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("ICAO", this.getICAO());
		map.put("IATA", this.getIATA());
		map.put("airstripFree", this.isAirstripFree());
		map.put("gatesAmount", this.getGatesAmount());

		return map;
	}
}
