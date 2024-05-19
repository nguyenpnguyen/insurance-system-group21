package org.group21.insurance.controllers;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for Beneficiary entities.
 *
 * @author Group 21
 */
public class BeneficiaryController implements GenericController<Beneficiary>, UserController<Beneficiary> {
	// Singleton
	private static BeneficiaryController instance = null;
	
	private BeneficiaryController() {
	}
	
	/**
	 * Get the singleton instance of the BeneficiaryController.
	 *
	 * @return the instance of the BeneficiaryController
	 */
	public static BeneficiaryController getInstance() {
		if (instance == null) {
			instance = new BeneficiaryController();
		}
		return instance;
	}
	
	/**
	 * Read a Policy holder entity by its ID.
	 *
	 * @param policyHolderId the ID of the policy holder entity
	 *
	 * @return the Policy holder entity with the given ID
	 */
	public Optional<Beneficiary> readPolicyHolder(String policyHolderId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			Optional<Beneficiary> optionalPh = Optional.ofNullable(em.find(Beneficiary.class, policyHolderId));
			if (optionalPh.isPresent() && optionalPh.get().isPolicyHolder()) {
				return optionalPh;
			}
			return Optional.empty();
		}
	}
	
	/**
	 * Read a Dependent entity by its ID.
	 *
	 * @param dependentId the ID of the Dependent entity
	 *
	 * @return the Dependent entity with the given ID
	 */
	public Optional<Beneficiary> readDependent(String dependentId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			Optional<Beneficiary> optionalDependent = Optional.ofNullable(em.find(Beneficiary.class, dependentId));
			if (optionalDependent.isPresent() && !optionalDependent.get().isPolicyHolder()) {
				return optionalDependent;
			}
			return Optional.empty();
		}
	}
	
	/**
	 * Read all Policy holder entities.
	 *
	 * @return a list of all Policy holder entities
	 */
	public List<Beneficiary> readAllPolicyHolders() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> root = cq.from(Beneficiary.class);
			cq.select(root).where(cb.equal(root.get("isPolicyHolder"), true));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Read all Dependent entities.
	 *
	 * @return a list of all Dependent entities
	 */
	public List<Beneficiary> readAllDependents() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> root = cq.from(Beneficiary.class);
			cq.select(root).where(cb.equal(root.get("isPolicyHolder"), false));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Read a Beneficiary entity based on its ID.
	 *
	 * @return an optional of the Beneficiary entity
	 */
	@Override
	public Optional<Beneficiary> read(String beneficiaryId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(Beneficiary.class, beneficiaryId));
		}
	}
	
	/**
	 * Read all Beneficiary entities.
	 *
	 * @return a list of all Beneficiary entities
	 */
	@Override
	public List<Beneficiary> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			cq.from(Beneficiary.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Create a new Beneficiary entity.
	 *
	 * @param b the Beneficiary entity to create
	 */
	@Override
	public void create(Beneficiary b) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(b)) {
				b = em.merge(b);
			}
			
			em.persist(b);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Update a Beneficiary entity.
	 *
	 * @param b the Beneficiary entity to update
	 */
	@Override
	public void update(Beneficiary b) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			if (!em.contains(b)) {
				em.merge(b);
			}
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete a Beneficiary entity.
	 *
	 * @param b the Beneficiary entity to delete
	 */
	@Override
	public void delete(Beneficiary b) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(b)) {
				b = em.merge(b);
			}
			
			em.remove(b);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Find a Beneficiary entity by its username.
	 *
	 * @param username the username of the Beneficiary entity
	 *
	 * @return an optional of the Beneficiary entity
	 */
	@Override
	public Optional<Beneficiary> findByUsername(String username) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> beneficiaryRoot = cq.from(Beneficiary.class);
			cq.select(beneficiaryRoot).where(cb.equal(beneficiaryRoot.get("username"), username));
			return Optional.ofNullable(em.createQuery(cq).getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Delete all Beneficiary entities.
	 */
	@Override
	public void deleteAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.createQuery("DELETE FROM Beneficiary").executeUpdate();
			em.getTransaction().commit();
		}
	}
}
