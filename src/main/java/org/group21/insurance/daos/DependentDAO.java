package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Dependent;

import java.util.List;
import java.util.Optional;

public class DependentDAO implements Dao<Dependent> {
	EntityManager entityManager;
	CustomerDAO customerDAO;
	
	@Override
	public Optional<Dependent> get(String id) {
		// Get dependent from database
		return Optional.empty();
	}
	
	@Override
	public List<Dependent> getAll() {
		return null;
	}
	
	@Override
	public void save(Dependent dependent) {
		// Save dependent to database
	}

	@Override
	public void update(Dependent dependent) {
		// Update dependent in database
	}

	@Override
	public void delete(Dependent dependent) {
		// Delete dependent from database
	}
}
