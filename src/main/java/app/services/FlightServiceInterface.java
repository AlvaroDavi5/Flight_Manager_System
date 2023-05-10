package app.services;

import java.util.LinkedList;
import org.springframework.stereotype.Service;
import infra.database.models.FlightsModel;
import domain.entities.Flight;

@Service
public interface FlightServiceInterface {
	public Flight create(FlightsModel flightData);

	public Flight read(long id);

	public Flight update(FlightsModel flightData);

	public Boolean delete(long id);

	public LinkedList<Flight> list(long startDate, long endDate);
}
