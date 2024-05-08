package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.InsuranceCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InsuranceCardDAO implements Dao<InsuranceCard> {
	EntityManager entityManager;
	
	public InsuranceCardDAO() {
	}
	
	public InsuranceCardDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<InsuranceCard> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<InsuranceCard> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(InsuranceCard insuranceCard) {
	}
	
	@Override
	public void update(InsuranceCard insuranceCard) {
	}
	
	@Override
	public void delete(InsuranceCard insuranceCard) {
	}
}
