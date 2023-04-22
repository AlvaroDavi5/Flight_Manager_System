package domain.enums;

public enum FlightStatusEnum {
	READY, // airplane fully operational and ready to fly
	DEPARTED, // flight departed and is in route to its destination
	IN_TRANSIT, // airplane currently in route to destination
	ARRIVED, // flight arrived at its destination
	HOLDING, // airplane currently circling the airport in a holding pattern
	GROUNDED, // airplane unable to fly due to maintenance or other issues
	BOARDING, // passengers boarding the airplane
	DEBOARDING, // passengers exiting the airplane
	MAINTENANCE, // airplane undergoing routine or unscheduled maintenance
	REFUELING, // airplane being refueled in preparation for a flight
	LOADING, // cargo or baggage is being loaded onto the airplane
}
