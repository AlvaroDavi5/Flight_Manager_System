package com.flightmanager.app.services;

import java.util.LinkedList;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.transaction.Transactional;
import com.flightmanager.domain.entities.Gate;
import com.flightmanager.infra.database.models.GatesModel;
import com.flightmanager.infra.database.repositories.GatesRepositoryInterface;

@Service
public class GateService {
	@Autowired
	private GatesRepositoryInterface gatesRepository;

	@Transactional
	public Gate create(GatesModel gateData) {
		try {
			Gate gate = new Gate(null);
			gate.fromModel(this.gatesRepository.save(gateData));

			if (gate == null || gate.getGateNumber() == null) {
				return null;
			}

			return gate;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Gate read(long id) {
		try {
			Gate gate = new Gate(null);
			gate.fromModel(this.gatesRepository.findById(id));

			if (gate == null || gate.getGateNumber() == null) {
				return null;
			}

			return gate;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Gate findByGateNumber(Integer number) {
		try {
			Gate gate = new Gate(null);
			gate.fromModel(this.gatesRepository.findByGateNumber(number));

			if (gate == null || gate.getGateNumber() == null) {
				return null;
			}

			return gate;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Gate update(long id, GatesModel gateData) {
		try {
			Gate gate = new Gate(null);
			GatesModel gateModel = this.gatesRepository.findById(id);
			if (gateData.getGateNumber() != null)
				gateModel.setGateNumber(gateData.getGateNumber());
			gateModel.setBoardingDuration(gateData.getBoardingDuration());
			gateModel.setFlightCode(gateData.getFlightCode());
			gateModel.setIsFreeToDock(gateData.getIsFreeToDock());
			gateModel.setIsOpenToBoarding(gateData.getIsFreeToDock());

			if (gate == null || gate.getGateNumber() == null) {
				return null;
			}

			return gate;
		} catch (Exception exception) {
			return null;
		}
	}

	@Transactional
	public Boolean delete(long id) {
		try {
			this.gatesRepository.deleteById(id);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	@Transactional
	public LinkedList<Gate> list(long startDate, long endDate) {
		if (startDate != 0 && endDate != 0)
			try {
				LinkedList<GatesModel> gateModels = this.gatesRepository.findAll();
				LinkedList<Gate> gates = new LinkedList<Gate>();

				for (GatesModel gateModel : gateModels) {
					Gate gate = new Gate(null);
					gate.fromModel(gateModel);
					gates.addLast(gate);
				}

				return gates;
			} catch (Exception exception) {
				return null;
			}
		return null;
	}
}
