package org.group21.insurance.utils;

public class DependentLogInHandler implements LogInHandler {
	private static DependentLogInHandler instance = null;
	
	@Override
	public boolean isAuthenticated(String username, String password) {
		return false;
	}
	
	@Override
	public String getRole() {
		return null;
	}
}
