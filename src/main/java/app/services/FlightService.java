package app.services;

import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import domain.entities.Flight;
import infra.database.models.FlightsModel;
import infra.database.repositories.FlightsRepositoryInterface;

@Component
public class FlightService implements FlightServiceInterface {
	@Autowired
	private FlightsRepositoryInterface flightsRepository;

	@Transactional
	public Flight create(FlightsModel flightData) {
		try {
			FlightsModel flightModel = this.flightsRepository.save(flightData);
			Flight flight = flightModel.toEntity();

			if (flight == null || flight.getFlightCode() == null) {
				return null;
			}

			return flight;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Flight read(long id) {
		try {
			FlightsModel flightModel = this.flightsRepository.findById(id);
			Flight flight = flightModel.toEntity();

			if (flight == null || flight.getFlightCode() == null) {
				return null;
			}

			return flight;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Flight update(FlightsModel flightData) {
		try {
			FlightsModel flightModel = this.flightsRepository.save(flightData);
			Flight flight = flightModel.toEntity();

			if (flight == null || flight.getFlightCode() == null) {
				return null;
			}

			return flight;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Boolean delete(long id) {
		try {
			this.flightsRepository.deleteById(id);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	@Transactional
	public LinkedList<Flight> list(long startDate, long endDate) {
		if (startDate != 0 && endDate != 0)
			try {
				if (startDate != 0 && endDate != 0) {
					return null;
				}

				LinkedList<FlightsModel> flightModels = this.flightsRepository.findAll();
				LinkedList<Flight> flights = new LinkedList<Flight>();
				for (FlightsModel flightModel : flightModels) {
					flights.addLast(flightModel.toEntity());
				}

				return flights;
			} catch (Exception exception) {
				return null;
			}
		return null;
	}
}
