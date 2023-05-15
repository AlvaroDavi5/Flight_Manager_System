package com.flightmanager.domain.enums;

public enum LogisticStatusEnum {
	REQUESTING_LAND, // airplane requesting to land
	ALLOWED_TO_LAND, // airplane allowed to land
	REJECTED_TO_LAND, // airplane rejected to land
	LANDED, // airplane landed
	REQUESTING_TAKEOFF, // airplane requesting to takeoff
	ALLOWED_TO_TAKEOFF, // airplane allowed to takeoff
	REJECTED_TO_TAKEOFF, // airplane rejected to takeoff
	TAKED_OFF, // airplane taked off
	DIVERTED, // flight diverted to a different airport than originally planned
	TAXIING, // airplane currently moving on the ground and airstrip in use
	IN_TRANSIT, // airplane currently in route to destination
	REQUESTING_MAINTENANCE, // airplane requesting maintenance
	MAINTENANCE, // airplane undergoing routine or unscheduled maintenance
}
