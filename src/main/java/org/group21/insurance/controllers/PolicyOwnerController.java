package org.group21.insurance.controllers;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.PolicyOwner;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for PolicyOwner entities.
 *
 * @author Group 21
 */

public class PolicyOwnerController implements GenericController<PolicyOwner>, UserController<PolicyOwner> {
	// Singleton
	private static PolicyOwnerController instance = null;
	
	private PolicyOwnerController() {
	}
	
	/**
	 * Get the singleton instance of the PolicyOwnerController.
	 *
	 * @return the instance of the PolicyOwnerController
	 */
	public static PolicyOwnerController getInstance() {
		if (instance == null) {
			instance = new PolicyOwnerController();
		}
		return instance;
	}
	
	
	/**
	 * Read a PolicyOwner entity by its ID.
	 *
	 * @param policyOwnerId the ID of the PolicyOwner entity
	 *
	 * @return the PolicyOwner entity with the given ID
	 */
	@Override
	public Optional<PolicyOwner> read(String policyOwnerId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(PolicyOwner.class, policyOwnerId));
		}
	}
	
	/**
	 * Read all PolicyOwner entities.
	 *
	 * @return a list of all PolicyOwner entities
	 */
	@Override
	public List<PolicyOwner> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PolicyOwner> cq = cb.createQuery(PolicyOwner.class);
			cq.from(PolicyOwner.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Create a new PolicyOwner entity.
	 *
	 * @param po the PolicyOwner entity to create
	 */
	@Override
	public void create(PolicyOwner po) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.persist(po);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Update a PolicyOwner entity.
	 *
	 * @param po the PolicyOwner entity to update
	 */
	@Override
	public void update(PolicyOwner po) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.merge(po);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete a PolicyOwner entity.
	 *
	 * @param po the PolicyOwner entity to delete
	 */
	@Override
	public void delete(PolicyOwner po) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.remove(po);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete all PolicyOwner entities.
	 */
	@Override
	public void deleteAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.createQuery("DELETE FROM PolicyOwner").executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Find a PolicyOwner entity by its username.
	 *
	 * @param username the username of the PolicyOwner entity
	 *
	 * @return the PolicyOwner entity with the given username
	 */
	@Override
	public Optional<PolicyOwner> findByUsername(String username) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<PolicyOwner> cq = cb.createQuery(PolicyOwner.class);
			Root<PolicyOwner> policyOwner = cq.from(PolicyOwner.class);
			cq.select(policyOwner).where(cb.equal(policyOwner.get("username"), username));
			return Optional.ofNullable(em.createQuery(cq).getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
