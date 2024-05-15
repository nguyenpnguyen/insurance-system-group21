package org.group21.insurance.authentication;

public class LogInHandlerFactory {
	private static final String POLICY_OWNER = "Policy owner";
	private static final String SYSTEM_ADMIN = "System admin";
	private static final String POLICY_HOLDER = "Policy holder";
	private static final String DEPENDENT = "Dependent";
	private static final String INSURANCE_MANAGER = "Insurance manager";
	private static final String INSURANCE_SURVEYOR = "Insurance surveyor";
	
	public LogInHandler getLogInHandler(String userType) {
		switch (userType) {
			case POLICY_OWNER:
				return new PolicyOwnerLogInHandler();
			case SYSTEM_ADMIN:
				return new SystemAdminLogInHandler();
			case POLICY_HOLDER:
				return new PolicyHolderLogInHandler();
			case DEPENDENT:
				return new DependentLogInHandler();
			case INSURANCE_MANAGER:
				return new InsuranceManagerLogInHandler();
			case INSURANCE_SURVEYOR:
				return new InsuranceSurveyorLogInHandler();
			default:
				return null;
		}
	}
}
