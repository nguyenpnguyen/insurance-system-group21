package org.group21.insurance.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordAuthenticatorTest {
	
	private PasswordAuthenticator passwordAuthenticator;
	
	@BeforeEach
	void setUp() {
		passwordAuthenticator = new PasswordAuthenticator();
	}
	
	@Test
	void hash() {
		char[] password = "testPassword".toCharArray();
		String hashedPassword = passwordAuthenticator.hash(password);
		
		assertNotNull(hashedPassword);
		assertTrue(hashedPassword.startsWith(PasswordAuthenticator.ID));
	}
	
	@Test
	void authenticate() {
		char[] password = "testPassword".toCharArray();
		String hashedPassword = passwordAuthenticator.hash(password);
		
		assertTrue(passwordAuthenticator.authenticate(password, hashedPassword));
		assertFalse(passwordAuthenticator.authenticate("wrongPassword".toCharArray(), hashedPassword));
	}
}
