package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.PolicyOwner;

import java.util.List;
import java.util.Optional;

public class PolicyOwnerController implements GenericController<PolicyOwner> {
	EntityManager em;
	
	public PolicyOwnerController(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Optional<PolicyOwner> read(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<PolicyOwner> readAll() {
		return List.of();
	}
	
	@Override
	public void create(PolicyOwner policyOwner) {
	
	}
	
	@Override
	public void update(PolicyOwner policyOwner) {
	
	}
	
	@Override
	public void delete(PolicyOwner policyOwner) {
	
	}
}
