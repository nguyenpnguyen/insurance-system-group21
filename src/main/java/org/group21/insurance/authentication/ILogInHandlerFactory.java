package org.group21.insurance.authentication;

/**
 * @author Group 21
 */
public interface ILogInHandlerFactory {
	LogInHandler getLogInHandler(String userType);
}
