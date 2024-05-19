package org.group21.insurance.controllers;

/**
 * @author Group 21
 */

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

public class BankingInfoController implements GenericController<BankingInfo> {
	private static BankingInfoController instance = null;
	
	private BankingInfoController() {
	}
	
	public static BankingInfoController getInstance() {
		if (instance == null) {
			instance = new BankingInfoController();
		}
		return instance;
	}
	
	@Override
	public Optional<BankingInfo> read(String accountNumber) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(BankingInfo.class, accountNumber));
		}
	}
	
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
