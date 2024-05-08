package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Dependent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DependentDAO implements Dao<Dependent> {
	EntityManager entityManager;
	CustomerDAO customerDAO;
	
	public DependentDAO() {
	}
	
	public DependentDAO(EntityManager entityManager, CustomerDAO customerDAO) {
		this.entityManager = entityManager;
		this.customerDAO = customerDAO;
	}
	
	@Override
	public Optional<Dependent> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<Dependent> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(Dependent Dependent) {
	}
	
	@Override
	public void update(Dependent Dependent) {
	}
	
	@Override
	public void delete(Dependent Dependent) {
	}
}
