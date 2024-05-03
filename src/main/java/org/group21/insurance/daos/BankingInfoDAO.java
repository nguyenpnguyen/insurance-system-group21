package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.BankingInfo;

import java.util.List;
import java.util.Optional;

public class BankingInfoDAO implements Dao<BankingInfo>{
	EntityManager entityManager;
	
	@Override
	public Optional<BankingInfo> get(String id) {
		// Get banking info from database
		return Optional.empty();
	}
	
	@Override
	public List<BankingInfo> getAll() {
		// Get all banking info from database
		return null;
	}
	
	@Override
	public void save(BankingInfo bankingInfo) {
		// Save banking info to database
	}
	
	@Override
	public void update(BankingInfo bankingInfo) {
		// Update banking info in database
	}
	
	@Override
	public void delete(BankingInfo bankingInfo) {
		// Delete banking info from database
	}
}
