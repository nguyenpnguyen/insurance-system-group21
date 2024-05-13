package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.Beneficiary;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BeneficiaryControllerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private BeneficiaryController bController;
	private List<Beneficiary> createdEntities;
	
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
		bController = new BeneficiaryController(em);
	}
	
	@AfterEach
	void tearDown() {
		for (Beneficiary entity : createdEntities) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}
		createdEntities.clear();
		bController = null;
	}
	
	@Test
	void create() {
		Beneficiary b = new Beneficiary();
		b.setCustomerId("BeneficiaryId_" + UUID.randomUUID().toString().substring(0, 8));
		b.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		b.setHashedPassword("Password_" + UUID.randomUUID().toString().substring(0, 8));
		b.setFullName("FullName_" + UUID.randomUUID().toString().substring(0, 8));
		b.setAddress("Address_" + UUID.randomUUID().toString().substring(0, 8));
		b.setEmail("Email_" + UUID.randomUUID().toString().substring(0, 8));
		b.setPhoneNumber("PhoneNumber_" + UUID.randomUUID().toString().substring(0, 8));
		b.setIsPolicyHolder(true);
		
		bController.create(b);
		
		createdEntities.add(b);
		
		Beneficiary savedBeneficiary = em.find(Beneficiary.class, b.getCustomerId());
		
		assertNotNull(savedBeneficiary);
		assertEquals(b.getCustomerId(), savedBeneficiary.getCustomerId());
		assertEquals(b.getUsername(), savedBeneficiary.getUsername());
		assertEquals(b.getHashedPassword(), savedBeneficiary.getHashedPassword());
		assertEquals(b.getFullName(), savedBeneficiary.getFullName());
		assertEquals(b.getAddress(), savedBeneficiary.getAddress());
		assertEquals(b.getEmail(), savedBeneficiary.getEmail());
		assertEquals(b.getPhoneNumber(), savedBeneficiary.getPhoneNumber());
		assertEquals(b.isPolicyHolder(), savedBeneficiary.isPolicyHolder());
	}
	
	@Test
	void read() {
		Beneficiary savedBeneficiary = createAndPersistPolicyHolder();
		
		Optional<Beneficiary> optionalBeneficiary = bController.read(savedBeneficiary.getCustomerId());
		
		assertTrue(optionalBeneficiary.isPresent());
		
		Beneficiary retrievedBeneficiary = optionalBeneficiary.get();
		
		assertEquals(savedBeneficiary.getCustomerId(), retrievedBeneficiary.getCustomerId());
		assertEquals(savedBeneficiary.getUsername(), retrievedBeneficiary.getUsername());
		assertEquals(savedBeneficiary.getHashedPassword(), retrievedBeneficiary.getHashedPassword());
		assertEquals(savedBeneficiary.getFullName(), retrievedBeneficiary.getFullName());
		assertEquals(savedBeneficiary.getAddress(), retrievedBeneficiary.getAddress());
		assertEquals(savedBeneficiary.getEmail(), retrievedBeneficiary.getEmail());
		assertEquals(savedBeneficiary.getPhoneNumber(), retrievedBeneficiary.getPhoneNumber());
		assertEquals(savedBeneficiary.isPolicyHolder(), retrievedBeneficiary.isPolicyHolder());
	}
	
	@Test
	void readPolicyHolder() {
		Beneficiary savedPolicyHolder = createAndPersistPolicyHolder();
		
		Optional<Beneficiary> optionalPolicyHolder = bController.read(savedPolicyHolder.getCustomerId());
		
		assertTrue(optionalPolicyHolder.isPresent());
		
		Beneficiary retrievedPolicyHolder = optionalPolicyHolder.get();
		
		assertTrue(retrievedPolicyHolder.isPolicyHolder());
		assertEquals(savedPolicyHolder.getCustomerId(), retrievedPolicyHolder.getCustomerId());
		assertEquals(savedPolicyHolder.getUsername(), retrievedPolicyHolder.getUsername());
		assertEquals(savedPolicyHolder.getHashedPassword(), retrievedPolicyHolder.getHashedPassword());
		assertEquals(savedPolicyHolder.getFullName(), retrievedPolicyHolder.getFullName());
		assertEquals(savedPolicyHolder.getAddress(), retrievedPolicyHolder.getAddress());
		assertEquals(savedPolicyHolder.getEmail(), retrievedPolicyHolder.getEmail());
		assertEquals(savedPolicyHolder.getPhoneNumber(), retrievedPolicyHolder.getPhoneNumber());
		assertEquals(savedPolicyHolder.isPolicyHolder(), retrievedPolicyHolder.isPolicyHolder());
	}
	
	@Test
	void readDependent() {
		Beneficiary savedDependent = createAndPersistPolicyHolder();
		
		Optional<Beneficiary> optionalDependent = bController.read(savedDependent.getCustomerId());
		
		assertTrue(optionalDependent.isPresent());
		
		Beneficiary retrievedDependent = optionalDependent.get();
		
		assertFalse(retrievedDependent.isPolicyHolder());
		assertEquals(savedDependent.getCustomerId(), retrievedDependent.getCustomerId());
		assertEquals(savedDependent.getUsername(), retrievedDependent.getUsername());
		assertEquals(savedDependent.getHashedPassword(), retrievedDependent.getHashedPassword());
		assertEquals(savedDependent.getFullName(), retrievedDependent.getFullName());
		assertEquals(savedDependent.getAddress(), retrievedDependent.getAddress());
		assertEquals(savedDependent.getEmail(), retrievedDependent.getEmail());
		assertEquals(savedDependent.getPhoneNumber(), retrievedDependent.getPhoneNumber());
		assertEquals(savedDependent.isPolicyHolder(), retrievedDependent.isPolicyHolder());
	}
	
	@Test
	void readAll() {
		Beneficiary b1 = createAndPersistPolicyHolder();
		Beneficiary b2 = createAndPersistPolicyHolder();
		Beneficiary b3 = createAndPersistDependent();
		Beneficiary b4 = createAndPersistDependent();
		
		List<Beneficiary> bList = bController.readAll();
		
		int expectedSize = 4; // Initial expected size
		expectedSize += createdEntities.size(); // Add the count of entities created during the test
		
		assertFalse(bList.isEmpty());
		assertEquals(expectedSize, bList.size());
		
		for (Beneficiary b : List.of(b1, b2, b3, b4)) {
			assertTrue(bList.contains(b));
		}
	}
	
	@Test
	void readAllPolicyHolders() {
		Beneficiary ph1 = createAndPersistPolicyHolder();
		Beneficiary ph2 = createAndPersistPolicyHolder();
		Beneficiary ph3 = createAndPersistPolicyHolder();
		
		List<Beneficiary> phList = bController.readAllPolicyHolders();
		
		int expectedSize = 3; // Initial expected size
		expectedSize += createdEntities.size(); // Add the count of entities created during the test
		
		assertFalse(phList.isEmpty());
		assertEquals(expectedSize, phList.size());
		
		for (Beneficiary ph : List.of(ph1, ph2, ph3)) {
			assertTrue(phList.contains(ph));
		}
	}
	
	@Test
	void readAllDependents() {
		Beneficiary d1 = createAndPersistDependent();
		Beneficiary d2 = createAndPersistDependent();
		Beneficiary d3 = createAndPersistDependent();
		
		List<Beneficiary> dList = bController.readAllDependents();
		
		int expectedSize = 3; // Initial expected size
		expectedSize += createdEntities.size(); // Add the count of entities created during the test
		
		assertFalse(dList.isEmpty());
		assertEquals(expectedSize, dList.size());
		
		for (Beneficiary d : List.of(d1, d2, d3)) {
			assertTrue(dList.contains(d));
		}
	}
	
	
	@Test
	void update() {
		Beneficiary savedBeneficiary = createAndPersistPolicyHolder();
		
		savedBeneficiary.setUsername("UpdatedUsername_" + UUID.randomUUID().toString().substring(0, 8));
		savedBeneficiary.setHashedPassword("UpdatedPassword_" + UUID.randomUUID().toString().substring(0, 8));
		savedBeneficiary.setFullName("UpdatedFullName_" + UUID.randomUUID().toString().substring(0, 8));
		savedBeneficiary.setAddress("UpdatedAddress_" + UUID.randomUUID().toString().substring(0, 8));
		savedBeneficiary.setEmail("UpdatedEmail_" + UUID.randomUUID().toString().substring(0, 8));
		savedBeneficiary.setPhoneNumber("UpdatedPhoneNumber_" + UUID.randomUUID().toString().substring(0, 8));
		savedBeneficiary.setIsPolicyHolder(!savedBeneficiary.isPolicyHolder());
		
		bController.update(savedBeneficiary);
		
		Beneficiary updatedBeneficiary = em.find(Beneficiary.class, savedBeneficiary.getCustomerId());
		
		assertNotNull(updatedBeneficiary);
		assertEquals(savedBeneficiary.getCustomerId(), updatedBeneficiary.getCustomerId());
		assertEquals(savedBeneficiary.getUsername(), updatedBeneficiary.getUsername());
		assertEquals(savedBeneficiary.getHashedPassword(), updatedBeneficiary.getHashedPassword());
		assertEquals(savedBeneficiary.getFullName(), updatedBeneficiary.getFullName());
		assertEquals(savedBeneficiary.getAddress(), updatedBeneficiary.getAddress());
		assertEquals(savedBeneficiary.getEmail(), updatedBeneficiary.getEmail());
		assertEquals(savedBeneficiary.getPhoneNumber(), updatedBeneficiary.getPhoneNumber());
		assertEquals(savedBeneficiary.isPolicyHolder(), updatedBeneficiary.isPolicyHolder());
	}
	
	@Test
	void delete() {
		Beneficiary savedBeneficiary = createAndPersistPolicyHolder();
		
		bController.delete(savedBeneficiary);
		
		Beneficiary deletedBeneficiary = em.find(Beneficiary.class, savedBeneficiary.getCustomerId());
		
		assertNull(deletedBeneficiary);
	}
	
	private Beneficiary createAndPersistPolicyHolder() {
		Beneficiary ph = new Beneficiary();
		ph.setCustomerId("BeneficiaryId_" + UUID.randomUUID().toString().substring(0, 8));
		ph.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		ph.setHashedPassword("Password_" + UUID.randomUUID().toString().substring(0, 8));
		ph.setFullName("FullName_" + UUID.randomUUID().toString().substring(0, 8));
		ph.setAddress("Address_" + UUID.randomUUID().toString().substring(0, 8));
		ph.setEmail("Email_" + UUID.randomUUID().toString().substring(0, 8));
		ph.setPhoneNumber("PhoneNumber_" + UUID.randomUUID().toString().substring(0, 8));
		ph.setIsPolicyHolder(true);
		
		bController.create(ph);
		
		createdEntities.add(ph);
		
		return ph;
	}
	
	private Beneficiary createAndPersistDependent() {
		Beneficiary d = new Beneficiary();
		d.setCustomerId("BeneficiaryId_" + UUID.randomUUID().toString().substring(0, 8));
		d.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		d.setHashedPassword("Password_" + UUID.randomUUID().toString().substring(0, 8));
		d.setFullName("FullName_" + UUID.randomUUID().toString().substring(0, 8));
		d.setAddress("Address_" + UUID.randomUUID().toString().substring(0, 8));
		d.setEmail("Email_" + UUID.randomUUID().toString().substring(0, 8));
		d.setPhoneNumber("PhoneNumber_" + UUID.randomUUID().toString().substring(0, 8));
		d.setIsPolicyHolder(false);
		
		bController.create(d);
		
		createdEntities.add(d);
		
		return d;
	}
}