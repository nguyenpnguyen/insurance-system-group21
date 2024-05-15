package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.InsuranceProviderController;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Optional;

public class InsuranceManagerLogInHandler extends LogInHandler {
	
	@Override
	public boolean isAuthenticated(EntityManager em, String username, String password) {
		InsuranceProviderController controller = InsuranceProviderController.getInstance();
		
		Optional<InsuranceProvider> optionalIm = controller.findByUsername(em, username);
		if (optionalIm.isEmpty()) {
			return false;
		}
		
		InsuranceProvider insuranceManager = optionalIm.get();
		if (!insuranceManager.isInsuranceManager()) {
			return false;
		}
		
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		return passwordAuthenticator.authenticate(password.toCharArray(), insuranceManager.getHashedPassword());
	}
}
