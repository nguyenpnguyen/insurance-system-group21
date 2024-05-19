package org.group21.insurance.authentication;


import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;

/**
 * Interface for LogInHandler classes
 *
 * @author Group 21
 */
public interface LogInHandler {
	/**
	 * Get the user ID of a user entity by its username and password.
	 *
	 * @param username the username of the user entity
	 * @param password the password of the user entity
	 *
	 * @return the user ID of the user entity
	 *
	 * @throws UserNotFoundException         if the user entity with the given username is not found
	 * @throws UserNotAuthenticatedException if the user entity with the given username is found but the password is
	 *                                       incorrect
	 */
	String getUserId(String username, String password) throws UserNotFoundException, UserNotAuthenticatedException;
}
