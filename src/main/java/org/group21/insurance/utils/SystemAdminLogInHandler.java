package org.group21.insurance.utils;

public class SystemAdminLogInHandler implements LogInHandler {
	private static final String ROLE = "SystemAdmin";
	
	private static SystemAdminLogInHandler instance = null;
	
	private SystemAdminLogInHandler() {
	}
	
	public static SystemAdminLogInHandler getInstance() {
		if (instance == null) {
			instance = new SystemAdminLogInHandler();
		}
		return instance;
	}
	
	@Override
	public boolean isAuthenticated(String username, String password) {
		return false;
	}
	
	@Override
	public String getRole() {
		return ROLE;
	}
}
