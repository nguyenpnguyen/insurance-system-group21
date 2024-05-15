package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

interface GenericController<T> {
	
	Optional<T> read(EntityManager em, String id);
	
	List<T> readAll(EntityManager em);
	
	void create(EntityManager em, T t);
	
	void update(EntityManager em, T t);
	
	void delete(EntityManager em, T t);
}
