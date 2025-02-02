package org.group21.insurance.authentication;

import org.group21.insurance.controllers.SystemAdminController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.SystemAdmin;

import java.util.Optional;

/**
 * LogInHandler for SystemAdmin entities.
 *
 * @author Group 21
 */
public class SystemAdminLogInHandler implements LogInHandler {
	
	/**
	 * Get the user ID of a SystemAdmin entity by its username and password.
	 *
	 * @param username the username of the SystemAdmin entity
	 * @param password the password of the SystemAdmin entity
	 *
	 * @return the user ID of the SystemAdmin entity
	 *
	 * @throws UserNotFoundException         if the SystemAdmin entity with the given username is not found
	 * @throws UserNotAuthenticatedException if the SystemAdmin entity with the given username is found but the password
	 *                                       is incorrect
	 */
	@Override
	public String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		SystemAdminController controller = SystemAdminController.getInstance();
		Optional<SystemAdmin> optionalAdmin = controller.findByUsername(username);
		if (optionalAdmin.isPresent()) {
			SystemAdmin admin = optionalAdmin.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), admin.getHashedPassword())) {
				return Long.toString(admin.getSysAdminId());
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
