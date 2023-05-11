package com.flightmanager.infra.database.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "gates")
public class GatesModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "gateNumber", nullable = false, unique = true)
	private int gateNumber;
	@Column(name = "isFreeToDock", nullable = true, unique = false)
	private boolean isFreeToDock = true;
	@Column(name = "isOpenToBoarding", nullable = true, unique = false)
	private boolean isOpenToBoarding = false;
	@Column(name = "boardingDuration", nullable = true, unique = false)
	private int boardingDuration = 120;
	@Column(name = "flightCode", length = 15, nullable = true, unique = false)
	private String flightCode = null;

	public GatesModel() {
	}

	public int getGateNumber() {
		return this.gateNumber;
	};

	public void setGateNumber(int gateNumber) {
		this.gateNumber = gateNumber;
	};

	public boolean getIsFreeToDock() {
		return this.isFreeToDock;
	};

	public void setIsFreeToDock(boolean isFreeToDock) {
		this.isFreeToDock = isFreeToDock;
	};

	public boolean getIsOpenToBoarding() {
		return this.isOpenToBoarding;
	};

	public void setIsOpenToBoarding(boolean isOpenToBoarding) {
		this.isOpenToBoarding = isOpenToBoarding;
	};

	public int getBoardingDuration() {
		return this.boardingDuration;
	};

	public void setBoardingDuration(int boardingDuration) {
		this.boardingDuration = boardingDuration;
	};

	public String getFlightCode() {
		return this.flightCode;
	};

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	};
}
