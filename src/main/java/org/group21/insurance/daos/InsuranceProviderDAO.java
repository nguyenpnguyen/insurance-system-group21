package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceProvider;

import java.util.List;
import java.util.Optional;

public class InsuranceProviderDAO implements Dao<InsuranceProvider> {
	EntityManager entityManager;
	
	@Override
	public Optional<InsuranceProvider> get(String id) {
		// Get insurance provider from database
		return Optional.empty();
	}
	
	@Override
	public List<InsuranceProvider> getAll() {
		// Get all insurance providers from database
		return null;
	}
	
	@Override
	public void save(InsuranceProvider insuranceProvider) {
		// Save insurance provider to database
	}
	
	@Override
	public void update(InsuranceProvider insuranceProvider) {
		// Update insurance provider in database
	}
	
	@Override
	public void delete(InsuranceProvider insuranceProvider) {
		// Delete insurance provider from database
	}
}
