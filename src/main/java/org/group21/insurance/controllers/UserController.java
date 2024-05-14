package org.group21.insurance.controllers;

import java.util.Optional;

interface UserController<T> {
	Optional<T> findByUsername(String username);
}
