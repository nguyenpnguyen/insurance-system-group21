package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.InsuranceProviderController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Optional;

public class InsuranceManagerLogInHandler extends LogInHandler<InsuranceProvider> {
	
	@Override
	public InsuranceProvider getUser(EntityManager em, String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		InsuranceProviderController controller = InsuranceProviderController.getInstance();
		Optional<InsuranceProvider> optionalIm = controller.findByUsername(em, username);
		if (optionalIm.isPresent()) {
			InsuranceProvider im = optionalIm.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), im.getHashedPassword()) && im.isInsuranceManager()) {
				return im;
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
