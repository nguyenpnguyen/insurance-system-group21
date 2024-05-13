package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceProvider;

import java.util.List;
import java.util.Optional;

public class InsuranceProviderController implements GenericController<InsuranceProvider> {
	EntityManager em;
	
	public InsuranceProviderController(EntityManager em) {
		this.em = em;
	}
	
	public Optional<InsuranceProvider> readInsuranceManager(String id) {
		return Optional.empty();
	}
	
	public Optional<InsuranceProvider> readInsuranceSurveyor(String id) {
		return Optional.empty();
	}
	
	@Override
	public Optional<InsuranceProvider> read(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<InsuranceProvider> readAll() {
		return List.of();
	}
	
	@Override
	public void create(InsuranceProvider insuranceProvider) {
	
	}
	
	@Override
	public void update(InsuranceProvider insuranceProvider) {
	
	}
	
	@Override
	public void delete(InsuranceProvider insuranceProvider) {
	
	}
}
