package com.flightmanager.infra.database.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.flightmanager.domain.entities.Flight;

@Entity
@Table(name = "flights")
public class FlightsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "code", length = 10, nullable = false, unique = false)
	private String code;
	private int gateNumber;
	private String flightStatus;
	private String logisticStatus;
	private String departureAirportCode;
	private Integer departureHorizontalDistance;
	private Integer departureVerticalDistance;
	private int departureAirportCandidates;
	private int departureTime;
	private String arrivalAirportCode;
	private Integer arrivalHorizontalDistance;
	private Integer arrivalVerticalDistance;
	private int arrivalAirportCandidates;
	private int arrivalTime;

	public FlightsModel() {
	}

	public Flight toEntity() {
		Flight flight = new Flight(this.flightStatus);
		flight.setGateNumber(this.gateNumber);
		flight.setFlightStatus(this.flightStatus);
		flight.setLogisticStatus(this.logisticStatus);
		flight.setDepartureAirportCode(this.departureAirportCode);
		flight.setDepartureDistanceInMeters(this.departureVerticalDistance, this.departureHorizontalDistance);
		flight.setDepartureAirportCandidates(this.departureAirportCandidates);
		flight.setDepartureTimeInEpoch(this.departureTime);
		flight.setArrivalAirportCode(this.arrivalAirportCode);
		flight.setArrivalDistanceInMeters(this.arrivalVerticalDistance, this.arrivalHorizontalDistance);
		flight.setArrivalAirportCandidates(this.arrivalAirportCandidates);
		flight.setArrivalTimeInEpoch(this.arrivalTime);

		return flight;
	};

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
