package org.group21.insurance.authentication;


import org.group21.insurance.controllers.InsuranceProviderController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Optional;

/**
 * LogInHandler for Insurance surveyor role.
 *
 * @author Group 21
 */
public class InsuranceSurveyorLogInHandler implements LogInHandler {
	
	/**
	 * Get the user ID of a Insurance surveyor entity by its username and password.
	 *
	 * @param username the username of the Insurance surveyor entity
	 * @param password the password of the Insurance surveyor entity
	 *
	 * @return the user ID of the Insurance surveyor entity
	 *
	 * @throws UserNotFoundException         if the Insurance surveyor entity with the given username is not found
	 * @throws UserNotAuthenticatedException if the Insurance surveyor entity with the given username is found but the
	 *                                       password is incorrect
	 */
	@Override
	public String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		InsuranceProviderController controller = InsuranceProviderController.getInstance();
		Optional<InsuranceProvider> optionalIs = controller.findByUsername(username);
		if (optionalIs.isPresent()) {
			InsuranceProvider is = optionalIs.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), is.getHashedPassword()) && !is.isInsuranceManager()) {
				return is.getInsuranceProviderId();
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
