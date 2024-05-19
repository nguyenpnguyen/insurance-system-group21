package org.group21.insurance.authentication;


import org.group21.insurance.controllers.PolicyOwnerController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.PolicyOwner;

import java.util.Optional;

/**
 * LogInHandler for PolicyOwner entities.
 *
 * @author Group 21
 */
public class PolicyOwnerLogInHandler implements LogInHandler {
	
	/**
	 * Get the user ID of a PolicyOwner entity by its username and password.
	 *
	 * @param username the username of the PolicyOwner entity
	 * @param password the password of the PolicyOwner entity
	 *
	 * @return the user ID of the PolicyOwner entity
	 *
	 * @throws UserNotFoundException         if the PolicyOwner entity with the given username is not found
	 * @throws UserNotAuthenticatedException if the PolicyOwner entity with the given username is found but the password
	 *                                       is incorrect
	 */
	@Override
	public String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		PolicyOwnerController controller = PolicyOwnerController.getInstance();
		Optional<PolicyOwner> optionalPo = controller.findByUsername(username);
		if (optionalPo.isPresent()) {
			PolicyOwner po = optionalPo.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), po.getHashedPassword())) {
				return po.getCustomerId();
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
