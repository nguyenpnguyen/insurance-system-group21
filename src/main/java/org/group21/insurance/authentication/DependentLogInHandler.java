package org.group21.insurance.authentication;


import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

/**
 * LogInHandler for Dependent entities.
 *
 * @author Group 21
 */
public class DependentLogInHandler implements LogInHandler {
	
	/**
	 * Get the user ID of a Dependent entity by its username and password.
	 *
	 * @param username the username of the Dependent entity
	 * @param password the password of the Dependent entity
	 *
	 * @return the user ID of the Dependent entity
	 *
	 * @throws UserNotFoundException         if the Dependent entity with the given username is not found
	 * @throws UserNotAuthenticatedException if the Dependent entity with the given username is found but the password is
	 *                                       incorrect
	 */
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
