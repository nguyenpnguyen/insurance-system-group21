package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.InsuranceProvider;
import org.group21.insurance.utils.PasswordAuthenticator;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InsuranceProviderControllerTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private InsuranceProviderController ipController;
	private List<InsuranceProvider> createdEntities;
	
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
		ipController = new InsuranceProviderController(em);
		createdEntities = new ArrayList<>();
	}
	
	@AfterEach
	void tearDown() {
		for (InsuranceProvider entity : createdEntities) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		}
		createdEntities.clear();
		ipController = null;
	}
	
	@Test
	void create() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		InsuranceProvider ip = new InsuranceProvider();
		ip.setInsuranceProviderId("ID_" + UUID.randomUUID().toString().substring(0, 8));
		ip.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		ip.setHashedPassword(pAuthenticator.hash(testPassword));
		ip.setInsuranceManager(true);
		
		ipController.create(ip);
		
		createdEntities.add(ip);
		
		InsuranceProvider savedIp = em.find(InsuranceProvider.class, ip.getInsuranceProviderId());
		
		assertNotNull(savedIp);
		assertEquals(ip.getInsuranceProviderId(), savedIp.getInsuranceProviderId());
		assertEquals(ip.getUsername(), savedIp.getUsername());
		assertEquals(ip.getHashedPassword(), savedIp.getHashedPassword());
		assertEquals(ip.isInsuranceManager(), savedIp.isInsuranceManager());
	}
	
	@Test
	void read() {
		InsuranceProvider savedIp = createAndPersistIManager();
		
		Optional<InsuranceProvider> optionalIp = ipController.read(savedIp.getInsuranceProviderId());
		
		assertTrue(optionalIp.isPresent());
		
		InsuranceProvider retrievedIp = optionalIp.get();
		
		assertEquals(savedIp.getInsuranceProviderId(), retrievedIp.getInsuranceProviderId());
		assertEquals(savedIp.getUsername(), retrievedIp.getUsername());
		assertEquals(savedIp.getHashedPassword(), retrievedIp.getHashedPassword());
		assertEquals(savedIp.isInsuranceManager(), retrievedIp.isInsuranceManager());
	}
	
	@Test
	void readInsuranceManager() {
		InsuranceProvider savedIm = createAndPersistIManager();
		
		Optional<InsuranceProvider> optionalIm = ipController.readInsuranceManager(savedIm.getInsuranceProviderId());
		
		assertTrue(optionalIm.isPresent());
		
		InsuranceProvider retrievedIm = optionalIm.get();
		
		assertTrue(retrievedIm.isInsuranceManager());
		assertEquals(savedIm.getInsuranceProviderId(), retrievedIm.getInsuranceProviderId());
		assertEquals(savedIm.getUsername(), retrievedIm.getUsername());
		assertEquals(savedIm.getHashedPassword(), retrievedIm.getHashedPassword());
		assertEquals(savedIm.isInsuranceManager(), retrievedIm.isInsuranceManager());
	}
	
	@Test
	void readInsuranceSurveyor() {
		InsuranceProvider savedIs = createAndPersistISurveyor();
		
		Optional<InsuranceProvider> optionalIs = ipController.readInsuranceSurveyor(savedIs.getInsuranceProviderId());
		
		assertTrue(optionalIs.isPresent());
		assertFalse(optionalIs.get().isInsuranceManager());
		
		InsuranceProvider retrievedIs = optionalIs.get();
		
		assertEquals(savedIs.getInsuranceProviderId(), retrievedIs.getInsuranceProviderId());
		assertEquals(savedIs.getUsername(), retrievedIs.getUsername());
		assertEquals(savedIs.getHashedPassword(), retrievedIs.getHashedPassword());
		assertEquals(savedIs.isInsuranceManager(), retrievedIs.isInsuranceManager());
		assertFalse(retrievedIs.isInsuranceManager());
	}
	
	@Test
	void readAll() {
		InsuranceProvider ip1 = createAndPersistIManager();
		InsuranceProvider ip2 = createAndPersistIManager();
		InsuranceProvider ip3 = createAndPersistISurveyor();
		InsuranceProvider ip4 = createAndPersistISurveyor();
		
		List<InsuranceProvider> insuranceProviderList = ipController.readAll();
		
		int expectedSize = 4;
		expectedSize += createdEntities.size();
		
		assertFalse(insuranceProviderList.isEmpty());
		assertEquals(expectedSize, insuranceProviderList.size());
		
		for (InsuranceProvider ip : List.of(ip1, ip2, ip3, ip4)) {
			assertTrue(insuranceProviderList.contains(ip));
		}
	}
	
	@Test
	void readAllInsuranceManagers() {
		InsuranceProvider im1 = createAndPersistIManager();
		InsuranceProvider im2 = createAndPersistIManager();
		InsuranceProvider im3 = createAndPersistIManager();
		
		List<InsuranceProvider> insuranceManagerList = ipController.readAllInsuranceManagers();
		
		int expectedSize = 3;
		expectedSize += createdEntities.size();
		
		assertFalse(insuranceManagerList.isEmpty());
		assertEquals(expectedSize, insuranceManagerList.size());
		
		for (InsuranceProvider im : List.of(im1, im2, im3)) {
			assertTrue(insuranceManagerList.contains(im));
			assertTrue(im.isInsuranceManager());
		}
	}
	
	@Test
	void readAllInsuranceSurveyors() {
		InsuranceProvider is1 = createAndPersistISurveyor();
		InsuranceProvider is2 = createAndPersistISurveyor();
		InsuranceProvider is3 = createAndPersistISurveyor();
		
		List<InsuranceProvider> insuranceSurveyorList = ipController.readAllInsuranceSurveyors();
		
		int expectedSize = 3;
		expectedSize += createdEntities.size();
		
		assertFalse(insuranceSurveyorList.isEmpty());
		assertEquals(expectedSize, insuranceSurveyorList.size());
		
		for (InsuranceProvider is : List.of(is1, is2, is3)) {
			assertTrue(insuranceSurveyorList.contains(is));
			assertFalse(is.isInsuranceManager());
		}
	}
	
	@Test
	void update() {
		InsuranceProvider savedIp = createAndPersistIManager();
		
		savedIp.setUsername("NewUsername_" + UUID.randomUUID().toString().substring(0, 8));
		savedIp.setHashedPassword("NewPassword_" + UUID.randomUUID().toString().substring(0, 8));
		savedIp.setInsuranceManager(!savedIp.isInsuranceManager());
		
		ipController.update(savedIp);
		
		InsuranceProvider updatedIp = em.find(InsuranceProvider.class, savedIp.getInsuranceProviderId());
		
		assertEquals(savedIp.getInsuranceProviderId(), updatedIp.getInsuranceProviderId());
		assertEquals(savedIp.getUsername(), updatedIp.getUsername());
		assertEquals(savedIp.getHashedPassword(), updatedIp.getHashedPassword());
		assertEquals(savedIp.isInsuranceManager(), updatedIp.isInsuranceManager());
		assertEquals(savedIp.getEmail(), updatedIp.getEmail());
		assertEquals(savedIp.getPhoneNumber(), updatedIp.getPhoneNumber());
		assertEquals(savedIp.getFullName(), updatedIp.getFullName());
	}
	
	@Test
	void delete() {
		InsuranceProvider savedIp = createAndPersistIManager();
		
		ipController.delete(savedIp);
		
		InsuranceProvider deletedIp = em.find(InsuranceProvider.class, savedIp.getInsuranceProviderId());
		
		assertNull(deletedIp);
	}
	
	private InsuranceProvider createAndPersistIManager() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		InsuranceProvider ip = new InsuranceProvider();
		// Set unique fields
		ip.setInsuranceProviderId("ID_" + UUID.randomUUID().toString().substring(0, 8));
		ip.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		ip.setHashedPassword(pAuthenticator.hash(testPassword));
		ip.setInsuranceManager(true);
		
		createdEntities.add(ip);
		
		em.getTransaction().begin();
		em.persist(ip);
		em.getTransaction().commit();
		return ip;
	}
	
	private InsuranceProvider createAndPersistISurveyor() {
		PasswordAuthenticator pAuthenticator = new PasswordAuthenticator();
		char[] testPassword = ("Password_" + UUID.randomUUID().toString().substring(0, 8)).toCharArray();
		
		InsuranceProvider ip = new InsuranceProvider();
		// Set unique fields
		ip.setInsuranceProviderId("ID_" + UUID.randomUUID().toString().substring(0, 8));
		ip.setUsername("Username_" + UUID.randomUUID().toString().substring(0, 8));
		ip.setHashedPassword(pAuthenticator.hash(testPassword));
		ip.setInsuranceManager(false);
		
		createdEntities.add(ip);
		
		em.getTransaction().begin();
		em.persist(ip);
		em.getTransaction().commit();
		return ip;
	}
}