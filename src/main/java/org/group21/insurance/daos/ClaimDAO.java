package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Claim;

import java.util.List;
import java.util.Optional;

public class ClaimDAO implements Dao<Claim> {
	EntityManager entityManager;
	
	@Override
	public Optional<Claim> get(String id) {
		// Get claim from database
		return Optional.empty();
	}
	
	@Override
	public List<Claim> getAll() {
		return null;
	}
	
	@Override
	public void save(Claim claim) {
		// Save claim to database
	}
	
	@Override
	public void update(Claim claim) {
		// Update claim in database
	}
	
	@Override
	public void delete(Claim claim) {
		// Delete claim from database
	}
}
