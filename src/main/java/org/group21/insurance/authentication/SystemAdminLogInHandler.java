package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.SystemAdminController;
import org.group21.insurance.models.SystemAdmin;

import java.util.Optional;

public class SystemAdminLogInHandler implements LogInHandler {
	private static final String ROLE = "SystemAdmin";
	private static SystemAdminLogInHandler instance = null;
	
	private SystemAdminLogInHandler(SystemAdminController systemAdminController) {
	}
	
	public static SystemAdminLogInHandler getInstance() {
		if (instance == null) {
			instance = new SystemAdminLogInHandler(SystemAdminController.getInstance());
		}
		return instance;
	}
	
	@Override
	public boolean isAuthenticated(EntityManager em, String username, String password) {
		SystemAdminController controller = SystemAdminController.getInstance();
		
		Optional<SystemAdmin> optionalAdmin = controller.findByUsername(em, username);
		if (optionalAdmin.isEmpty()) {
			return false;
		}
		
		SystemAdmin admin = optionalAdmin.get();
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		return passwordAuthenticator.authenticate(password.toCharArray(), admin.getHashedPassword());
	}
	
	@Override
	public String getRole() {
		return ROLE;
	}
}
