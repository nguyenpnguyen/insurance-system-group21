package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.SystemAdminController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.SystemAdmin;

import java.util.Optional;

public class SystemAdminLogInHandler extends LogInHandler<SystemAdmin> {
	
	@Override
	public SystemAdmin getUser(EntityManager em, String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		SystemAdminController controller = SystemAdminController.getInstance();
		Optional<SystemAdmin> optionalAdmin = controller.findByUsername(em, username);
		if (optionalAdmin.isPresent()) {
			SystemAdmin admin = optionalAdmin.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), admin.getHashedPassword())) {
				return admin;
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
