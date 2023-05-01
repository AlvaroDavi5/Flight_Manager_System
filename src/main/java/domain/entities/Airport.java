package domain.entities;

import java.util.LinkedList;

public class Airport {
	private String ICAO;
	private String IATA;
	private boolean airstripFree;
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

	public String getIATA() {
		return this.IATA;
	}

	public void setIATA(String IATA) {
		this.IATA = IATA;
	}

	public boolean isAirstripFree() {
		return this.airstripFree;
	}

	public void openAirstrip() {
		this.airstripFree = true;
	}

	public void closeAirstrip() {
		this.airstripFree = false;
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

	public Gate getLastFreeGate() {
		Gate lastGate = null;
		LinkedList<Gate> gates = this.getGatesList();

		for (int i = 0; i < gates.size(); i++) {
			Gate gate = this.gatesList.get(i);
			if (gate.isFree())
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
			flight.setGateNumber(gate.getNumber());
			gate.setFlightCode(flight.getFlightCode());
			gate.setFlight(flight);
		}
	}
}
