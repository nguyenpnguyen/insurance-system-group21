package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.BankingInfo;

import java.util.List;
import java.util.Optional;

public class BankingInfoController implements GenericController<BankingInfo>{
	EntityManager em;
	
	public BankingInfoController() {
	}
	
	public BankingInfoController(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEm() {
		return em;
	}
	
	public void setEm(EntityManager em) {
		this.em = em;
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
	public void create(BankingInfo bankingInfo) {
	
	}
	
	@Override
	public void update(BankingInfo bankingInfo) {
	
	}
	
	@Override
	public void delete(BankingInfo bankingInfo) {
	
	}
}
