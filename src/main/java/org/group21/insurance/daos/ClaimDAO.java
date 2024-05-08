package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Claim;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClaimDAO implements Dao<Claim> {
	EntityManager entityManager;
	
	public ClaimDAO() {
	}
	
	public ClaimDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<Claim> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<Claim> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(Claim claim) {
	}
	
	@Override
	public void update(Claim claim) {
	}
	
	@Override
	public void delete(Claim claim) {
	}
}
