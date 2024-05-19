package org.group21.insurance.controllers;

/**
 * @author Group 21
 */

import java.util.Optional;

interface UserController<T> {
	Optional<T> findByUsername(String username);
}
