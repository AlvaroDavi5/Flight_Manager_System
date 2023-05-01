package domain.enums;

public enum PanelStatusEnum {
	SCHEDULED, // flight scheduled to depart or arrive at a specific time
	ON_TIME, // airplane currently expected to flight at its scheduled time
	DELAYED, // airplane behind schedule and its arrival/departure time has been delayed
	DEBOARDING, // passengers exiting the airplane
	BOARDING, // opened boarding the airplane
	CLOSED, // boarding closed
	IN_TRANSIT, // currently in route to destination
	CANCELLED, // flight cancelled and airplane not used for the scheduled flight
}
