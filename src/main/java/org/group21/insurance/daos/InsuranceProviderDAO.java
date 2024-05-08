package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InsuranceProviderDAO implements Dao<InsuranceProvider> {
	EntityManager entityManager;
	
	public InsuranceProviderDAO() {
	}
	
	public InsuranceProviderDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<InsuranceProvider> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<InsuranceProvider> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(InsuranceProvider insuranceProvider) {
	}
	
	@Override
	public void update(InsuranceProvider insuranceProvider) {
	}
	
	@Override
	public void delete(InsuranceProvider insuranceProvider) {
	}
}
