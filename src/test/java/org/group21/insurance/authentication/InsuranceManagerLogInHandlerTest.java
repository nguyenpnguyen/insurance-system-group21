package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.InsuranceProvider;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InsuranceManagerLogInHandlerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static final String TEST_USERNAME = "insuranceManager";
	private static final String TEST_PASSWORD = "testPassword";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static InsuranceManagerLogInHandler insuranceManagerLogInHandler;
	
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
		insuranceManagerLogInHandler = new InsuranceManagerLogInHandler();
	}
	
	@AfterEach
	void tearDown() {
		em.close();
	}
	
	@Test
	void isAuthenticated() {
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		String testPassword = passwordAuthenticator.hash(TEST_PASSWORD.toCharArray());
		
		// Create an insurance manager entity
		InsuranceProvider insuranceManager = new InsuranceProvider();
		insuranceManager.setUsername(TEST_USERNAME);
		insuranceManager.setHashedPassword(testPassword);
		insuranceManager.setInsuranceManager(true);
		
		// Persist the insurance manager entity
		em.getTransaction().begin();
		em.persist(insuranceManager);
		em.getTransaction().commit();
		
		boolean authenticated = insuranceManagerLogInHandler.isAuthenticated(em, TEST_USERNAME, TEST_PASSWORD);
		
		assertTrue(authenticated);
		
		// Clean up
		em.getTransaction().begin();
		em.remove(insuranceManager);
		em.getTransaction().commit();
	}
}