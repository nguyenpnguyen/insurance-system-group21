package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.PolicyOwner;

import java.util.List;
import java.util.Optional;

public class PolicyOwnerController implements GenericController<PolicyOwner>, UserController<PolicyOwner> {
	EntityManager em;
	private static PolicyOwnerController instance = null;
	
	private PolicyOwnerController(EntityManager em) {
		this.em = em;
	}
	
	public static PolicyOwnerController getInstance(EntityManager em) {
		if (instance == null) {
			instance = new PolicyOwnerController(em);
		}
		return instance;
	}
	
	
	@Override
	public Optional<PolicyOwner> read(String policyOwnerId) {
		return Optional.empty();
	}
	
	@Override
	public List<PolicyOwner> readAll() {
		return List.of();
	}
	
	@Override
	public void create(PolicyOwner po) {
	
	}
	
	@Override
	public void update(PolicyOwner po) {
	
	}
	
	@Override
	public void delete(PolicyOwner po) {
	
	}
	
	@Override
	public Optional<PolicyOwner> findByUsername(String username) {
		return Optional.empty();
	}
}
