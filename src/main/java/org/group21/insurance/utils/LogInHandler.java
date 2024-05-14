package org.group21.insurance.utils;

interface LogInHandler {
	
	boolean isAuthenticated(String username, String password);
	
	String getRole();
}
