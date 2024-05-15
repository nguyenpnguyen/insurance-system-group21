package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;

public abstract class LogInHandler {
	abstract boolean isAuthenticated(EntityManager em, String username, String password);
}
