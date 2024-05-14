package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.InsuranceCard;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceCardControllerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private InsuranceCardController icController;
	private List<InsuranceCard> createdEntities;
	
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
		icController = InsuranceCardController.getInstance(em);
	}
	
	@AfterEach
	void tearDown() {
		for (InsuranceCard entity : createdEntities) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}
		createdEntities.clear();
		icController = null;
	}
	
	@Test
	void create() {
		InsuranceCard ic = new InsuranceCard();
		ic.setCardNumber("1234567890");
		ic.setExpirationDate(LocalDate.now());
		icController.create(ic);
		
		createdEntities.add(ic);
		
		InsuranceCard savedIc = em.find(InsuranceCard.class, ic.getCardNumber());
		
		assertNotNull(savedIc);
		assertEquals(ic.getCardNumber(), savedIc.getCardNumber());
		assertEquals(ic.getExpirationDate(), savedIc.getExpirationDate());
		assertEquals(ic.getCardHolder().getCustomerId(), savedIc.getCardHolder().getCustomerId());
		assertEquals(ic.getPolicyOwner().getCustomerId(), savedIc.getPolicyOwner().getCustomerId());
	}
	
	@Test
	void read() {
		InsuranceCard savedIc = createAndPersist();
		
		Optional<InsuranceCard> optionalIc = icController.read(savedIc.getCardNumber());
		
		assertTrue(optionalIc.isPresent());
		
		InsuranceCard retrievedIc = optionalIc.get();
		
		assertEquals(savedIc.getCardNumber(), retrievedIc.getCardNumber());
		assertEquals(savedIc.getExpirationDate(), retrievedIc.getExpirationDate());
		assertEquals(savedIc.getCardHolder().getCustomerId(), retrievedIc.getCardHolder().getCustomerId());
		assertEquals(savedIc.getPolicyOwner().getCustomerId(), retrievedIc.getPolicyOwner().getCustomerId());
	}
	
	@Test
	void readAll() {
		InsuranceCard ic1 = createAndPersist();
		InsuranceCard ic2 = createAndPersist();
		InsuranceCard ic3 = createAndPersist();
		
		List<InsuranceCard> insuranceCards = icController.readAll();
		
		int expectedSize = 3; // Initial expected size
		expectedSize += createdEntities.size(); // Add the count of entities created during the test
		
		// Assert that the list is not empty and contains the expected number of elements
		assertFalse(insuranceCards.isEmpty());
		assertEquals(expectedSize, insuranceCards.size());
		
		for (InsuranceCard insuranceCard : List.of(ic1, ic2, ic3)) {
			assertTrue(insuranceCards.contains(insuranceCard));
		}
	}
	
	
	@Test
	void update() {
		InsuranceCard savedIc = createAndPersist();
		
		savedIc.setCardNumber("UpdatedCardNumber_" + UUID.randomUUID().toString().substring(0, 8));
		savedIc.setExpirationDate(LocalDate.now().plusDays(1));
		icController.update(savedIc);
		
		InsuranceCard updatedIc = em.find(InsuranceCard.class, savedIc.getCardNumber());
		
		assertNotNull(updatedIc);
		assertEquals(savedIc.getCardNumber(), updatedIc.getCardNumber());
		assertEquals(savedIc.getExpirationDate(), updatedIc.getExpirationDate());
		assertEquals(savedIc.getCardHolder().getCustomerId(), updatedIc.getCardHolder().getCustomerId());
		assertEquals(savedIc.getPolicyOwner().getCustomerId(), updatedIc.getPolicyOwner().getCustomerId());
	}
	
	@Test
	void delete() {
		InsuranceCard savedIc = createAndPersist();
		
		icController.delete(savedIc);
		
		InsuranceCard deletedIc = em.find(InsuranceCard.class, savedIc.getCardNumber());
		
		assertNull(deletedIc);
	}
	
	private InsuranceCard createAndPersist() {
		InsuranceCard ic = new InsuranceCard();
		// Set unique values for accountNumber, bank, and name
		ic.setCardNumber("CardNumber_" + UUID.randomUUID().toString().substring(0, 8));
		ic.setExpirationDate(LocalDate.now());
		
		createdEntities.add(ic);
		
		em.getTransaction().begin();
		em.persist(ic);
		em.getTransaction().commit();
		return ic;
	}
}