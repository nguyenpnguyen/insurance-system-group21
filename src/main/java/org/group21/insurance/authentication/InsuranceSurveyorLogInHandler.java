package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.InsuranceProviderController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Optional;

public class InsuranceSurveyorLogInHandler extends LogInHandler<InsuranceProvider> {
	
	@Override
	public InsuranceProvider getUser(EntityManager em, String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		InsuranceProviderController controller = InsuranceProviderController.getInstance();
		Optional<InsuranceProvider> optionalIs = controller.findByUsername(em, username);
		if (optionalIs.isPresent()) {
			InsuranceProvider is = optionalIs.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), is.getHashedPassword()) && !is.isInsuranceManager()) {
				return is;
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
