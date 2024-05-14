package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.Beneficiary;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PolicyHolderLogInHandlerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static final String TEST_USERNAME = "policyHolder";
	private static final String TEST_PASSWORD = "testPassword";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static PolicyHolderLogInHandler policyHolderLogInHandler;
	
	@BeforeAll
	static void setUpAll() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		em = emf.createEntityManager();
	}
	
	@AfterAll
	static void tearDownAll() {
		em.close();
		emf.close();
	}
	
	@BeforeEach
	void setUp() {
		policyHolderLogInHandler = PolicyHolderLogInHandler.getInstance(em);
	}
	
	@Test
	void isAuthenticated() {
		// Create a policy holder entity
		Beneficiary policyHolder = new Beneficiary();
		policyHolder.setUsername(TEST_USERNAME);
		policyHolder.setHashedPassword(TEST_PASSWORD);
		policyHolder.setIsPolicyHolder(true);
		
		// Persist the policy holder entity
		em.getTransaction().begin();
		em.persist(policyHolder);
		em.getTransaction().commit();
		
		boolean authenticated = policyHolderLogInHandler.isAuthenticated(TEST_USERNAME, TEST_PASSWORD);
		
		assertTrue(authenticated);
		
		// Clean up
		em.getTransaction().begin();
		em.remove(policyHolder);
		em.getTransaction().commit();
	}
}