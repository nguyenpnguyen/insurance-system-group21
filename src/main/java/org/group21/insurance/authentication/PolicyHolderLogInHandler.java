package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.BeneficiaryController;
import org.group21.insurance.models.Beneficiary;

import java.util.Optional;

public class PolicyHolderLogInHandler implements LogInHandler {
	private static final String ROLE = "PolicyHolder";
	private static EntityManager em;
	private static PolicyHolderLogInHandler instance = null;
	
	private PolicyHolderLogInHandler(BeneficiaryController beneficiaryController) {
	}
	
	public static PolicyHolderLogInHandler getInstance(EntityManager entityManager) {
		em = entityManager;
		if (instance == null) {
			instance = new PolicyHolderLogInHandler(BeneficiaryController.getInstance(em));
		}
		return instance;
	}
	
	@Override
	public boolean isAuthenticated(String username, String password) {
		BeneficiaryController controller = BeneficiaryController.getInstance(em);
		
		Optional<Beneficiary> optionalPh = controller.findByUsername(username);
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
