package com.flightmanager.domain.enums;

public enum FlightStatusEnum {
	SCHEDULED, // flight scheduled to depart or arrive at a specific time
	HOLDING, // airplane currently circling the airport in a holding pattern
	READY, // airplane fully operational and ready to fly
	DEPARTED, // flight departed and is in route to its destination
	ARRIVED, // flight arrived at its destination
	GROUNDED, // airplane unable to fly due to maintenance or other issues
	OPENED, // boarding opened
	CLOSED, // boarding closed
	LOADING, // cargo or baggage is being loaded onto the airplane
	CANCELLED, // flight cancelled and airplane not used for the scheduled flight
	CONCLUDED, // flight concluded, nothing to do next
}
