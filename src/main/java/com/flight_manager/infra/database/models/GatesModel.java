package com.flight_manager.infra.database.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "gates")
public class GatesModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private int gateNumber;
	private boolean isFreeToDock;
	private boolean isOpenToBoarding;
	private int boardingDuration;
	private String flightCode;

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
