package com.flightmanager.infra.database.repositories;

import java.util.LinkedList;
import org.springframework.data.jpa.repository.JpaRepository;
import com.flightmanager.infra.database.models.FlightsModel;

public interface FlightsRepositoryInterface extends JpaRepository<FlightsModel, Long> {
	<FM extends FlightsModel> FM save(FM entity);

	FlightsModel findById(long id);

	LinkedList<FlightsModel> findAll();

	long count();

	void delete(FlightsModel entity);

	boolean existsById(long id);
}
