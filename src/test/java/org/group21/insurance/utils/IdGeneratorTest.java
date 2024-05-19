package org.group21.insurance.utils;

/**
 * @author Group 21
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.group21.insurance.authentication.PasswordAuthenticator;
import org.group21.insurance.models.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IdGeneratorTest {
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static final String CUSTOMER_PREFIX = "c";
	private static final String POLICY_OWNER_IDENTIFIER = "P";
	private static final String BENEFICIARY_IDENTIFIER = "B";
	private static final String INSURANCE_PROVIDER_PREFIX = "p";
	private static final String INSURANCE_MANAGER_IDENTIFIER = "I";
	private static final String INSURANCE_CARD_PREFIX = "ic";
	private static final String INSURANCE_CARD_IDENTIFIER = "I";
	private static final String CLAIM_PREFIX = "f";
	private static final String CLAIM_IDENTIFIER = "C";
	private static final int CUSTOMER_LENGTH = 7;
	private static final int INSURANCE_PROVIDER_LENGTH = 7;
	private static final int INSURANCE_CARD_LENGTH = 10;
	private static final int CLAIM_LENGTH = 10;
	
	@BeforeAll
	static void setUpAll() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	@AfterAll
	static void tearDownAll() {
		emf.close();
	}
	
	@BeforeEach
	void setUp() {
		em = emf.createEntityManager();
	}
	
	@AfterEach
	void tearDown() {
		em.close();
	}
	
	@Test
	void generateCustomerId() {
		// Create a policy owner
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		em.getTransaction().begin();
		
		PolicyOwner po = new PolicyOwner();
		po.setUsername("poUsername");
		po.setHashedPassword(passwordAuthenticator.hash("PoTestPassword".toCharArray()));
		
		em.persist(po);
		em.getTransaction().commit();
		
		// Create a beneficiary
		em.getTransaction().begin();
		
		Beneficiary beneficiary = new Beneficiary();
		beneficiary.setUsername("bUsername");
		beneficiary.setHashedPassword(passwordAuthenticator.hash("BTestPassword".toCharArray()));
		em.persist(beneficiary);
		em.getTransaction().commit();
		
		// Check if the generated customer ID is correct
		assertNotNull(po.getCustomerId());
		assertEquals(po.getCustomerId().length() - 3, CUSTOMER_LENGTH);
		assertEquals(po.getCustomerId().charAt(0), CUSTOMER_PREFIX.charAt(0));
		assertEquals(po.getCustomerId().charAt(2), POLICY_OWNER_IDENTIFIER.charAt(0));
		
		assertNotNull(beneficiary.getCustomerId());
		assertEquals(beneficiary.getCustomerId().length() - 3, CUSTOMER_LENGTH);
		assertEquals(beneficiary.getCustomerId().charAt(0), CUSTOMER_PREFIX.charAt(0));
		assertEquals(beneficiary.getCustomerId().charAt(2), BENEFICIARY_IDENTIFIER.charAt(0));
		
		// Clean up
		em.getTransaction().begin();
		em.remove(po);
		em.remove(beneficiary);
		em.getTransaction().commit();
	}
	
	@Test
	void generateInsuranceProviderId() {
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		
		em.getTransaction().begin();
		
		InsuranceProvider iManager = new InsuranceProvider();
		iManager.setUsername("iManagerTestUsername");
		iManager.setHashedPassword(passwordAuthenticator.hash("ImTestPassword".toCharArray()));
		iManager.setInsuranceManager(true);
		
		em.persist(iManager);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		
		InsuranceProvider iSurveyor = new InsuranceProvider();
		iSurveyor.setUsername("iSurveyorTestUsername");
		iSurveyor.setHashedPassword(passwordAuthenticator.hash("IsTestPassword".toCharArray()));
		iSurveyor.setInsuranceManager(false);
		
		em.persist(iSurveyor);
		em.getTransaction().commit();
		
		assertNotNull(iManager.getInsuranceProviderId());
		assertEquals(iManager.getInsuranceProviderId().length() - 3, INSURANCE_PROVIDER_LENGTH);
		assertEquals(iManager.getInsuranceProviderId().charAt(0), INSURANCE_PROVIDER_PREFIX.charAt(0));
		assertEquals(iManager.getInsuranceProviderId().charAt(2), INSURANCE_MANAGER_IDENTIFIER.charAt(0));
		
		assertNotNull(iSurveyor.getInsuranceProviderId());
		assertEquals(iSurveyor.getInsuranceProviderId().length() - 3, INSURANCE_PROVIDER_LENGTH);
		assertEquals(iSurveyor.getInsuranceProviderId().charAt(0), INSURANCE_PROVIDER_PREFIX.charAt(0));
		assertEquals(iSurveyor.getInsuranceProviderId().charAt(2), INSURANCE_MANAGER_IDENTIFIER.charAt(0));
		
		em.getTransaction().begin();
		em.remove(iManager);
		em.remove(iSurveyor);
		em.getTransaction().commit();
	}
	
	@Test
	void generateCardNumber() {
		PasswordAuthenticator passwordAuthenticator = new PasswordAuthenticator();
		em.getTransaction().begin();
		
		Beneficiary b = new Beneficiary();
		b.setUsername("bUsername");
		b.setHashedPassword(passwordAuthenticator.hash("BTestPassword".toCharArray()));
		
		em.persist(b);
		em.getTransaction().commit();
		
		em.getTransaction().begin();
		InsuranceCard ic = new InsuranceCard();
		ic.setCardHolder(b);
		
		em.persist(ic);
		em.getTransaction().commit();
		
		assertNotNull(ic.getCardNumber());
		assertEquals(ic.getCardNumber().length() - 3, INSURANCE_CARD_LENGTH);
		assertEquals(ic.getCardNumber().substring(0, 2), INSURANCE_CARD_PREFIX);
		assertEquals(ic.getCardNumber().charAt(3), INSURANCE_CARD_IDENTIFIER.charAt(0));
		
		em.getTransaction().begin();
		em.remove(ic);
		em.remove(b);
		em.getTransaction().commit();
	}
	
	@Test
	void generateClaimId() {
		em.getTransaction().begin();
		
		Claim c = new Claim();
		c.setStatus(Claim.ClaimStatus.NEW);
		
		em.persist(c);
		em.getTransaction().commit();
		
		assertNotNull(c.getClaimId());
		assertEquals(c.getClaimId().length() - 3, CLAIM_LENGTH);
		assertEquals(c.getClaimId().charAt(0), CLAIM_PREFIX.charAt(0));
		assertEquals(c.getClaimId().charAt(2), CLAIM_IDENTIFIER.charAt(0));
		
		em.getTransaction().begin();
		em.remove(c);
		em.getTransaction().commit();
	}
}