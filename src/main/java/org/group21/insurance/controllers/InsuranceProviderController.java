package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceProvider;

import java.util.List;
import java.util.Optional;

public class InsuranceProviderController implements GenericController<InsuranceProvider>, UserController<InsuranceProvider> {
	private static InsuranceProviderController instance = null;
	
	private InsuranceProviderController() {
	}
	
	public static InsuranceProviderController getInstance() {
		if (instance == null) {
			instance = new InsuranceProviderController();
		}
		return instance;
	}
	
	public Optional<InsuranceProvider> readInsuranceManager(EntityManager em, String insuranceManagerId) {
		return Optional.empty();
	}
	
	public Optional<InsuranceProvider> readInsuranceSurveyor(EntityManager em, String insuranceSurveyorId) {
		return Optional.empty();
	}
	
	public List<InsuranceProvider> readAllInsuranceManagers(EntityManager em) {
		return List.of();
	}
	
	public List<InsuranceProvider> readAllInsuranceSurveyors(EntityManager em) {
		return List.of();
	}
	
	@Override
	public Optional<InsuranceProvider> read(EntityManager em, String insuranceProviderId) {
		return Optional.empty();
	}
	
	@Override
	public List<InsuranceProvider> readAll(EntityManager em) {
		return List.of();
	}
	
	@Override
	public void create(EntityManager em, InsuranceProvider ip) {
	
	}
	
	@Override
	public void update(EntityManager em, InsuranceProvider ip) {
	
	}
	
	@Override
	public void delete(EntityManager em, InsuranceProvider ip) {
	
	}
	
	@Override
	public Optional<InsuranceProvider> findByUsername(EntityManager em, String username) {
		return Optional.empty();
	}
}
