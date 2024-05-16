package org.group21.insurance.exceptions;

public class UserNotAuthenticatedException extends Exception {
	public UserNotAuthenticatedException(String message) {
		super(message);
	}
}
