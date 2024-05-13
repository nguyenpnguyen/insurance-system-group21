package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.BankingInfo;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BankingInfoControllerTest implements ControllerTest<BankingInfo> {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private BankingInfoController biController;
	private final List<BankingInfo> createdEntities = new ArrayList<>();
	
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
		biController = new BankingInfoController(em);
	}
	
	@AfterEach
	void tearDown() {
		for (BankingInfo entity : createdEntities) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}
		createdEntities.clear();
		biController = null;
	}
	
	@Test
	public void create() {
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
	public void read() {
		// Create a BankingInfo entity and persist it
		BankingInfo savedBi = createAndPersist();
		
		// Read the BankingInfo using its ID
		Optional<BankingInfo> optionalBi = biController.read(savedBi.getAccountNumber());
		
		// Add the created entity to the list of created entities
		createdEntities.add(savedBi);
		
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
	public void readAll() {
		// Create and persist multiple BankingInfo entities
		BankingInfo bi1 = createAndPersist();
		BankingInfo bi2 = createAndPersist();
		BankingInfo bi3 = createAndPersist();
		
		// Add the created entities to the list of created entities
		createdEntities.add(bi1);
		createdEntities.add(bi2);
		createdEntities.add(bi3);
		
		// Read all BankingInfo entities
		List<BankingInfo> bankingInfoList = biController.readAll();
		
		// Count the number of entities created during the test
		int expectedSize = 3; // Initial expected size
		expectedSize += createdEntities.size(); // Add the count of entities created during the test
		
		// Assert that the list is not empty and contains the expected number of elements
		assertFalse(bankingInfoList.isEmpty());
		assertEquals(expectedSize, bankingInfoList.size());
	}
	
	@Test
	public void update() {
		// Create and persist a BankingInfo entity
		BankingInfo savedBi = createAndPersist();
		
		// Add the created entity to the list of created entities
		createdEntities.add(savedBi);
		
		// Update the BankingInfo entity
		savedBi.setAccountNumber("UpdatedAccountNumber");
		savedBi.setBank("UpdatedBank");
		savedBi.setName("UpdatedName");
		biController.update(savedBi);
		
		// Retrieve the updated BankingInfo from the database
		BankingInfo updatedBi = em.find(BankingInfo.class, savedBi.getAccountNumber());
		
		// Assert that the updatedBi has the correct values
		assertEquals("UpdatedAccountNumber", updatedBi.getAccountNumber());
		assertEquals("UpdatedBank", updatedBi.getBank());
		assertEquals("UpdatedName", updatedBi.getName());
	}
	
	@Test
	public void delete() {
		// Create and persist a BankingInfo entity
		BankingInfo savedBi = createAndPersist();
		
		// Add the created entity to the list of created entities
		createdEntities.add(savedBi);
		
		// Delete the BankingInfo entity
		biController.delete(savedBi);
		
		// Try to retrieve the deleted BankingInfo from the database
		BankingInfo deletedBi = em.find(BankingInfo.class, savedBi.getAccountNumber());
		
		// Assert that the deletedBi is null, indicating deletion
		assertNull(deletedBi);
	}
	
	// Helper method to create and persist a BankingInfo entity
	public BankingInfo createAndPersist() {
		BankingInfo bi = new BankingInfo();
		// Set unique values for accountNumber, bank, and name
		bi.setAccountNumber("AccountNumber_" + UUID.randomUUID().toString().substring(0, 8));
		bi.setBank("Bank_" + UUID.randomUUID().toString().substring(0, 8));
		bi.setName("Name_" + UUID.randomUUID().toString().substring(0, 8));
		
		em.getTransaction().begin();
		em.persist(bi);
		em.getTransaction().commit();
		return bi;
	}
}
