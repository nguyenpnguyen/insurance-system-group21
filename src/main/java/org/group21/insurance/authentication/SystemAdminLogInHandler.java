package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.SystemAdminController;
import org.group21.insurance.models.SystemAdmin;

import java.util.Optional;

public class SystemAdminLogInHandler extends LogInHandler {
	
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
}
