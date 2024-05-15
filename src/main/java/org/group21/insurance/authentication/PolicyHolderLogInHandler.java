package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

public class PolicyHolderLogInHandler implements LogInHandler {
	private static final String ROLE = "PolicyHolder";
	private static PolicyHolderLogInHandler instance = null;
	
	private PolicyHolderLogInHandler(BeneficiaryController beneficiaryController) {
	}
	
	public static PolicyHolderLogInHandler getInstance() {
		if (instance == null) {
			instance = new PolicyHolderLogInHandler(BeneficiaryController.getInstance());
		}
		return instance;
	}
	
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
	
	@Override
	public String getRole() {
		return ROLE;
	}
}
