package com.flightmanager.infra.database.models;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "flights")
public class FlightsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "code", length = 15, nullable = false, unique = true)
	private String code;
	@Column(name = "gateNumber", nullable = true, unique = false)
	private int gateNumber = 0;
	@Column(name = "flightStatus", length = 20, nullable = false, unique = false)
	private String flightStatus;
	@Column(name = "logisticStatus", length = 20, nullable = false, unique = false)
	private String logisticStatus;
	@Column(name = "departureAirportCode", length = 10, nullable = true, unique = false)
	private String departureAirportCode = null;
	@Column(name = "departureHorizontalDistance", nullable = true, unique = false)
	private Integer departureHorizontalDistance = 0;
	@Column(name = "departureVerticalDistance", nullable = true, unique = false)
	private Integer departureVerticalDistance = 0;
	@Column(name = "departureAirportCandidates", nullable = true, unique = false)
	private int departureAirportCandidates = 0;
	@Column(name = "departureTime", nullable = true, unique = false)
	private int departureTime = 0;
	@Column(name = "arrivalAirportCode", length = 10, nullable = true, unique = false)
	private String arrivalAirportCode = null;
	@Column(name = "arrivalHorizontalDistance", nullable = true, unique = false)
	private Integer arrivalHorizontalDistance = 0;
	@Column(name = "arrivalVerticalDistance", nullable = true, unique = false)
	private Integer arrivalVerticalDistance = 0;
	@Column(name = "arrivalAirportCandidates", nullable = true, unique = false)
	private int arrivalAirportCandidates = 0;
	@Column(name = "arrivalTime", nullable = true, unique = false)
	private int arrivalTime = 0;

	public FlightsModel() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	};

	public int getGateNumber() {
		return this.gateNumber;
	}

	public void setGateNumber(int gateNumber) {
		this.gateNumber = gateNumber;
	};

	public String getFlightStatus() {
		return this.flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	};

	public String getLogisticStatus() {
		return this.logisticStatus;
	}

	public void setLogisticStatus(String logisticStatus) {
		this.logisticStatus = logisticStatus;
	};

	public String getDepartureAirportCode() {
		return this.departureAirportCode;
	}

	public void setDepartureAirportCode(String departureAirportCode) {
		this.departureAirportCode = departureAirportCode;
	};

	public Integer getDepartureHorizontalDistance() {
		return this.departureHorizontalDistance;
	}

	public void setDepartureHorizontalDistance(Integer departureHorizontalDistance) {
		this.departureHorizontalDistance = departureHorizontalDistance;
	};

	public Integer getDepartureVerticalDistance() {
		return this.departureVerticalDistance;
	}

	public void setDepartureVerticalDistance(Integer departureVerticalDistance) {
		this.departureVerticalDistance = departureVerticalDistance;
	};

	public int getDepartureAirportCandidates() {
		return this.departureAirportCandidates;
	}

	public void setDepartureAirportCandidates(int departureAirportCandidates) {
		this.departureAirportCandidates = departureAirportCandidates;
	};

	public int getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	};

	public String getArrivalAirportCode() {
		return this.arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	};

	public Integer getArrivalHorizontalDistance() {
		return this.arrivalHorizontalDistance;
	}

	public void setArrivalHorizontalDistance(Integer arrivalHorizontalDistance) {
		this.arrivalHorizontalDistance = arrivalHorizontalDistance;
	};

	public Integer getArrivalVerticalDistance() {
		return this.arrivalVerticalDistance;
	}

	public void setArrivalVerticalDistance(Integer arrivalVerticalDistance) {
		this.arrivalVerticalDistance = arrivalVerticalDistance;
	};

	public int getArrivalAirportCandidates() {
		return this.arrivalAirportCandidates;
	}

	public void setArrivalAirportCandidates(int arrivalAirportCandidates) {
		this.arrivalAirportCandidates = arrivalAirportCandidates;
	};

	public int getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	};
}
