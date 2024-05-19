package org.group21.insurance.exceptions;

/**
 * @author Group 21
 */
public class UserNotAuthenticatedException extends Exception {
	public UserNotAuthenticatedException(String message) {
		super(message);
	}
}
