package org.group21.insurance.authentication;

/**
 * Interface for LogInHandlerFactory classes
 *
 * @author Group 21
 */
public interface ILogInHandlerFactory {
	/**
	 * <p>Returns the appropriate LogInHandler based on the user type</p>
	 * Use to verify the user's credentials
	 *
	 * @param userType The type of user
	 *
	 * @return LogInHandler<?> object to authenticate user
	 */
	LogInHandler getLogInHandler(String userType);
}
