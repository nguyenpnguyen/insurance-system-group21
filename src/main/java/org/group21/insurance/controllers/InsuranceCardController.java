package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceCard;

import java.util.List;
import java.util.Optional;

public class InsuranceCardController implements GenericController<InsuranceCard> {
	EntityManager em;
	
	private static InsuranceCardController instance = null;
	
	private InsuranceCardController(EntityManager em) {
		this.em = em;
	}
	
	public static InsuranceCardController getInstance(EntityManager em) {
		if (instance == null) {
			instance = new InsuranceCardController(em);
		}
		return instance;
	}
	
	@Override
	public Optional<InsuranceCard> read(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<InsuranceCard> readAll() {
		return List.of();
	}
	
	@Override
	public void create(InsuranceCard ic) {
	
	}
	
	@Override
	public void update(InsuranceCard ic) {
	
	}
	
	@Override
	public void delete(InsuranceCard ic) {
	
	}
}
