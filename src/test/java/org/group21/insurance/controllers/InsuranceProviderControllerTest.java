package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.models.InsuranceProvider;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class InsuranceProviderControllerTest implements ControllerTest<InsuranceProvider> {
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
	public void create() {
		// TODO: Implement
		InsuranceProvider ip = new InsuranceProvider();
		ipController.create(ip);
	}
	
	@Test
	public void read() {
	}
	
	@Test
	public void readInsuranceManager() {
	}
	
	@Test
	public void readInsuranceSurveyor() {
	}
	
	@Test
	public void readAll() {
	}
	
	@Test
	public void readAllInsuranceManagers() {
	}
	
	@Test
	public void readAllInsuranceSurveyors() {
	}
	
	@Test
	public void update() {
	}
	
	@Test
	public void delete() {
	}
	
	@Override
	public InsuranceProvider createAndPersist() {
		return null;
	}
}