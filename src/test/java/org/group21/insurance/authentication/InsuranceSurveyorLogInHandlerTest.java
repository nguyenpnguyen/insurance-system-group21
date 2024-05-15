package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.InsuranceProvider;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InsuranceSurveyorLogInHandlerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static final String TEST_USERNAME = "surveyor";
	private static final String TEST_PASSWORD = "testPassword";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static InsuranceSurveyorLogInHandler insuranceSurveyorLogInHandler;
	
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
		insuranceSurveyorLogInHandler = InsuranceSurveyorLogInHandler.getInstance();
	}
	
	@AfterEach
	void tearDown() {
		em.close();
	}
	
	@Test
	void isAuthenticated() {
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		String testPassword = passwordAuthenticator.hash(TEST_PASSWORD.toCharArray());
		
		// Create an insurance surveyor entity
		InsuranceProvider insuranceSurveyor = new InsuranceProvider();
		insuranceSurveyor.setUsername(TEST_USERNAME);
		insuranceSurveyor.setHashedPassword(testPassword);
		insuranceSurveyor.setInsuranceManager(false);
		
		// Persist the insurance surveyor entity
		em.getTransaction().begin();
		em.persist(insuranceSurveyor);
		em.getTransaction().commit();
		
		boolean authenticated = insuranceSurveyorLogInHandler.isAuthenticated(em, TEST_USERNAME, TEST_PASSWORD);
		
		assertTrue(authenticated);
		
		// Clean up
		em.getTransaction().begin();
		em.remove(insuranceSurveyor);
		em.getTransaction().commit();
	}
}