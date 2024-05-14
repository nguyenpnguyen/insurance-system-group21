package org.group21.insurance.authentication;

interface LogInHandler {
	
	boolean isAuthenticated(String username, String password);
	
	String getRole();
}
