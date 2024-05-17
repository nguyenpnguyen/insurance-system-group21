package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.group21.insurance.models.BankingInfo;
import org.group21.insurance.utils.EntityManagerFactorySingleton;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BankingInfoControllerTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private BankingInfoController biController;
	private List<BankingInfo> createdEntities;
	
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
		biController = BankingInfoController.getInstance();
		createdEntities = new ArrayList<>();
	}
	
	@AfterEach
	void tearDown() {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin(); // Start a transaction
			for (BankingInfo entity : createdEntities) {
				BankingInfo managedEntity = em.merge(entity); // Merge the entity back into the persistence context
				em.remove(managedEntity); // Remove the entity
			}
			em.getTransaction().commit(); // Commit the transaction
			createdEntities.clear(); // Clear the list for the next test
		}
	}
	
	@Test
	void create() {
		BankingInfo bi = new BankingInfo();
		// Create a new BankingInfo entity
		bi.setAccountNumber("1234567890");
		bi.setBank("Bank of America");
		bi.setName("John Doe");
		biController.create(bi);
		
		// Add the created entity to the list of created entities
		createdEntities.add(bi);
		
		// Retrieve the BankingInfo from the database
		BankingInfo savedBi = em.find(BankingInfo.class, bi.getAccountNumber());
		
		// Assert that the savedBi is not null
		assertNotNull(savedBi);
		// Assert that the savedBi has the correct values
		assertEquals(bi.getAccountNumber(), savedBi.getAccountNumber());
		assertEquals(bi.getBank(), savedBi.getBank());
		assertEquals(bi.getName(), savedBi.getName());
	}
	
	@Test
	void read() {
		// Create a BankingInfo entity and persist it
		BankingInfo savedBi = createAndPersist();
		
		// Read the BankingInfo using its ID
		Optional<BankingInfo> optionalBi = biController.read(savedBi.getAccountNumber());
		
		// Assert that the optionalBi is present
		assertTrue(optionalBi.isPresent());
		
		// Retrieve the BankingInfo from the optional
		BankingInfo retrievedBi = optionalBi.get();
		
		// Assert that the retrievedBi has the correct values
		assertEquals(savedBi.getAccountNumber(), retrievedBi.getAccountNumber());
		assertEquals(savedBi.getBank(), retrievedBi.getBank());
		assertEquals(savedBi.getName(), retrievedBi.getName());
	}
	
	@Test
	void readAll() {
		// Create and persist multiple BankingInfo entities
		BankingInfo bi1 = createAndPersist();
		BankingInfo bi2 = createAndPersist();
		BankingInfo bi3 = createAndPersist();
		
		// Read all BankingInfo entities
		List<BankingInfo> bankingInfoList = biController.readAll();
		
		// Count the number of entities created during the test
		int expectedSize = 3; // Initial expected size
		
		// Assert that the list is not empty and contains the expected number of elements
		assertFalse(bankingInfoList.isEmpty());
		assertEquals(expectedSize, bankingInfoList.size());
		
		// Assert that the list contains the created entities
		for (BankingInfo bi : List.of(bi1, bi2, bi3)) {
			assertTrue(bankingInfoList.contains(bi));
		}
	}
	
	@Test
	void update() {
		// Create and persist a BankingInfo entity
		BankingInfo savedBi = createAndPersist();
		
		// Update the BankingInfo entity
		savedBi.setBank("UpdatedBank_" + UUID.randomUUID().toString().substring(0, 8));
		savedBi.setName("UpdatedName_" + UUID.randomUUID().toString().substring(0, 8));
		biController.update(savedBi);
		
		// Retrieve the updated BankingInfo from the database
		BankingInfo updatedBi = em.find(BankingInfo.class, savedBi.getAccountNumber());
		
		// Assert that the updatedBi has the correct values
		assertEquals(savedBi.getAccountNumber(), updatedBi.getAccountNumber());
		assertEquals(savedBi.getBank(), updatedBi.getBank());
		assertEquals(savedBi.getName(), updatedBi.getName());
	}
	
	@Test
	void delete() {
		// Create and persist a BankingInfo entity
		BankingInfo savedBi = createAndPersist();
		
		// Delete the BankingInfo entity
		biController.delete(savedBi);
		
		// Try to retrieve the deleted BankingInfo from the database
		BankingInfo deletedBi = em.find(BankingInfo.class, savedBi.getAccountNumber());
		
		// Assert that the deletedBi is null, indicating deletion
		assertNull(deletedBi);
	}
	
	// Helper method to create and persist a BankingInfo entity
	private BankingInfo createAndPersist() {
		BankingInfo bi = new BankingInfo();
		// Set unique values for accountNumber, bank, and name
		bi.setAccountNumber("AccountNumber_" + UUID.randomUUID().toString().substring(0, 8));
		bi.setBank("Bank_" + UUID.randomUUID().toString().substring(0, 8));
		bi.setName("Name_" + UUID.randomUUID().toString().substring(0, 8));
		
		createdEntities.add(bi);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(bi);
		em.getTransaction().commit();
		return bi;
	}
}
