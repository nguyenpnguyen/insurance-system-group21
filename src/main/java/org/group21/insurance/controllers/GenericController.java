package org.group21.insurance.controllers;

import java.util.List;
import java.util.Optional;

interface GenericController<T> {
	
	Optional<T> read(String id);
	
	List<T> readAll();
	
	void create(T t);
	
	void update(T t);
	
	void delete(T t);
}
