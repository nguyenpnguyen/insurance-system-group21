package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.Beneficiary;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DependentLogInHandlerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static final String TEST_USERNAME = "dependent";
	private static final String TEST_PASSWORD = "testPassword";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static DependentLogInHandler dependentLogInHandler;
	
	@BeforeAll
	static void setUpAll() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	@AfterAll
	static void tearDownAll() {
		emf.close();
	}
	
	@BeforeEach
	void setUp() {
		em = emf.createEntityManager();
		dependentLogInHandler = new DependentLogInHandler();
	}
	
	@AfterEach
	void tearDown() {
		em.close();
	}
	
	@Test
	void isAuthenticated() {
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		String testPassword = passwordAuthenticator.hash(TEST_PASSWORD.toCharArray());
		
		// Create a dependent entity
		Beneficiary dependent = new Beneficiary();
		dependent.setUsername(TEST_USERNAME);
		dependent.setHashedPassword(testPassword);
		dependent.setIsPolicyHolder(false);
		
		// Persist the dependent entity
		em.getTransaction().begin();
		em.persist(dependent);
		em.getTransaction().commit();
		
		boolean authenticated = dependentLogInHandler.isAuthenticated(em, TEST_USERNAME, TEST_PASSWORD);
		
		assertTrue(authenticated);
		
		// Clean up
		em.getTransaction().begin();
		em.remove(dependent);
		em.getTransaction().commit();
	}
}