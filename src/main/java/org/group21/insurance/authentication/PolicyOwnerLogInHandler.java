package org.group21.insurance.authentication;

import org.group21.insurance.controllers.PolicyOwnerController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.PolicyOwner;

import java.util.Optional;

public class PolicyOwnerLogInHandler extends LogInHandler<PolicyOwner> {
	PolicyOwner user;
	
	@Override
	public PolicyOwner getUser(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		PolicyOwnerController controller = PolicyOwnerController.getInstance();
		Optional<PolicyOwner> optionalPo = controller.findByUsername(username);
		if (optionalPo.isPresent()) {
			PolicyOwner po = optionalPo.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), po.getHashedPassword())) {
				user = po;
				return user;
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
