package com.flightmanager.app.strategies;

import com.flightmanager.domain.enums.PanelStatusEnum;
import com.flightmanager.domain.enums.FlightStatusEnum;
import com.flightmanager.domain.enums.LogisticStatusEnum;
import com.flightmanager.domain.entities.Flight;
import com.flightmanager.domain.entities.Gate;
import com.flightmanager.app.services.FlightManagerService;

public class FlightManagerFluxStrategy {
	FlightManagerService flightManagerService;

	public FlightManagerFluxStrategy(FlightManagerService flightManagerService) {
		this.flightManagerService = flightManagerService;
	}

	public void manageFlux(Flight flight) {
		FlightStatusEnum flightStatus = flight.getFlightStatus();
		LogisticStatusEnum logisticStatus = flight.getLogisticStatus();
		PanelStatusEnum panelStatus = flight.getpanelStatus();
		Boolean isRegistered = this.flightManagerService.isRegistered(flight);

		if (logisticStatus == LogisticStatusEnum.REQUESTING_LAND) {

			if (flightStatus == FlightStatusEnum.SCHEDULED && isRegistered) {
				Gate freeGate = this.flightManagerService.getFreeGate();
				Boolean isAirstripFreeToLand = this.flightManagerService.isAirstripFreeToLand();

				panelStatus = PanelStatusEnum.ON_TIME;
				if (freeGate != null) {
					flight.setGateNumber(freeGate.getGateNumber());
					freeGate.closeDocking();
					freeGate.setFlightCode(flight.getFlightCode());
					freeGate.setFlight(flight);

					if (isAirstripFreeToLand) {
						this.flightManagerService.closeAirstrip();
						logisticStatus = LogisticStatusEnum.ALLOWED_TO_LAND;
					} else {
						logisticStatus = LogisticStatusEnum.REJECTED_TO_LAND;
						flightStatus = FlightStatusEnum.HOLDING;
					}
				} else {
					logisticStatus = LogisticStatusEnum.REJECTED_TO_LAND;
					flightStatus = FlightStatusEnum.HOLDING;
				}
			} else if (flightStatus == FlightStatusEnum.HOLDING) {
				Gate gate = this.flightManagerService.findGateByNumber(flight.getGateNumber());
				Boolean isAirstripFreeToLand = this.flightManagerService.isAirstripFreeToLand();

				if (gate == null) {
					Gate freeGate = this.flightManagerService.getFreeGate();
					gate = freeGate;
				}
				if (gate != null && isAirstripFreeToLand) {
					this.flightManagerService.closeAirstrip();
					flight.setGateNumber(gate.getGateNumber());
					gate.closeDocking();
					gate.setFlightCode(flight.getFlightCode());
					gate.setFlight(flight);
					logisticStatus = LogisticStatusEnum.ALLOWED_TO_LAND;
				}
			} else {
				logisticStatus = LogisticStatusEnum.DIVERTED;
				flightStatus = FlightStatusEnum.CANCELLED;
				panelStatus = PanelStatusEnum.CANCELLED;
			}
		} else if (logisticStatus == LogisticStatusEnum.LANDED) {
			this.flightManagerService.openAirstrip();
			Gate gate = this.flightManagerService.findGateByNumber(flight.getGateNumber());

			logisticStatus = LogisticStatusEnum.TAXIING;
			if (flight.getArrivalAirportCode() == this.flightManagerService.getAirportICAO()) {
				gate.closeBoarding();
				flightStatus = FlightStatusEnum.CONCLUDED;
			} else {
				gate.openBoarding();
				flightStatus = FlightStatusEnum.ARRIVED;
			}
		} else if (logisticStatus == LogisticStatusEnum.REQUESTING_TAKEOFF) {

			if (flightStatus == FlightStatusEnum.READY) {
				Boolean isAirstripFreeToLand = this.flightManagerService.isAirstripFreeToLand();
				Gate gate = this.flightManagerService.findGateByNumber(flight.getGateNumber());

				if (isAirstripFreeToLand && !gate.isOpenToBoarding()) {
					this.flightManagerService.closeAirstrip();
					logisticStatus = LogisticStatusEnum.ALLOWED_TO_TAKEOFF;
				} else {
					logisticStatus = LogisticStatusEnum.REJECTED_TO_TAKEOFF;
					flightStatus = FlightStatusEnum.GROUNDED;
				}
			} else {
				logisticStatus = LogisticStatusEnum.REJECTED_TO_TAKEOFF;
				flightStatus = FlightStatusEnum.GROUNDED;
			}
		} else if (logisticStatus == LogisticStatusEnum.TAKED_OFF) {
			Gate gate = this.flightManagerService.findGateByNumber(flight.getGateNumber());
			gate.closeBoarding();
			gate.openDocking();

			logisticStatus = LogisticStatusEnum.IN_TRANSIT;
			flightStatus = FlightStatusEnum.DEPARTED;
			panelStatus = PanelStatusEnum.IN_TRANSIT;

			this.flightManagerService.openAirstrip();
		} else if (logisticStatus == LogisticStatusEnum.REQUESTING_MAINTENANCE) {
			logisticStatus = LogisticStatusEnum.MAINTENANCE;
			flightStatus = FlightStatusEnum.GROUNDED;
		} else {
			if (flightStatus == FlightStatusEnum.CLOSED)
				panelStatus = PanelStatusEnum.CLOSED;
		}

		// TODO - add status
		/*
			FlightStatusEnum.LOADING

			FlightStatusEnum.OPENED
			PanelStatusEnum.DEBOARDING
			PanelStatusEnum.BOARDING

			PanelStatusEnum.SCHEDULED
			PanelStatusEnum.DELAYED
		 */

		flight.setpanelStatus(panelStatus);
		flight.setFlightStatus(flightStatus);
		flight.setLogisticStatus(logisticStatus);
	}
}
