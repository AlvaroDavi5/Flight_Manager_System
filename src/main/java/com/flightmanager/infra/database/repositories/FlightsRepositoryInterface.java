package com.flightmanager.infra.database.repositories;

import java.util.LinkedList;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.flightmanager.infra.database.models.FlightsModel;

@Repository
public interface FlightsRepositoryInterface extends CrudRepository<FlightsModel, Long> {
	<FM extends FlightsModel> FM save(FM entity);

	FlightsModel findById(long id);

	LinkedList<FlightsModel> findAll();

	long count();

	void delete(FlightsModel entity);

	boolean existsById(long id);
}
