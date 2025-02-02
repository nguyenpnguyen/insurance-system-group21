package org.group21.insurance.controllers;

/**
 * @author Group 21
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.group21.insurance.authentication.PasswordAuthenticator;
import org.group21.insurance.models.PolicyOwner;
import org.group21.insurance.utils.EntityManagerFactorySingleton;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PolicyOwnerControllerTest {
	private EntityManager em;
	private static EntityManagerFactory emf;
	private PolicyOwnerController poController;
	private List<PolicyOwner> createdEntities;
	
	@BeforeAll
	static void setUpAll() {
		emf = EntityManagerFactorySingleton.getInstance();
	}
	
	@AfterAll
	static void tearDownAll() {
		EntityManagerFactorySingleton.close();
	}
	
	@BeforeEach
	void setUp() {
		em = emf.createEntityManager();
		
		createdEntities = new ArrayList<>();
		poController = PolicyOwnerController.getInstance();
	}
	
	@AfterEach
	void tearDown() {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin(); // Start a transaction
			for (PolicyOwner entity : createdEntities) {
				PolicyOwner managedEntity = em.merge(entity); // Merge the entity back into the persistence context
				em.remove(managedEntity); // Remove the entity
			}
			em.getTransaction().commit(); // Commit the transaction
			createdEntities.clear(); // Clear the list for the next test
		}
	}
	
	@Test
	void create() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		PolicyOwner po = new PolicyOwner();
		po.setCustomerId("PolicyOwnerId_" + UUID.randomUUID().toString().substring(0, 8));
		po.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		po.setHashedPassword(pAuthenticator.hash(testPassword));
		po.setFullName("FullName_" + UUID.randomUUID().toString().substring(0, 8));
		po.setAddress("Address_" + UUID.randomUUID().toString().substring(0, 8));
		po.setEmail("Email_" + UUID.randomUUID().toString().substring(0, 8));
		po.setPhoneNumber("PhoneNumber_" + UUID.randomUUID().toString().substring(0, 8));
		
		poController.create(po);
		
		createdEntities.add(po);
		
		PolicyOwner savedPo = em.find(PolicyOwner.class, po.getCustomerId());
		
		assertNotNull(savedPo);
		assertEquals(po.getCustomerId(), savedPo.getCustomerId());
		assertEquals(po.getUsername(), savedPo.getUsername());
		assertEquals(po.getFullName(), savedPo.getFullName());
		assertEquals(po.getAddress(), savedPo.getAddress());
		assertEquals(po.getEmail(), savedPo.getEmail());
		assertEquals(po.getPhoneNumber(), savedPo.getPhoneNumber());
	}
	
	@Test
	void read() {
		PolicyOwner savedPo = createAndPersist();
		
		Optional<PolicyOwner> optionalPo = poController.read(savedPo.getCustomerId());
		
		assertTrue(optionalPo.isPresent());
		
		PolicyOwner retrievedPo = optionalPo.get();
		
		assertEquals(savedPo.getCustomerId(), retrievedPo.getCustomerId());
		assertEquals(savedPo.getUsername(), retrievedPo.getUsername());
		assertEquals(savedPo.getFullName(), retrievedPo.getFullName());
		assertEquals(savedPo.getAddress(), retrievedPo.getAddress());
		assertEquals(savedPo.getEmail(), retrievedPo.getEmail());
		assertEquals(savedPo.getPhoneNumber(), retrievedPo.getPhoneNumber());
	}
	
	@Test
	void readAll() {
		PolicyOwner po1 = createAndPersist();
		PolicyOwner po2 = createAndPersist();
		PolicyOwner po3 = createAndPersist();
		
		List<PolicyOwner> poList = poController.readAll();
		
		int expectedSize = 3; // Initial expected size
		expectedSize += createdEntities.size(); // Add the count of entities created during the test
		
		assertFalse(poList.isEmpty());
		assertEquals(expectedSize, poList.size());
		
		for (PolicyOwner po : List.of(po1, po2, po3)) {
			assertTrue(poList.contains(po));
		}
	}
	
	@Test
	void update() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		PolicyOwner savedPo = createAndPersist();
		
		savedPo.setUsername("NewUsername_" + UUID.randomUUID().toString().substring(0, 8));
		savedPo.setHashedPassword(pAuthenticator.hash(testPassword));
		savedPo.setFullName("NewFullName_" + UUID.randomUUID().toString().substring(0, 8));
		savedPo.setAddress("NewAddress_" + UUID.randomUUID().toString().substring(0, 8));
		savedPo.setEmail("NewEmail_" + UUID.randomUUID().toString().substring(0, 8));
		savedPo.setPhoneNumber("NewPhoneNumber_" + UUID.randomUUID().toString().substring(0, 8));
		
		poController.update(savedPo);
		
		PolicyOwner updatedPo = em.find(PolicyOwner.class, savedPo.getCustomerId());
		
		assertEquals(savedPo.getCustomerId(), updatedPo.getCustomerId());
		assertEquals(savedPo.getUsername(), updatedPo.getUsername());
		assertEquals(savedPo.getFullName(), updatedPo.getFullName());
		assertEquals(savedPo.getAddress(), updatedPo.getAddress());
		assertEquals(savedPo.getEmail(), updatedPo.getEmail());
		assertEquals(savedPo.getPhoneNumber(), updatedPo.getPhoneNumber());
	}
	
	@Test
	void delete() {
		PolicyOwner savedPo = createAndPersist();
		
		poController.delete(savedPo);
		
		PolicyOwner deletedPo = em.find(PolicyOwner.class, savedPo.getCustomerId());
		
		assertNull(deletedPo);
	}
	
	private PolicyOwner createAndPersist() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		PolicyOwner po = new PolicyOwner();
		po.setCustomerId("PolicyOwnerId_" + UUID.randomUUID().toString().substring(0, 8));
		po.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		po.setHashedPassword(pAuthenticator.hash(testPassword));
		po.setFullName("FullName_" + UUID.randomUUID().toString().substring(0, 8));
		po.setAddress("Address_" + UUID.randomUUID().toString().substring(0, 8));
		po.setEmail("Email_" + UUID.randomUUID().toString().substring(0, 8));
		po.setPhoneNumber("PhoneNumber_" + UUID.randomUUID().toString().substring(0, 8));
		
		createdEntities.add(po);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(po);
		em.getTransaction().commit();
		
		return po;
	}
	
	@Test
	void findByUsername() {
		PolicyOwner savedPo = createAndPersist();
		
		Optional<PolicyOwner> optionalPo = poController.findByUsername(savedPo.getUsername());
		
		assertTrue(optionalPo.isPresent());
		
		PolicyOwner retrievedPo = optionalPo.get();
		
		assertEquals(savedPo.getCustomerId(), retrievedPo.getCustomerId());
		assertEquals(savedPo.getUsername(), retrievedPo.getUsername());
	}
}