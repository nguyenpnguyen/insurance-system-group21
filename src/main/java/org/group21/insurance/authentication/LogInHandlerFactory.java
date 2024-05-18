package org.group21.insurance.authentication;

public class LogInHandlerFactory {
	private static final String POLICY_OWNER = "Policy owner";
	private static final String SYSTEM_ADMIN = "System admin";
	private static final String POLICY_HOLDER = "Policy holder";
	private static final String DEPENDENT = "Dependent";
	private static final String INSURANCE_MANAGER = "Insurance manager";
	private static final String INSURANCE_SURVEYOR = "Insurance surveyor";
	
	/**
	 * <p>Returns the appropriate LogInHandler based on the user type</p>
	 * Use to verify the user's credentials
	 *
	 * @param userType The type of user
	 *
	 * @return LogInHandler<?> object to authenticate user
	 */
	public LogInHandler getLogInHandler(String userType) {
		return switch (userType) {
			case POLICY_OWNER -> new PolicyOwnerLogInHandler();
			case SYSTEM_ADMIN -> new SystemAdminLogInHandler();
			case POLICY_HOLDER -> new PolicyHolderLogInHandler();
			case DEPENDENT -> new DependentLogInHandler();
			case INSURANCE_MANAGER -> new InsuranceManagerLogInHandler();
			case INSURANCE_SURVEYOR -> new InsuranceSurveyorLogInHandler();
			default -> null;
		};
	}
}
