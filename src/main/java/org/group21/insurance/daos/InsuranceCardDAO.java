package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceCard;

import java.util.List;
import java.util.Optional;

public class InsuranceCardDAO implements Dao<InsuranceCard>{
	EntityManager entityManager;
	
	@Override
	public Optional<InsuranceCard> get(String id) {
			// Get insurance card from database
			return Optional.empty();
	}
	
	@Override
	public List<InsuranceCard> getAll() {
			// Get all insurance cards from database
			return null;
	}

	@Override
	public void save(InsuranceCard insuranceCard) {
			// Save insurance card to database
	}

	@Override
	public void update(InsuranceCard insuranceCard) {
			// Update insurance card in database
	}

	@Override
	public void delete(InsuranceCard insuranceCard) {
			// Delete insurance card from database
	}
}
