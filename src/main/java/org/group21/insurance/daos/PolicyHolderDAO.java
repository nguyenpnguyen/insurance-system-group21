package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.PolicyHolder;

import java.util.List;
import java.util.Optional;

public class PolicyHolderDAO implements Dao<PolicyHolder>{
	EntityManager entityManager;
	CustomerDAO customerDAO;
	
	@Override
	public Optional<PolicyHolder> get(String id) {
		// Get policy holder from database
		return Optional.empty();
	}
	
	@Override
	public List<PolicyHolder> getAll() {
		// Get all policy holders from database
		return null;
	}
	
	@Override
	public void save(PolicyHolder policyHolder) {
			// Save policy holder to database
	}

	@Override
	public void update(PolicyHolder policyHolder) {
			// Update policy holder in database
	}

	@Override
	public void delete(PolicyHolder policyHolder) {
			// Delete policy holder from database
	}
}
