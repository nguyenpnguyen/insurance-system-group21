package org.group21.insurance.utils;

public class InsuranceManagerLogInHandler implements LogInHandler {
	@Override
	public boolean isAuthenticated(String username, String password) {
		return false;
	}
	
	@Override
	public String getRole() {
		return null;
	}
}
