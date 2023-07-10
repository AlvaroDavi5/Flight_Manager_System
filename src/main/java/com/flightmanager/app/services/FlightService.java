package com.flightmanager.app.services;

import java.util.LinkedList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.infra.database.models.FlightsModel;
import com.flightmanager.infra.database.repositories.FlightsRepositoryInterface;

@Service
public class FlightService {
	@Autowired
	private FlightsRepositoryInterface flightsRepository;

	@Transactional
	public Flight create(FlightsModel flightData) {
		try {
			Flight flight = new Flight(null);
			flight.fromModel(this.flightsRepository.save(flightData));

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
			Flight flight = new Flight(null);
			flight.fromModel(this.flightsRepository.findById(id));

			if (flight == null || flight.getFlightCode() == null) {
				return null;
			}

			return flight;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Flight findByCode(String code) {
		try {
			Flight flight = new Flight(null);
			flight.fromModel(this.flightsRepository.findByCode(code));

			if (flight == null || flight.getFlightCode() == null) {
				return null;
			}

			return flight;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Flight update(long id, FlightsModel flightData) {
		try {
			Flight flight = new Flight(null);
			FlightsModel flightModel = this.flightsRepository.findById(id);
			if (flightData.getCode() != null)
				flightModel.setCode(flightData.getCode());
			if (flightData.getGateNumber() != null)
				flightModel.setGateNumber(flightData.getGateNumber());
			if (flightData.getFlightStatus() != null)
				flightModel.setFlightStatus(flightData.getFlightStatus());
			if (flightData.getLogisticStatus() != null)
				flightModel.setLogisticStatus(flightData.getLogisticStatus());
			if (flightData.getDepartureAirportCode() != null)
				flightModel.setDepartureAirportCode(flightData.getDepartureAirportCode());
			flightModel.setDepartureHorizontalDistance(flightData.getDepartureHorizontalDistance());
			flightModel.setDepartureVerticalDistance(flightData.getDepartureVerticalDistance());
			flightModel.setDepartureAirportCandidates(flightData.getDepartureAirportCandidates());
			flightModel.setDepartureTime(flightData.getDepartureTime());
			if (flightData.getArrivalAirportCode() != null)
				flightModel.setArrivalAirportCode(flightData.getArrivalAirportCode());
			flightModel.setArrivalHorizontalDistance(flightData.getArrivalHorizontalDistance());
			flightModel.setArrivalVerticalDistance(flightData.getArrivalVerticalDistance());
			flightModel.setArrivalAirportCandidates(flightData.getArrivalAirportCandidates());
			flightModel.setArrivalTime(flightData.getArrivalTime());
			flight.fromModel(flightModel);

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
				LinkedList<FlightsModel> flightModels = this.flightsRepository.findAll();
				LinkedList<Flight> flights = new LinkedList<Flight>();

				for (FlightsModel flightModel : flightModels) {
					Flight flight = new Flight(null);
					flight.fromModel(flightModel);
					flights.addLast(flight);
				}

				return flights;
			} catch (Exception exception) {
				return null;
			}
		return null;
	}
}
