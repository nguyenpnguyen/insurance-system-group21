package org.group21.insurance.controllers;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.Claim;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for Claim entities.
 *
 * @author Group 21
 */
public class ClaimController implements GenericController<Claim> {
	// Singleton
	private static ClaimController instance = null;
	
	private ClaimController() {
	}
	
	/**
	 * Get the singleton instance of the ClaimController.
	 *
	 * @return the instance of the ClaimController
	 */
	public static ClaimController getInstance() {
		if (instance == null) {
			instance = new ClaimController();
		}
		return instance;
	}
	
	/**
	 * Read a Claim entity by its ID.
	 *
	 * @param claimId the ID of the Claim entity
	 *
	 * @return the Claim entity with the given ID
	 */
	@Override
	public Optional<Claim> read(String claimId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(Claim.class, claimId));
		}
	}
	
	/**
	 * Read all Claim entities.
	 *
	 * @return a list of all Claim entities
	 */
	@Override
	public List<Claim> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Claim> cq = cb.createQuery(Claim.class);
			cq.from(Claim.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Create a new Claim entity.
	 *
	 * @param c the Claim entity to create
	 */
	@Override
	public void create(Claim c) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(c)) {
				c = em.merge(c);
			}
			
			em.persist(c);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Update a Claim entity.
	 *
	 * @param c the Claim entity to update
	 */
	@Override
	public void update(Claim c) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			if (!em.contains(c)) {
				em.merge(c);
			}
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete a Claim entity.
	 *
	 * @param c the Claim entity to delete
	 */
	@Override
	public void delete(Claim c) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(c)) {
				c = em.merge(c);
			}
			
			em.remove(c);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Get all Claim entities with a specific status.
	 *
	 * @param status the status of the Claim entities
	 *
	 * @return a list of all Claim entities with the given status
	 */
	public List<Claim> getClaimByStatus(Claim.ClaimStatus status) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Claim> cq = cb.createQuery(Claim.class);
			Root<Claim> claim = cq.from(Claim.class);
			cq.select(claim).where(cb.equal(claim.get("status"), status));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Delete all Claim entities.
	 */
	@Override
	public void deleteAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.createQuery("DELETE FROM Claim").executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Read all Claim entities by the ID of the insured person.
	 *
	 * @param userId the ID of the insured person
	 *
	 * @return a list of all Claim entities with the given insured person ID
	 */
	public List<Claim> readAllByInsuredPerson(String userId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Claim> cq = cb.createQuery(Claim.class);
			Root<Claim> claim = cq.from(Claim.class);
			cq.select(claim).where(cb.equal(claim.get("insuredPerson").get("id"), userId));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}
