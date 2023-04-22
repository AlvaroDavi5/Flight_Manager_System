package domain.entities;

import java.util.LinkedList;

public class Airport {
	private String IATA;
	private String ICAO;
	private int gatesAmount;
	private LinkedList<Gate> gatesList;

	public Airport(String IATA, String ICAO, int gatesAmount) {
		this.IATA = IATA;
		this.ICAO = ICAO;
		this.gatesAmount = gatesAmount;
		this.gatesList = new LinkedList<Gate>();

		for (int i = 1; i < gatesAmount + 1; i++) {
			this.gatesList.addLast(new Gate(i));
		}
	}

	public String getIATA() {
		return this.IATA;
	}

	public String getICAO() {
		return this.ICAO;
	}

	public int getGatesAmount() {
		return this.gatesAmount;
	}

	public LinkedList<Gate> getGatesList() {
		return this.gatesList;
	}

	public Gate getGate(int gateNumber) {
		return this.gatesList.get(gateNumber - 1);
	}

	public void updateGate(int gateNumber, String status) {
		Gate gate = this.getGate(gateNumber);
		gate.setStatus(status);
	}

	public void updateGate(int gateNumber, String status, String flightCode) {
		Gate gate = this.getGate(gateNumber);
		gate.setStatus(status);
		gate.setFlightCode(flightCode);
	}
}
