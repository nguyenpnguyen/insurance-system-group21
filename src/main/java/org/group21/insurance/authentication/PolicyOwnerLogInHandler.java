package org.group21.insurance.authentication;

/**
 * @author Group 21
 */

import org.group21.insurance.controllers.PolicyOwnerController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.PolicyOwner;

import java.util.Optional;

public class PolicyOwnerLogInHandler implements LogInHandler {
	
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
