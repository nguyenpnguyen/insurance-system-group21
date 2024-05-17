package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.InsuranceCard;
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
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.persist(ic);
			em.getTransaction().commit();
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
