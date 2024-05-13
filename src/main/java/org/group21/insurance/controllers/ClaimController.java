package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Claim;

import java.util.List;
import java.util.Optional;

public class ClaimController implements GenericController<Claim> {
	EntityManager em;
	
	public ClaimController() {
	}
	
	public ClaimController(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Optional<Claim> read(String id) {
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
