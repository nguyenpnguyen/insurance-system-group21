package org.group21.insurance.controllers;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.models.InsuranceCard;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for InsuranceCard entities.
 *
 * @author Group 21
 */
public class InsuranceCardController implements GenericController<InsuranceCard> {
	// Singleton
	private static InsuranceCardController instance = null;
	
	private InsuranceCardController() {
	}
	
	/**
	 * Get the singleton instance of the InsuranceCardController.
	 *
	 * @return the instance of the InsuranceCardController
	 */
	public static InsuranceCardController getInstance() {
		if (instance == null) {
			instance = new InsuranceCardController();
		}
		return instance;
	}
	
	/**
	 * Read an InsuranceCard entity by its card number.
	 *
	 * @param cardNumber the card number of the InsuranceCard entity
	 *
	 * @return the InsuranceCard entity with the given card number
	 */
	@Override
	public Optional<InsuranceCard> read(String cardNumber) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(InsuranceCard.class, cardNumber));
		}
	}
	
	/**
	 * Read all InsuranceCard entities.
	 *
	 * @return a list of all InsuranceCard entities
	 */
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
	
	/**
	 * Create a new InsuranceCard entity.
	 *
	 * @param ic the InsuranceCard entity to create
	 */
	@Override
	public void create(InsuranceCard ic) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(ic)) {
				ic = em.merge(ic);
			}
			
			if (ic.getCardHolder() != null) {
				Beneficiary existingBeneficiary = em.find(Beneficiary.class, ic.getCardHolder().getCustomerId());
				if (existingBeneficiary != null) {
					ic.setCardHolder(em.merge(ic.getCardHolder()));
				} else {
					em.persist(ic.getCardHolder());
				}
			}
			
			// Now persist the InsuranceCard
			em.persist(ic);
			
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Update an existing InsuranceCard entity.
	 *
	 * @param ic the InsuranceCard entity to update
	 */
	@Override
	public void update(InsuranceCard ic) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(ic)) {
				em.merge(ic);
			}
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete an existing InsuranceCard entity.
	 *
	 * @param ic the InsuranceCard entity to delete
	 */
	@Override
	public void delete(InsuranceCard ic) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(ic)) {
				ic = em.merge(ic);
			}
			
			em.remove(ic);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Find an InsuranceCard entity by its card holder.
	 *
	 * @param cardHolder the card holder of the InsuranceCard entity
	 *
	 * @return the InsuranceCard entity with the given card holder
	 */
	public InsuranceCard findCardByCardHolder(Beneficiary cardHolder) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceCard> cq = cb.createQuery(InsuranceCard.class);
			cq.from(InsuranceCard.class);
			List<InsuranceCard> insuranceCards = em.createQuery(cq).getResultList();
			
			for (InsuranceCard ic : insuranceCards) {
				if (ic.getCardHolder().equals(cardHolder)) {
					return ic;
				}
			}
			return null;
		}
	}
	
	/**
	 * Delete all InsuranceCard entities.
	 */
	@Override
	public void deleteAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.createQuery("DELETE FROM InsuranceCard").executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Find an InsuranceCard entity by its card holder.
	 *
	 * @param b the card holder of the InsuranceCard entity
	 *
	 * @return the InsuranceCard entity with the given card holder
	 */
	public InsuranceCard getCardByCardHolder(Beneficiary b) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceCard> cq = cb.createQuery(InsuranceCard.class);
			cq.from(InsuranceCard.class);
			List<InsuranceCard> insuranceCards = em.createQuery(cq).getResultList();
			
			for (InsuranceCard ic : insuranceCards) {
				if (ic.getCardHolder().equals(b)) {
					return ic;
				}
			}
			return null;
		}
	}
}
