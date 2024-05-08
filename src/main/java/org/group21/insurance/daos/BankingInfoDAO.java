package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.BankingInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankingInfoDAO implements Dao<BankingInfo> {
	EntityManager entityManager;
	
	public BankingInfoDAO() {
	}
	
	public BankingInfoDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<BankingInfo> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<BankingInfo> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(BankingInfo bankingInfo) {
	}
	
	@Override
	public void update(BankingInfo bankingInfo) {
	}
	
	@Override
	public void delete(BankingInfo bankingInfo) {
	}
}
