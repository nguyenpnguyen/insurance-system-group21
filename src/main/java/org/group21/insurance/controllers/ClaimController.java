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

public class ClaimController implements GenericController<Claim> {
	private static ClaimController instance = null;
	
	private ClaimController() {
	}
	
	public static ClaimController getInstance() {
		if (instance == null) {
			instance = new ClaimController();
		}
		return instance;
	}
	
	@Override
	public Optional<Claim> read(String claimId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(Claim.class, claimId));
		}
	}
	
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
