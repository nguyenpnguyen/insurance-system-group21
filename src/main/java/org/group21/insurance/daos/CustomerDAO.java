package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Implement the methods in the Dao interface

public class CustomerDAO implements Dao<Customer> {
	EntityManager entityManager;
	
	public CustomerDAO() {
	}
	
	public CustomerDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<Customer> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<Customer> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(Customer customer) {
	}
	
	@Override
	public void update(Customer customer) {
	}
	
	@Override
	public void delete(Customer customer) {
	}
}
