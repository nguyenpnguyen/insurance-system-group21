package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;

import java.util.Optional;

interface UserController<T> {
	Optional<T> findByUsername(EntityManager em, String username);
}
