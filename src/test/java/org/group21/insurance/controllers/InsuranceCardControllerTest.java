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

class InsuranceCardControllerTest implements ControllerTest<InsuranceCard> {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private InsuranceCardController icController;
	private final List<InsuranceCard> createdEntities = new ArrayList<>();
	
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
		icController = new InsuranceCardController(em);
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
	public void create() {
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
	public void read() {
		InsuranceCard savedIc = createAndPersist();
		
		Optional<InsuranceCard> optionalIc = icController.read(savedIc.getCardNumber());
		
		createdEntities.add(savedIc);
		
		assertTrue(optionalIc.isPresent());
		
		InsuranceCard retrievedIc = optionalIc.get();
		
		assertEquals(savedIc.getCardNumber(), retrievedIc.getCardNumber());
		assertEquals(savedIc.getExpirationDate(), retrievedIc.getExpirationDate());
		assertEquals(savedIc.getCardHolder().getCustomerId(), retrievedIc.getCardHolder().getCustomerId());
		assertEquals(savedIc.getPolicyOwner().getCustomerId(), retrievedIc.getPolicyOwner().getCustomerId());
	}
	
	@Test
	public void readAll() {
		InsuranceCard ic1 = new InsuranceCard();
		ic1.setCardNumber("1234567890");
		ic1.setExpirationDate(LocalDate.now());
		icController.create(ic1);
		
		InsuranceCard ic2 = new InsuranceCard();
		ic2.setCardNumber("0987654321");
		ic2.setExpirationDate(LocalDate.now());
		icController.create(ic2);
		
		createdEntities.add(ic1);
		createdEntities.add(ic2);
		
		List<InsuranceCard> insuranceCards = icController.readAll();
		
		assertNotNull(insuranceCards);
		assertEquals(2, insuranceCards.size());
		assertTrue(insuranceCards.stream().anyMatch(ic -> ic.getCardNumber().equals(ic1.getCardNumber())));
		assertTrue(insuranceCards.stream().anyMatch(ic -> ic.getCardNumber().equals(ic2.getCardNumber())));
	}
	
	
	@Test
	public void update() {
		InsuranceCard savedIc = createAndPersist();
		
		createdEntities.add(savedIc);
		
		savedIc.setCardNumber("UpdatedCardNumber");
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
	public void delete() {
		InsuranceCard savedIc = createAndPersist();
		
		createdEntities.add(savedIc);
		
		icController.delete(savedIc);
		
		InsuranceCard deletedIc = em.find(InsuranceCard.class, savedIc.getCardNumber());
		
		assertNull(deletedIc);
	}
	
	@Override
	public InsuranceCard createAndPersist() {
		InsuranceCard ic = new InsuranceCard();
		// Set unique values for accountNumber, bank, and name
		ic.setCardNumber("CardNumber_" + UUID.randomUUID().toString().substring(0, 8));
		ic.setExpirationDate(LocalDate.now());
		
		em.getTransaction().begin();
		em.persist(ic);
		em.getTransaction().commit();
		return ic;
	}
}