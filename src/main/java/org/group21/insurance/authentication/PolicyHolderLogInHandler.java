package org.group21.insurance.authentication;


import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

/**
 * LogInHandler for Policy holder role.
 *
 * @author Group 21
 */
public class PolicyHolderLogInHandler implements LogInHandler {
	
	/**
	 * Get the user ID of a Policy holder entity by its username and password.
	 *
	 * @param username the username of the Policy holder entity
	 * @param password the password of the Policy holder entity
	 *
	 * @return the user ID of the Policy holder entity
	 *
	 * @throws UserNotFoundException         if the Policy holder entity with the given username is not found
	 * @throws UserNotAuthenticatedException if the Policy holder entity with the given username is found but the password
	 *                                       is incorrect
	 */
	@Override
	public String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		BeneficiaryController controller = BeneficiaryController.getInstance();
		Optional<Beneficiary> optionalPh = controller.findByUsername(username);
		if (optionalPh.isPresent()) {
			Beneficiary ph = optionalPh.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), ph.getHashedPassword()) && ph.isPolicyHolder()) {
				return ph.getCustomerId();
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
