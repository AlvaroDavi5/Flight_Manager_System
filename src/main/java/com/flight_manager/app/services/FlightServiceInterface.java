package com.flight_manager.app.services;

import java.util.LinkedList;
import org.springframework.stereotype.Service;
import com.flight_manager.domain.entities.Flight;
import com.flight_manager.infra.database.models.FlightsModel;

@Service
public interface FlightServiceInterface {
	public Flight create(FlightsModel flightData);

	public Flight read(long id);

	public Flight update(FlightsModel flightData);

	public Boolean delete(long id);

	public LinkedList<Flight> list(long startDate, long endDate);
}
