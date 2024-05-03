package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerDAO implements Dao<Customer> {
	EntityManager entityManager;
	
	@Override
	public Optional<Customer> get(String id) {
		// Get customer from database
		return Optional.empty();
	}
	
	@Override
	public List<Customer> getAll() {
		// Get all customers from database
		return null;
	}
	
	@Override
	public void save(Customer customer) {
			// Save customer to database
	}

	@Override
	public void update(Customer customer) {
			// Update customer in database
	}

	@Override
	public void delete(Customer customer) {
			// Delete customer from database
	}
}
