package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

public class PolicyHolderLogInHandler extends LogInHandler {
	
	@Override
	public boolean isAuthenticated(EntityManager em, String username, String password) {
		BeneficiaryController controller = BeneficiaryController.getInstance();
		
		Optional<Beneficiary> optionalPh = controller.findByUsername(em, username);
		if (optionalPh.isEmpty()) {
			return false;
		}
		
		Beneficiary ph = optionalPh.get();
		if (!ph.isPolicyHolder()) {
			return false;
		}
		
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		return passwordAuthenticator.authenticate(password.toCharArray(), ph.getHashedPassword());
	}
}
