package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.controllers.InsuranceProviderController;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Optional;

public class InsuranceSurveyorLogInHandler implements LogInHandler {
	private static final String ROLE = "InsuranceSurveyor";
	private static InsuranceSurveyorLogInHandler instance = null;
	
	private InsuranceSurveyorLogInHandler(InsuranceProviderController ipController) {
	}
	
	public static InsuranceSurveyorLogInHandler getInstance() {
		if (instance == null) {
			instance = new InsuranceSurveyorLogInHandler(InsuranceProviderController.getInstance());
		}
		return instance;
	}
	
	@Override
	public boolean isAuthenticated(EntityManager em, String username, String password) {
		InsuranceProviderController controller = InsuranceProviderController.getInstance();
		
		Optional<InsuranceProvider> optionalIs = controller.findByUsername(em, username);
		if (optionalIs.isEmpty()) {
			return false;
		}
		
		InsuranceProvider insuranceSurveyor = optionalIs.get();
		if (insuranceSurveyor.isInsuranceManager()) {
			return false;
		}
		
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		return passwordAuthenticator.authenticate(password.toCharArray(), insuranceSurveyor.getHashedPassword());
	}
	
	@Override
	public String getRole() {
		return ROLE;
	}
}
