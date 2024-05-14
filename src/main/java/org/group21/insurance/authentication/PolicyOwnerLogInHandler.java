package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.PolicyOwnerController;
import org.group21.insurance.models.PolicyOwner;

import java.util.Optional;

public class PolicyOwnerLogInHandler implements LogInHandler {
	private static final String ROLE = "PolicyOwner";
	private static EntityManager em;
	private static PolicyOwnerLogInHandler instance = null;
	
	private PolicyOwnerLogInHandler(PolicyOwnerController policyOwnerController) {
	}
	
	public static PolicyOwnerLogInHandler getInstance(EntityManager entityManager) {
		em = entityManager;
		if (instance == null) {
			instance = new PolicyOwnerLogInHandler(PolicyOwnerController.getInstance(em));
		}
		return instance;
	}
	
	@Override
	public boolean isAuthenticated(String username, String password) {
		PolicyOwnerController controller = PolicyOwnerController.getInstance(em);
		
		Optional<PolicyOwner> optionalPo = controller.findByUsername(username);
		if (optionalPo.isEmpty()) {
			return false;
		}
		
		PolicyOwner po = optionalPo.get();
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		return passwordAuthenticator.authenticate(password.toCharArray(), po.getHashedPassword());
	}
	
	@Override
	public String getRole() {
		return ROLE;
	}
}
