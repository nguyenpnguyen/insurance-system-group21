package org.group21.insurance.controllers;

/**
 * @author Group 21
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.group21.insurance.models.Claim;
import org.group21.insurance.utils.EntityManagerFactorySingleton;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClaimControllerTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private ClaimController claimController;
	private List<Claim> createdEntities;
	
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
		claimController = ClaimController.getInstance();
	}
	
	@AfterEach
	void tearDown() {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin(); // Start a transaction
			for (Claim entity : createdEntities) {
				Claim managedEntity = em.merge(entity); // Merge the entity back into the persistence context
				em.remove(managedEntity); // Remove the entity
			}
			em.getTransaction().commit(); // Commit the transaction
			createdEntities.clear(); // Clear the list for the next test
		}
	}
	
	@Test
	void create() {
		Claim claim = new Claim();
		claim.setClaimId("ClaimId_" + UUID.randomUUID().toString().substring(0, 8));
		claim.setClaimAmount(10000L);
		claim.setClaimDate(LocalDate.now());
		claim.setExamDate(LocalDate.now().plusDays(1));
		claim.setStatus(Claim.ClaimStatus.NEW);
		
		claimController.create(claim);
		
		createdEntities.add(claim);
		
		Claim savedClaim = em.find(Claim.class, claim.getClaimId());
		
		assertNotNull(savedClaim);
		assertEquals(claim.getClaimId(), savedClaim.getClaimId());
		assertEquals(claim.getClaimAmount(), savedClaim.getClaimAmount());
		assertEquals(claim.getClaimDate(), savedClaim.getClaimDate());
		assertEquals(claim.getExamDate(), savedClaim.getExamDate());
		assertEquals(claim.getStatus(), savedClaim.getStatus());
	}
	
	@Test
	void read() {
		Claim savedClaim = createAndPersist();
		
		Optional<Claim> optionalClaim = claimController.read(savedClaim.getClaimId());
		
		assertTrue(optionalClaim.isPresent());
		
		Claim retrievedClaim = optionalClaim.get();
		
		assertNotNull(retrievedClaim);
		assertEquals(savedClaim.getClaimId(), retrievedClaim.getClaimId());
		assertEquals(savedClaim.getClaimAmount(), retrievedClaim.getClaimAmount());
		assertEquals(savedClaim.getClaimDate(), retrievedClaim.getClaimDate());
		assertEquals(savedClaim.getExamDate(), retrievedClaim.getExamDate());
		assertEquals(savedClaim.getStatus(), retrievedClaim.getStatus());
	}
	
	@Test
	void readAll() {
		Claim c1 = createAndPersist();
		Claim c2 = createAndPersist();
		Claim c3 = createAndPersist();
		
		List<Claim> claims = claimController.readAll();
		
		int expectedSize = 3;
		expectedSize += createdEntities.size(); // Add the count of entities created during the test
		
		assertFalse(claims.isEmpty());
		assertEquals(expectedSize, claims.size());
		
		for (Claim claim : List.of(c1, c2, c3)) {
			assertTrue(claims.contains(claim));
		}
	}
	
	@Test
	void update() {
		Claim savedClaim = createAndPersist();
		
		savedClaim.setClaimAmount(20000L);
		savedClaim.setClaimDate(LocalDate.now().minusDays(1));
		savedClaim.setExamDate(LocalDate.now().minusDays(2));
		savedClaim.setStatus(Claim.ClaimStatus.PROCESSING);
		
		claimController.update(savedClaim);
		
		Claim updatedClaim = em.find(Claim.class, savedClaim.getClaimId());
		
		assertNotNull(updatedClaim);
		assertEquals(savedClaim.getClaimId(), updatedClaim.getClaimId());
		assertEquals(savedClaim.getClaimAmount(), updatedClaim.getClaimAmount());
		assertEquals(savedClaim.getClaimDate(), updatedClaim.getClaimDate());
		assertEquals(savedClaim.getExamDate(), updatedClaim.getExamDate());
		assertEquals(savedClaim.getStatus(), updatedClaim.getStatus());
	}
	
	@Test
	void delete() {
		Claim savedClaim = createAndPersist();
		
		claimController.delete(savedClaim);
		
		Claim deletedClaim = em.find(Claim.class, savedClaim.getClaimId());
		
		assertNull(deletedClaim);
	}
	
	private Claim createAndPersist() {
		Claim claim = new Claim();
		claim.setClaimId("ClaimId_" + UUID.randomUUID().toString().substring(0, 8));
		claim.setClaimAmount(10000L);
		claim.setClaimDate(LocalDate.now());
		claim.setExamDate(LocalDate.now().plusDays(1));
		claim.setStatus(Claim.ClaimStatus.NEW);
		
		createdEntities.add(claim);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(claim);
		em.getTransaction().commit();
		
		return claim;
	}
}