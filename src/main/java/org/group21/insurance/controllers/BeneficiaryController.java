package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Beneficiary;

import java.util.List;
import java.util.Optional;

public class BeneficiaryController implements GenericController<Beneficiary> {
	EntityManager em;
	
	public BeneficiaryController() {
	}
	
	public BeneficiaryController(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public Optional<Beneficiary> readPolicyHolder(String policyHolderId) {
		return Optional.empty();
	}
	
	public Optional<Beneficiary> readDependent(String dependentId) {
		return Optional.empty();
	}
	
	public List<Beneficiary> readAllPolicyHolders() {
		return List.of();
	}
	
	public List<Beneficiary> readAllDependents() {
		return List.of();
	}
	
	@Override
	public Optional<Beneficiary> read(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<Beneficiary> readAll() {
		return List.of();
	}
	
	@Override
	public void create(Beneficiary beneficiary) {
	
	}
	
	@Override
	public void update(Beneficiary beneficiary) {
	
	}
	
	@Override
	public void delete(Beneficiary beneficiary) {
	
	}
}
