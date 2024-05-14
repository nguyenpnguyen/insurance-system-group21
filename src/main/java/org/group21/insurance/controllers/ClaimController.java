package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Claim;

import java.util.List;
import java.util.Optional;

public class ClaimController implements GenericController<Claim> {
	EntityManager em;
	
	private static ClaimController instance = null;
	
	private ClaimController(EntityManager em) {
		this.em = em;
	}
	
	public static ClaimController getInstance(EntityManager em) {
		if (instance == null) {
			instance = new ClaimController(em);
		}
		return instance;
	}
	
	@Override
	public Optional<Claim> read(String claimId) {
		return Optional.empty();
	}
	
	@Override
	public List<Claim> readAll() {
		return List.of();
	}
	
	@Override
	public void create(Claim c) {
	
	}
	
	@Override
	public void update(Claim c) {
	
	}
	
	@Override
	public void delete(Claim c) {
	
	}
}
