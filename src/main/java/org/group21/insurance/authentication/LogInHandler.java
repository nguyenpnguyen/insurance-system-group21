package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;

interface LogInHandler {
	
	boolean isAuthenticated(EntityManager em, String username, String password);
	
	String getRole();
}
