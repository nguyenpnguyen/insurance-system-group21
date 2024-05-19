package org.group21.insurance.controllers;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.InsuranceProvider;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for InsuranceProvider entities.
 *
 * @author Group 21
 */
public class InsuranceProviderController implements GenericController<InsuranceProvider>, UserController<InsuranceProvider> {
	// Singleton
	private static InsuranceProviderController instance = null;
	
	private InsuranceProviderController() {
	}
	
	/**
	 * Get the singleton instance of the InsuranceProviderController.
	 *
	 * @return the instance of the InsuranceProviderController
	 */
	public static InsuranceProviderController getInstance() {
		if (instance == null) {
			instance = new InsuranceProviderController();
		}
		return instance;
	}
	
	/**
	 * Read an Insurance manager entity by its ID.
	 *
	 * @param insuranceManagerId the ID of the Insurance manager entity
	 *
	 * @return the Insurance manager entity with the given ID
	 */
	public Optional<InsuranceProvider> readInsuranceManager(String insuranceManagerId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			Optional<InsuranceProvider> optionalIm = Optional.ofNullable(em.find(InsuranceProvider.class, insuranceManagerId));
			if (optionalIm.isPresent() && optionalIm.get().isInsuranceManager()) {
				return optionalIm;
			}
			return Optional.empty();
		}
	}
	
	/**
	 * Read an Insurance surveyor entity by its ID.
	 *
	 * @param insuranceSurveyorId the ID of the Insurance surveyor entity
	 *
	 * @return the Insurance surveyor entity with the given ID
	 */
	public Optional<InsuranceProvider> readInsuranceSurveyor(String insuranceSurveyorId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			Optional<InsuranceProvider> optionalIs = Optional.ofNullable(em.find(InsuranceProvider.class, insuranceSurveyorId));
			if (optionalIs.isPresent() && !optionalIs.get().isInsuranceManager()) {
				return optionalIs;
			}
			return Optional.empty();
		}
	}
	
	/**
	 * Read all Insurance manager entities.
	 *
	 * @return a list of all Insurance manager entities
	 */
	public List<InsuranceProvider> readAllInsuranceManagers() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			Root<InsuranceProvider> root = cq.from(InsuranceProvider.class);
			cq.select(root).where(cb.equal(root.get("isInsuranceManager"), true));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Read all Insurance surveyor entities.
	 *
	 * @return a list of all Insurance surveyor entities
	 */
	public List<InsuranceProvider> readAllInsuranceSurveyors() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			Root<InsuranceProvider> root = cq.from(InsuranceProvider.class);
			cq.select(root).where(cb.equal(root.get("isInsuranceManager"), false));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Read a InsuranceProvider entity with given ID .
	 *
	 * @param insuranceProviderId the ID of the InsuranceProvider entity
	 *
	 * @return an optional of InsuranceProvider entity
	 */
	@Override
	public Optional<InsuranceProvider> read(String insuranceProviderId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(InsuranceProvider.class, insuranceProviderId));
		}
	}
	
	/**
	 * Read all InsuranceProvider entities.
	 *
	 * @return a list of all InsuranceProvider entities
	 */
	@Override
	public List<InsuranceProvider> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			cq.from(InsuranceProvider.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Create a new InsuranceProvider entity.
	 *
	 * @param ip the InsuranceProvider entity to create
	 */
	@Override
	public void create(InsuranceProvider ip) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			if (!em.contains(ip)) {
				ip = em.merge(ip);
			}
			em.persist(ip);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Update an existing InsuranceProvider entity.
	 *
	 * @param ip the InsuranceProvider entity to update
	 */
	@Override
	public void update(InsuranceProvider ip) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			if (!em.contains(ip)) {
				em.merge(ip);
			}
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Delete an existing InsuranceProvider entity.
	 *
	 * @param ip the InsuranceProvider entity to delete
	 */
	@Override
	public void delete(InsuranceProvider ip) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			if (!em.contains(ip)) {
				ip = em.merge(ip);
			}
			em.remove(ip);
			em.getTransaction().commit();
		}
	}
	
	/**
	 * Find an InsuranceProvider entity by its username.
	 *
	 * @param username the username of the InsuranceProvider entity
	 *
	 * @return an optional of InsuranceProvider entity
	 */
	@Override
	public Optional<InsuranceProvider> findByUsername(String username) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			Root<InsuranceProvider> insuranceProviderRoot = cq.from(InsuranceProvider.class);
			cq.select(insuranceProviderRoot).where(cb.equal(insuranceProviderRoot.get("username"), username));
			return Optional.ofNullable(em.createQuery(cq).getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
	
	/**
	 * Delete all InsuranceProvider entities.
	 */
	@Override
	public void deleteAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.createQuery("DELETE FROM InsuranceProvider").executeUpdate();
			em.getTransaction().commit();
		}
	}
}
