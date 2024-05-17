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

public class PolicyOwnerController implements GenericController<PolicyOwner>, UserController<PolicyOwner> {
	private static PolicyOwnerController instance = null;
	
	private PolicyOwnerController() {
	}
	
	public static PolicyOwnerController getInstance() {
		if (instance == null) {
			instance = new PolicyOwnerController();
		}
		return instance;
	}
	
	
	@Override
	public Optional<PolicyOwner> read(String policyOwnerId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(PolicyOwner.class, policyOwnerId));
		}
	}
	
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
