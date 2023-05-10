package com.flightmanager.infra.database.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "airports")
public class AirportsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String ICAO;
	private String IATA;
	private boolean isAirstripFree;
	private boolean isSafeToFlight;
	private int gatesAmount;

	public AirportsModel() {
	}

	public String getICAO() {
		return this.ICAO;
	};

	public void setICAO(String ICAO) {
		this.ICAO = ICAO;
	};

	public String getIATA() {
		return this.IATA;
	};

	public void setIATA(String IATA) {
		this.IATA = IATA;
	};

	public boolean getIsAirstripFree() {
		return this.isAirstripFree;
	};

	public void setIsAirstripFree(boolean isAirstripFree) {
		this.isAirstripFree = isAirstripFree;
	};

	public boolean getIsSafeToFlight() {
		return this.isSafeToFlight;
	};

	public void setIsSafeToFlight(boolean isSafeToFlight) {
		this.isSafeToFlight = isSafeToFlight;
	};

	public int getGatesAmount() {
		return this.gatesAmount;
	};

	public void setGatesAmount(int gatesAmount) {
		this.gatesAmount = gatesAmount;
	};
}
