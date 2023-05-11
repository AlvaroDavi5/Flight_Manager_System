package com.flightmanager.infra.database.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "airports")
public class AirportsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "ICAO", length = 10, nullable = false, unique = false)
	private String ICAO;
	@Column(name = "IATA", length = 10, nullable = false, unique = false)
	private String IATA;
	@Column(name = "isAirstripFree", nullable = true, unique = false)
	private boolean isAirstripFree = true;
	@Column(name = "isSafeToFlight", nullable = true, unique = false)
	private boolean isSafeToFlight = true;
	@Column(name = "gatesAmount", nullable = false, unique = false)
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
