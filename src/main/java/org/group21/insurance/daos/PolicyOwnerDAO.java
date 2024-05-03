package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.PolicyOwner;

import java.util.List;
import java.util.Optional;

public class PolicyOwnerDAO implements Dao<PolicyOwner> {
	EntityManager entityManager;
	CustomerDAO customerDAO;
	
	@Override
	public Optional<PolicyOwner> get(String id) {
		// Get customer from database
		return Optional.empty();
	}
	
	@Override
	public List<PolicyOwner> getAll() {
		// Get all customers from database
		return null;
	}
	
	@Override
	public void save(PolicyOwner policyOwner) {
		// Save customer to database
	}
	
	@Override
	public void update(PolicyOwner policyOwner) {
		// Update customer in database
	}
	
	@Override
	public void delete(PolicyOwner policyOwner) {
		// Delete customer from database
	}
}
