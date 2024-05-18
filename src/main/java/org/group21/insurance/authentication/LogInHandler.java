package org.group21.insurance.authentication;

import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;

public interface LogInHandler {
	String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException;
}
