package com.flightmanager.infra.database.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "gates")
public class GatesModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id;

	@Column(name = "gateNumber", nullable = false, unique = true)
	private Integer gateNumber;
	@Column(name = "isFreeToDock", nullable = true, unique = false)
	private Boolean isFreeToDock = true;
	@Column(name = "isOpenToBoarding", nullable = true, unique = false)
	private Boolean isOpenToBoarding = false;
	@Column(name = "boardingDuration", nullable = true, unique = false)
	private Integer boardingDuration = 120;
	@Column(name = "flightCode", length = 15, nullable = true, unique = false)
	private String flightCode = null;

	public GatesModel() {
	}

	public Integer getGateNumber() {
		return this.gateNumber;
	};

	public void setGateNumber(Integer gateNumber) {
		this.gateNumber = gateNumber;
	};

	public Boolean getIsFreeToDock() {
		return this.isFreeToDock;
	};

	public void setIsFreeToDock(Boolean isFreeToDock) {
		this.isFreeToDock = isFreeToDock;
	};

	public Boolean getIsOpenToBoarding() {
		return this.isOpenToBoarding;
	};

	public void setIsOpenToBoarding(Boolean isOpenToBoarding) {
		this.isOpenToBoarding = isOpenToBoarding;
	};

	public Integer getBoardingDuration() {
		return this.boardingDuration;
	};

	public void setBoardingDuration(Integer boardingDuration) {
		this.boardingDuration = boardingDuration;
	};

	public String getFlightCode() {
		return this.flightCode;
	};

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	};
}
