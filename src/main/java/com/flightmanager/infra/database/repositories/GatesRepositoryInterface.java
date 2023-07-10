package com.flightmanager.infra.database.repositories;

import java.util.LinkedList;
import org.springframework.data.jpa.repository.JpaRepository;
import com.flightmanager.infra.database.models.GatesModel;

public interface GatesRepositoryInterface extends JpaRepository<GatesModel, Long> {
	<GM extends GatesModel> GM save(GM entity);

	GatesModel findById(long id);

	GatesModel findByGateNumber(Integer gateNumber);

	LinkedList<GatesModel> findAll();

	long count();

	void delete(GatesModel entity);

	boolean existsById(long id);
}
