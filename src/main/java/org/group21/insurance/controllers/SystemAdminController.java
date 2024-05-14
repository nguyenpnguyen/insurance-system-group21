package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.SystemAdmin;

import java.util.List;
import java.util.Optional;

public class SystemAdminController implements GenericController<SystemAdmin> {
	EntityManager em;
	
	public SystemAdminController(EntityManager em) {
		this.em = em;
	}
	
	public Optional<SystemAdmin> read(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<SystemAdmin> readAll() {
		String sysAdminIdString = Long.toString(1);
		Optional<SystemAdmin> optionalSysAdmin = read(sysAdminIdString);
		
		return optionalSysAdmin.map(List::of).orElse(null);
	}
	
	@Override
	public void create(SystemAdmin systemAdmin) {
	
	}
	
	@Override
	public void update(SystemAdmin systemAdmin) {
	
	}
	
	@Override
	public void delete(SystemAdmin systemAdmin) {
	
	}
}
