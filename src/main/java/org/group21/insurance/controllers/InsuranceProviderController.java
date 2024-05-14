package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceProvider;

import java.util.List;
import java.util.Optional;

public class InsuranceProviderController implements GenericController<InsuranceProvider>, UserController<InsuranceProvider> {
	EntityManager em;
	
	private static InsuranceProviderController instance = null;
	
	private InsuranceProviderController(EntityManager em) {
		this.em = em;
	}
	
	public static InsuranceProviderController getInstance(EntityManager em) {
		if (instance == null) {
			instance = new InsuranceProviderController(em);
		}
		return instance;
	}
	
	public Optional<InsuranceProvider> readInsuranceManager(String imId) {
		return Optional.empty();
	}
	
	public Optional<InsuranceProvider> readInsuranceSurveyor(String isId) {
		return Optional.empty();
	}
	
	public List<InsuranceProvider> readAllInsuranceManagers() {
		return List.of();
	}
	
	public List<InsuranceProvider> readAllInsuranceSurveyors() {
		return List.of();
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
	public void create(InsuranceProvider ip) {
	
	}
	
	@Override
	public void update(InsuranceProvider ip) {
	
	}
	
	@Override
	public void delete(InsuranceProvider ip) {
	
	}
	
	@Override
	public Optional<InsuranceProvider> findByUsername(String username) {
		return Optional.empty();
	}
}
