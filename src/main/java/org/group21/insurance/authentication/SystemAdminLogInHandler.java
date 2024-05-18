package org.group21.insurance.authentication;

import org.group21.insurance.controllers.SystemAdminController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.SystemAdmin;

import java.util.Optional;

public class SystemAdminLogInHandler extends LogInHandler<SystemAdmin> {
	SystemAdmin user;
	
	@Override
	public SystemAdmin getUser(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		SystemAdminController controller = SystemAdminController.getInstance();
		Optional<SystemAdmin> optionalAdmin = controller.findByUsername(username);
		if (optionalAdmin.isPresent()) {
			SystemAdmin admin = optionalAdmin.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), admin.getHashedPassword())) {
				user = admin;
				return user;
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
