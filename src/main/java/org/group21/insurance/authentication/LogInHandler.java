package org.group21.insurance.authentication;

import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;

public abstract class LogInHandler<T> {
	T user;
	
	public abstract T getUser(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException;
	
	public void logOut() {
		user = null;
	}
}
