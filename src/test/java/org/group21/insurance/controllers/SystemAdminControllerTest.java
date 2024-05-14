package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.authentication.PasswordAuthenticator;
import org.group21.insurance.models.SystemAdmin;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SystemAdminControllerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private SystemAdminController systemAdminController;
	private List<SystemAdmin> createdEntities;
	
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
		createdEntities = new ArrayList<>();
		systemAdminController = SystemAdminController.getInstance(em);
	}
	
	@AfterEach
	void tearDown() {
		for (SystemAdmin entity : createdEntities) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}
		createdEntities.clear();
		systemAdminController = null;
	}
	
	@Test
	void create() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		
		SystemAdmin testAdmin = new SystemAdmin();
		testAdmin.setUsername("testAdmin");
		testAdmin.setHashedPassword(pAuthenticator.hash("testAdmin".toCharArray()));
		
		systemAdminController.create(testAdmin);
		
		createdEntities.add(testAdmin);
		
		SystemAdmin createdAdmin = em.find(SystemAdmin.class, testAdmin.getSysAdminId());
		
		assertNotNull(createdAdmin);
		assertEquals(testAdmin.getSysAdminId(), createdAdmin.getSysAdminId());
		assertEquals(testAdmin.getUsername(), createdAdmin.getUsername());
	}
	
	@Test
	void read() {
		SystemAdmin savedAdmin = createAndPersist();
		
		Optional<SystemAdmin> optionalAdmin = systemAdminController.read(Long.toString(savedAdmin.getSysAdminId()));
		
		assertTrue(optionalAdmin.isPresent());
		
		SystemAdmin retrievedAdmin = optionalAdmin.get();
		
		assertEquals(savedAdmin.getSysAdminId(), retrievedAdmin.getSysAdminId());
		assertEquals(savedAdmin.getUsername(), retrievedAdmin.getUsername());
	}
	
	@Test
	void readAll() {
		SystemAdmin savedAdmin = createAndPersist();
		
		List<SystemAdmin> admins = systemAdminController.readAll();
		
		assertNotNull(admins);
		assertEquals(1, admins.size());
		
		SystemAdmin retrievedAdmin = admins.get(0);
		
		assertEquals(savedAdmin.getSysAdminId(), retrievedAdmin.getSysAdminId());
		assertEquals(savedAdmin.getUsername(), retrievedAdmin.getUsername());
	}
	
	@Test
	void update() {
		SystemAdmin savedAdmin = createAndPersist();
		
		savedAdmin.setUsername("updatedUsername");
		
		systemAdminController.update(savedAdmin);
		
		SystemAdmin updatedAdmin = em.find(SystemAdmin.class, savedAdmin.getSysAdminId());
		
		assertNotNull(updatedAdmin);
		assertEquals(savedAdmin.getSysAdminId(), updatedAdmin.getSysAdminId());
		assertEquals(savedAdmin.getUsername(), updatedAdmin.getUsername());
	}
	
	@Test
	void delete() {
		SystemAdmin savedAdmin = createAndPersist();
		
		systemAdminController.delete(savedAdmin);
		
		SystemAdmin deletedAdmin = em.find(SystemAdmin.class, savedAdmin.getSysAdminId());
		
		assertNull(deletedAdmin);
	}
	
	private SystemAdmin createAndPersist() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		
		SystemAdmin admin = new SystemAdmin();
		admin.setUsername("admin");
		admin.setHashedPassword(pAuthenticator.hash("admin".toCharArray()));
		
		createdEntities.add(admin);
		
		em.getTransaction().begin();
		em.persist(admin);
		em.getTransaction().commit();
		
		return admin;
	}
}