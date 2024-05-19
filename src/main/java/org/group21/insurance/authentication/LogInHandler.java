package org.group21.insurance.authentication;

/**
 * @author Group 21
 */

import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;

public interface LogInHandler {
	String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException;
}
