package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

public class DependentLogInHandler extends LogInHandler {
	
	@Override
	public boolean isAuthenticated(EntityManager em, String username, String password) {
		BeneficiaryController controller = BeneficiaryController.getInstance();
		
		Optional<Beneficiary> optionalDependent = controller.findByUsername(em, username);
		if (optionalDependent.isEmpty()) {
			return false;
		}
		
		Beneficiary dependent = optionalDependent.get();
		if (dependent.isPolicyHolder()) {
			return false;
		}
		
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		return passwordAuthenticator.authenticate(password.toCharArray(), dependent.getHashedPassword());
	}
}
