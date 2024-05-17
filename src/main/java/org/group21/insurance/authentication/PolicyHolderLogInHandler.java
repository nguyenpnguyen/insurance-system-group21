package org.group21.insurance.authentication;

import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

public class PolicyHolderLogInHandler extends LogInHandler<Beneficiary> {
	
	@Override
	public Beneficiary getUser(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException {
		BeneficiaryController controller = BeneficiaryController.getInstance();
		Optional<Beneficiary> optionalPh = controller.findByUsername(username);
		if (optionalPh.isPresent()) {
			Beneficiary ph = optionalPh.get();
			PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
			if (passwordAuthenticator.authenticate(password.toCharArray(), ph.getHashedPassword()) && ph.isPolicyHolder()) {
				return ph;
			} else {
				throw new UserNotAuthenticatedException("User " + username + " is not authenticated.");
			}
		}
		throw new UserNotFoundException("User " + username + " not found.");
	}
}
