package domain.enums;

public enum LogisticStatusEnum {
	REQUESTING_LAND, // airplane requesting
	ALLOWED_TO_LAND, // airplane requesting
	LANDED, // airplane landed
	REQUESTING_TAKEOFF, // airplane requesting
	ALLOWED_TO_TAKEOFF, // airplane requesting
	TAKED_OFF, // airplane taked off
	REQUESTING_MAINTENANCE, // airplane requesting maintenance
	REQUESTING_REFUELING, // airplane requesting refueling
	TAXIING, // airplane currently moving on the ground and airstrip in use
	REJECTED, // land rejected
}
