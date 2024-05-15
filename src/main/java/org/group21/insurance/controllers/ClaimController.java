package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Claim;

import java.util.List;
import java.util.Optional;

public class ClaimController implements GenericController<Claim> {
	private static ClaimController instance = null;
	
	private ClaimController() {
	}
	
	public static ClaimController getInstance() {
		if (instance == null) {
			instance = new ClaimController();
		}
		return instance;
	}
	
	@Override
	public Optional<Claim> read(EntityManager em, String claimId) {
		return Optional.empty();
	}
	
	@Override
	public List<Claim> readAll(EntityManager em) {
		return List.of();
	}
	
	@Override
	public void create(EntityManager em, Claim c) {
	
	}
	
	@Override
	public void update(EntityManager em, Claim c) {
	
	}
	
	@Override
	public void delete(EntityManager em, Claim c) {
	
	}
}
