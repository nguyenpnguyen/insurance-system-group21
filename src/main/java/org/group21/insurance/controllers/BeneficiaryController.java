package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Beneficiary;

import java.util.List;
import java.util.Optional;

public class BeneficiaryController implements GenericController<Beneficiary>, UserController<Beneficiary> {
	private static BeneficiaryController instance = null;
	
	private BeneficiaryController() {
	}
	
	public static BeneficiaryController getInstance() {
		if (instance == null) {
			instance = new BeneficiaryController();
		}
		return instance;
	}
	
	public Optional<Beneficiary> readPolicyHolder(EntityManager em, String policyHolderId) {
		return Optional.empty();
	}
	
	public Optional<Beneficiary> readDependent(EntityManager em, String dependentId) {
		return Optional.empty();
	}
	
	public List<Beneficiary> readAllPolicyHolders(EntityManager em) {
		return List.of();
	}
	
	public List<Beneficiary> readAllDependents(EntityManager em) {
		return List.of();
	}
	
	@Override
	public Optional<Beneficiary> read(EntityManager em, String beneficiaryId) {
		return Optional.empty();
	}
	
	@Override
	public List<Beneficiary> readAll(EntityManager em) {
		return List.of();
	}
	
	@Override
	public void create(EntityManager em, Beneficiary b) {
	
	}
	
	@Override
	public void update(EntityManager em, Beneficiary b) {
	
	}
	
	@Override
	public void delete(EntityManager em, Beneficiary b) {
	
	}
	
	@Override
	public Optional<Beneficiary> findByUsername(EntityManager em, String username) {
		return Optional.empty();
	}
}
