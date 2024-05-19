package org.group21.insurance.authentication;

/**
 * @author Group 21
 */

import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

public class DependentLogInHandler implements LogInHandler {
	
	@Override
	public String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		BeneficiaryController controller = BeneficiaryController.getInstance();
		Optional<Beneficiary> optionalDependent = controller.findByUsername(username);
		if (optionalDependent.isPresent()) {
			Beneficiary dependent = optionalDependent.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), dependent.getHashedPassword()) && !dependent.isPolicyHolder()) {
				return dependent.getCustomerId();
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
