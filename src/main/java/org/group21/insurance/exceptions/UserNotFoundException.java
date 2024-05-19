package org.group21.insurance.exceptions;

/**
 * Exception thrown when a user is not found.
 *
 * @author Group 21
 */
public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}
}
