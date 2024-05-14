package org.group21.insurance.authentication;

public class InsuranceSurveyorLogInHandler implements LogInHandler {
	@Override
	public boolean isAuthenticated(String username, String password) {
		return false;
	}
	
	@Override
	public String getRole() {
		return null;
	}
}
