package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.Claim;

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
	public Optional<Claim> read(EntityManager em, String claimId) {
		return Optional.ofNullable(em.find(Claim.class, claimId));
	}
	
	@Override
	public List<Claim> readAll(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Claim> cq = cb.createQuery(Claim.class);
			cq.from(Claim.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void create(EntityManager em, Claim c) {
		if (!em.contains(c)) {
			c = em.merge(c);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(c);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(EntityManager em, Claim c) {
		if (!em.contains(c)) {
			c = em.merge(c);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(c);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(EntityManager em, Claim c) {
		if (!em.contains(c)) {
			c = em.merge(c);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(c);
		em.getTransaction().commit();
	}
}
