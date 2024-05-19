package org.group21.insurance.exceptions;

/**
 * Exception thrown when a user is not authenticated.
 *
 * @author Group 21
 */
public class UserNotAuthenticatedException extends Exception {
	public UserNotAuthenticatedException(String message) {
		super(message);
	}
}
