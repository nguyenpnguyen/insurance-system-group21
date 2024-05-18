package org.group21.insurance.authentication;

import org.group21.insurance.controllers.InsuranceProviderController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Optional;

public class InsuranceManagerLogInHandler extends LogInHandler<InsuranceProvider> {
	InsuranceProvider user;
	
	@Override
	public InsuranceProvider getUser(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		InsuranceProviderController controller = InsuranceProviderController.getInstance();
		Optional<InsuranceProvider> optionalIm = controller.findByUsername(username);
		if (optionalIm.isPresent()) {
			InsuranceProvider im = optionalIm.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), im.getHashedPassword()) && im.isInsuranceManager()) {
				user = im;
				return user;
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
