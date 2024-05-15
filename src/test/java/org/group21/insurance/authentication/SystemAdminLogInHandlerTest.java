package org.group21.insurance.authentication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.SystemAdmin;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemAdminLogInHandlerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static final String TEST_USERNAME = "testAdmin";
	private static final String TEST_PASSWORD = "testPassword";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static SystemAdminLogInHandler systemAdminLogInHandler;
	
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
		systemAdminLogInHandler = new SystemAdminLogInHandler();
	}
	
	@AfterEach
	void tearDown() {
		em.close();
	}
	
	@Test
	void isAuthenticated() {
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		String testPassword = passwordAuthenticator.hash(TEST_PASSWORD.toCharArray());
		
		// Create a system admin entity
		SystemAdmin systemAdmin = new SystemAdmin();
		systemAdmin.setUsername(TEST_USERNAME);
		systemAdmin.setHashedPassword(testPassword);
		
		// Persist the system admin entity
		em.getTransaction().begin();
		em.persist(systemAdmin);
		em.getTransaction().commit();
		
		boolean authenticated = systemAdminLogInHandler.isAuthenticated(em, TEST_USERNAME, TEST_PASSWORD);
		
		assertTrue(authenticated);
		
		// Clean up
		em.getTransaction().begin();
		em.remove(systemAdmin);
		em.getTransaction().commit();
	}
}