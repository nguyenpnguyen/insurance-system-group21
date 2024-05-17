package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.group21.insurance.authentication.PasswordAuthenticator;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.models.InsuranceCard;
import org.group21.insurance.utils.EntityManagerFactorySingleton;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceCardControllerTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private InsuranceCardController icController;
	private List<Object> createdEntities;
	
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
		icController = InsuranceCardController.getInstance();
	}
	
	@AfterEach
	void tearDown() {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			for (Object entity : createdEntities) {
				if (entity instanceof InsuranceCard ic) {
					InsuranceCard found = em.find(InsuranceCard.class, ic.getCardNumber());
					if (found != null) {
						em.remove(found);
					}
				} else if (entity instanceof Beneficiary beneficiary) {
					Beneficiary found = em.find(Beneficiary.class, beneficiary.getCustomerId());
					if (found != null) {
						em.remove(found);
					}
				}
			}
			em.getTransaction().commit();
			createdEntities.clear();
		}
	}
	
	@Test
	void create() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		Beneficiary testBeneficiary = new Beneficiary();
		testBeneficiary.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		testBeneficiary.setHashedPassword(pAuthenticator.hash(testPassword));
		testBeneficiary.setIsPolicyHolder(true);
		
		createdEntities.add(testBeneficiary);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(testBeneficiary);
		em.getTransaction().commit();
		
		InsuranceCard ic = new InsuranceCard();
		ic.setExpirationDate(LocalDate.now());
		ic.setCardHolder(testBeneficiary);
		
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
	}
	
	@Test
	void readAll() {
		InsuranceCard ic1 = createAndPersist();
		InsuranceCard ic2 = createAndPersist();
		InsuranceCard ic3 = createAndPersist();
		
		List<InsuranceCard> insuranceCards = icController.readAll();
		
		int expectedSize = 6; // Initial expected size
		
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
		
		savedIc.setExpirationDate(LocalDate.now().plusDays(1));
		icController.update(savedIc);
		
		InsuranceCard updatedIc = em.find(InsuranceCard.class, savedIc.getCardNumber());
		
		assertNotNull(updatedIc);
		assertEquals(savedIc.getCardNumber(), updatedIc.getCardNumber());
		assertEquals(savedIc.getExpirationDate(), updatedIc.getExpirationDate());
		assertEquals(savedIc.getCardHolder().getCustomerId(), updatedIc.getCardHolder().getCustomerId());
	}
	
	@Test
	void delete() {
		InsuranceCard savedIc = createAndPersist();
		
		icController.delete(savedIc);
		
		InsuranceCard deletedIc = em.find(InsuranceCard.class, savedIc.getCardNumber());
		
		assertNull(deletedIc);
	}
	
	private InsuranceCard createAndPersist() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		Beneficiary testBeneficiary = new Beneficiary();
		testBeneficiary.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		testBeneficiary.setHashedPassword(pAuthenticator.hash(testPassword));
		testBeneficiary.setIsPolicyHolder(true);
		
		createdEntities.add(testBeneficiary);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(testBeneficiary);
		em.getTransaction().commit();
		
		InsuranceCard ic = new InsuranceCard();
		ic.setExpirationDate(LocalDate.now());
		ic.setCardHolder(testBeneficiary);
		
		createdEntities.add(ic);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(ic);
		em.getTransaction().commit();
		return ic;
	}
}