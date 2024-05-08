package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.PolicyOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PolicyOwnerDAO implements Dao<PolicyOwner> {
	EntityManager entityManager;
	CustomerDAO customerDAO;
	
	public PolicyOwnerDAO() {
	}
	
	public PolicyOwnerDAO(EntityManager entityManager, CustomerDAO customerDAO) {
		this.entityManager = entityManager;
		this.customerDAO = customerDAO;
	}
	
	@Override
	public Optional<PolicyOwner> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<PolicyOwner> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(PolicyOwner policyOwner) {
	}
	
	@Override
	public void update(PolicyOwner policyOwner) {
	}
	
	@Override
	public void delete(PolicyOwner policyOwner) {
	}
}
