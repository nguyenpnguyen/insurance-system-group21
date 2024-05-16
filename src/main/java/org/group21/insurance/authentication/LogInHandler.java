package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import org.group21.insurance.exceptions.UserNotAuthenticatedException;
import org.group21.insurance.exceptions.UserNotFoundException;

public abstract class LogInHandler<T> {
	public abstract T getUser(EntityManager em, String username, String password) throws UserNotFoundException, UserNotAuthenticatedException;
}
