package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

public class DependentLogInHandler implements LogInHandler {
	private static final String ROLE = "Dependent";
	private static DependentLogInHandler instance = null;
	
	private DependentLogInHandler(BeneficiaryController beneficiaryController) {
	}
	
	public static DependentLogInHandler getInstance() {
		if (instance == null) {
			instance = new DependentLogInHandler(BeneficiaryController.getInstance());
		}
		return instance;
	}
	
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
	
	@Override
	public String getRole() {
		return ROLE;
	}
}
