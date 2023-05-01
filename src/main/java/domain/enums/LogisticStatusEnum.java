package domain.enums;

public enum LogisticStatusEnum {
	REQUESTING_LAND, // airplane requesting to land
	ALLOWED_TO_LAND, // airplane allowed to land
	REJECTED_TO_LAND, // airplane rejected to land
	LANDED, // airplane landed
	REQUESTING_TAKEOFF, // airplane requesting to takeoff
	ALLOWED_TO_TAKEOFF, // airplane allowed to takeoff
	REJECTED_TO_TAKEOFF, // airplane rejected to takeoff
	TAKED_OFF, // airplane taked off
	TAXIING, // airplane currently moving on the ground and airstrip in use
	REQUESTING_MAINTENANCE, // airplane requesting maintenance
	REQUESTING_REFUELING, // airplane requesting refueling
	MAINTENANCE, // airplane undergoing routine or unscheduled maintenance
	REFUELING, // airplane being refueled in preparation for a flight
}
