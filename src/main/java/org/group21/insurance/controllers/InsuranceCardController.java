package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.InsuranceCard;

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
	public Optional<InsuranceCard> read(EntityManager em, String cardNumber) {
		return Optional.ofNullable(em.find(InsuranceCard.class, cardNumber));
	}
	
	@Override
	public List<InsuranceCard> readAll(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceCard> cq = cb.createQuery(InsuranceCard.class);
			cq.from(InsuranceCard.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void create(EntityManager em, InsuranceCard ic) {
		if (!em.contains(ic)) {
			ic = em.merge(ic);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(ic);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(EntityManager em, InsuranceCard ic) {
		if (!em.contains(ic)) {
			ic = em.merge(ic);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(ic);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(EntityManager em, InsuranceCard ic) {
		if (!em.contains(ic)) {
			ic = em.merge(ic);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(ic);
		em.getTransaction().commit();
	}
}
