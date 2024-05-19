package org.group21.insurance.controllers;


import java.util.Optional;

/**
 * Interface for user controllers.
 *
 * @author Group 21
 */
interface UserController<T> {
	/**
	 * Find a user by username.
	 *
	 * @param username the username of the user
	 *
	 * @return the user with the given username
	 */
	Optional<T> findByUsername(String username);
}
