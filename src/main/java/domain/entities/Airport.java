package domain.entities;

import java.util.LinkedList;
import java.util.HashMap;

public class Airport {
	private String ICAO;
	private String IATA;
	private boolean isAirstripFree;
	private boolean isSafeToFlight;
	private int gatesAmount;
	private LinkedList<Gate> gatesList;

	public Airport(String ICAO, String IATA, int gatesAmount) {
		this.IATA = IATA;
		this.ICAO = ICAO;
		this.gatesAmount = gatesAmount;
		this.gatesList = new LinkedList<Gate>();

		for (int i = 1; i < gatesAmount + 1; i++) {
			this.gatesList.addLast(new Gate(i));
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

	public boolean isAirstripFree() {
		return this.isAirstripFree;
	}

	public void openAirstrip() {
		this.isAirstripFree = true;
	}

	public void closeAirstrip() {
		this.isAirstripFree = false;
	}

	public boolean isSafeToFlight() {
		return this.isSafeToFlight;
	}

	public void safeToFlight() {
		this.isSafeToFlight = true;
	}

	public void unsafeToFlight() {
		this.isSafeToFlight = false;
	}

	public int getGatesAmount() {
		return this.gatesAmount;
	}

	private void setGatesAmount(int amount) {
		this.gatesAmount = amount;
	}

	public LinkedList<Gate> getGatesList() {
		return this.gatesList;
	}

	public Gate getGate(int gateNumber) {
		return this.gatesList.get(gateNumber - 1);
	}

	public Gate getLastFreeGate() {
		Gate lastGate = null;
		LinkedList<Gate> gates = this.getGatesList();

		for (int i = 0; i < gates.size(); i++) {
			Gate gate = this.gatesList.get(i);
			if (gate.isFreeToDock())
				lastGate = gate;
		}

		return lastGate;
	}

	public void updateGateDocking(Gate gate, boolean isFreeToDock) {
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
		if ((boolean) map.get("airstripFree"))
			this.openAirstrip();
		else
			this.closeAirstrip();
		this.setGatesAmount((int) map.get("gatesAmount"));
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
