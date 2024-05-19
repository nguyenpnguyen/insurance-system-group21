package org.group21.insurance.authentication;


import org.group21.insurance.controllers.InsuranceProviderController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Optional;

/**
 * LogInHandler for Insurance Manager role.
 *
 * @author Group 21
 */
public class InsuranceManagerLogInHandler implements LogInHandler {
	
	/**
	 * Get the user ID of a Insurance Manager entity by its username and password.
	 *
	 * @param username the username of the Insurance Manager entity
	 * @param password the password of the Insurance Manager entity
	 *
	 * @return the user ID of the Insurance Manager entity
	 *
	 * @throws UserNotFoundException         if the Insurance Manager entity with the given username is not found
	 * @throws UserNotAuthenticatedException if the Insurance Manager entity with the given username is found but the
	 *                                       password is incorrect
	 */
	@Override
	public String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		InsuranceProviderController controller = InsuranceProviderController.getInstance();
		Optional<InsuranceProvider> optionalIm = controller.findByUsername(username);
		if (optionalIm.isPresent()) {
			InsuranceProvider im = optionalIm.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), im.getHashedPassword()) && im.isInsuranceManager()) {
				return im.getInsuranceProviderId();
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
