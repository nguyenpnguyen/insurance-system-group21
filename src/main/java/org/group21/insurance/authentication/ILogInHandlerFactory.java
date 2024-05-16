package org.group21.insurance.authentication;

public interface ILogInHandlerFactory {
	LogInHandler<?> getLogInHandler(String userType);
}
