package org.group21.insurance.controllers;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.BankingInfo;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for BankingInfo entities.
 *
 * @author Group 21
 */
public class BankingInfoController implements GenericController<BankingInfo> {
	// Singleton
	private static BankingInfoController instance = null;
	
	private BankingInfoController() {
	}
	
	/**
	 * Get the singleton instance of the BankingInfoController.
	 *
	 * @return the instance of the BankingInfoController
	 */
	public static BankingInfoController getInstance() {
		if (instance == null) {
			instance = new BankingInfoController();
		}
		return instance;
	}
	
	/**
	 * Read a BankingInfo entity by its account number.
	 *
	 * @param accountNumber the account number of the BankingInfo entity
	 *
	 * @return the BankingInfo entity with the given account number
	 */
	@Override
	public Optional<BankingInfo> read(String accountNumber) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(BankingInfo.class, accountNumber));
		}
	}
	
	/**
	 * Read all BankingInfo entities.
	 *
	 * @return a list of all BankingInfo entities
	 */
	@Override
	public List<BankingInfo> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<BankingInfo> cq = cb.createQuery(BankingInfo.class);
			cq.from(BankingInfo.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Create a new BankingInfo entity.
	 *
	 * @param bi the BankingInfo entity to create
	 */
	@Override
	public void create(BankingInfo bi) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(bi)) {
				bi = em.merge(bi);
			}
			em.persist(bi);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Update a BankingInfo entity.
	 *
	 * @param bi the BankingInfo entity to update
	 */
	@Override
	public void update(BankingInfo bi) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			if (!em.contains(bi)) {
				em.merge(bi);
			}
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete a BankingInfo entity.
	 *
	 * @param bi the BankingInfo entity to delete
	 */
	@Override
	public void delete(BankingInfo bi) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(bi)) {
				bi = em.merge(bi);
			}
			
			em.remove(bi);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete all BankingInfo entities.
	 */
	@Override
	public void deleteAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.createQuery("DELETE FROM BankingInfo").executeUpdate();
			em.getTransaction().commit();
		}
	}
}
