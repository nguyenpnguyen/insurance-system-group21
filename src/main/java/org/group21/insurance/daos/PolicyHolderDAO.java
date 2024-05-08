package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.PolicyHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PolicyHolderDAO implements Dao<PolicyHolder> {
	EntityManager entityManager;
	CustomerDAO customerDAO;
	
	public PolicyHolderDAO() {
	}
	
	public PolicyHolderDAO(EntityManager entityManager, CustomerDAO customerDAO) {
		this.entityManager = entityManager;
		this.customerDAO = customerDAO;
	}
	
	@Override
	public Optional<PolicyHolder> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<PolicyHolder> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(PolicyHolder policyHolder) {
	}
	
	@Override
	public void update(PolicyHolder policyHolder) {
	}
	
	@Override
	public void delete(PolicyHolder policyHolder) {
	}
}
