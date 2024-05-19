package org.group21.insurance.controllers;


import java.util.List;
import java.util.Optional;

/**
 * Interface for generic controllers.
 *
 * @author Group 21
 */
interface GenericController<T> {
	
	/**
	 * Read an entity by its ID.
	 *
	 * @param id the ID of the entity
	 *
	 * @return the entity with the given ID
	 */
	Optional<T> read(String id);
	
	/**
	 * Read all entities.
	 *
	 * @return a list of all entities
	 */
	List<T> readAll();
	
	/**
	 * Create an entity.
	 *
	 * @param t the entity to create
	 */
	void create(T t);
	
	/**
	 * Update an entity.
	 *
	 * @param t the entity to update
	 */
	void update(T t);
	
	/**
	 * Delete an entity.
	 *
	 * @param t the entity to delete
	 */
	void delete(T t);
	
	/**
	 * Delete all entities.
	 */
	void deleteAll();
	
}
