package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.models.InsuranceCard;
import org.group21.insurance.models.PolicyOwner;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InsuranceCardController implements GenericController<InsuranceCard> {
	private static InsuranceCardController instance = null;
	
	private InsuranceCardController() {
	}
	
	public static InsuranceCardController getInstance() {
		if (instance == null) {
			instance = new InsuranceCardController();
		}
		return instance;
	}
	
	@Override
	public Optional<InsuranceCard> read(String cardNumber) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(InsuranceCard.class, cardNumber));
		}
	}
	
	@Override
	public List<InsuranceCard> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceCard> cq = cb.createQuery(InsuranceCard.class);
			cq.from(InsuranceCard.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void create(InsuranceCard ic) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			
			// Check if the card holder (Beneficiary) already exists and merge if necessary
			if (ic.getCardHolder() != null && ic.getCardHolder().getCustomerId() != null) {
				Beneficiary existingBeneficiary = em.find(Beneficiary.class, ic.getCardHolder().getCustomerId());
				if (existingBeneficiary != null) {
					ic.setCardHolder(em.merge(ic.getCardHolder()));
				} else {
					em.persist(ic.getCardHolder());
				}
			}
			
			// Check if the policy owner already exists and merge if necessary
			if (ic.getPolicyOwner() != null && ic.getPolicyOwner().getCustomerId() != null) {
				PolicyOwner existingPolicyOwner = em.find(PolicyOwner.class, ic.getPolicyOwner().getCustomerId());
				if (existingPolicyOwner != null) {
					ic.setPolicyOwner(em.merge(ic.getPolicyOwner()));
				} else {
					em.persist(ic.getPolicyOwner());
				}
			}
			
			// Now persist the InsuranceCard
			em.persist(ic);
			
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw e; // Or handle more gracefully
		} finally {
			em.close();
		}
	}
	
	@Override
	public void update(InsuranceCard ic) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.merge(ic);
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void delete(InsuranceCard ic) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.remove(ic);
			em.getTransaction().commit();
		}
	}
}
