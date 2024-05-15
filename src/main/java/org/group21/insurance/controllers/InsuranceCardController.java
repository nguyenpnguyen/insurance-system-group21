package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceCard;

import java.util.List;
import java.util.Optional;

public class InsuranceCardController implements GenericController<InsuranceCard> {
	private static InsuranceCardController instance = null;
	
	private InsuranceCardController() {
	}
	
	public static InsuranceCardController getInstance() {
		if (instance == null) {
			instance = new InsuranceCardController();
		}
		return instance;
	}
	
	@Override
	public Optional<InsuranceCard> read(EntityManager em, String cardNumber) {
		return Optional.empty();
	}
	
	@Override
	public List<InsuranceCard> readAll(EntityManager em) {
		return List.of();
	}
	
	@Override
	public void create(EntityManager em, InsuranceCard ic) {
	
	}
	
	@Override
	public void update(EntityManager em, InsuranceCard ic) {
	
	}
	
	@Override
	public void delete(EntityManager em, InsuranceCard ic) {
	
	}
}
