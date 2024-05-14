package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.BankingInfo;

import java.util.List;
import java.util.Optional;

public class BankingInfoController implements GenericController<BankingInfo> {
	EntityManager em;
	
	private static BankingInfoController instance = null;
	
	private BankingInfoController(EntityManager em) {
		this.em = em;
	}
	
	public static BankingInfoController getInstance(EntityManager em) {
		if (instance == null) {
			instance = new BankingInfoController(em);
		}
		return instance;
	}
	
	@Override
	public Optional<BankingInfo> read(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<BankingInfo> readAll() {
		return List.of();
	}
	
	@Override
	public void create(BankingInfo bi) {
	
	}
	
	@Override
	public void update(BankingInfo bi) {
	
	}
	
	@Override
	public void delete(BankingInfo bi) {
	
	}
}
