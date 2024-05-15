package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.BankingInfo;

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
	public Optional<BankingInfo> read(EntityManager em, String accountNumber) {
		return Optional.ofNullable(em.find(BankingInfo.class, accountNumber));
	}
	
	@Override
	public List<BankingInfo> readAll(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<BankingInfo> cq = cb.createQuery(BankingInfo.class);
			cq.from(BankingInfo.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void create(EntityManager em, BankingInfo bi) {
		if (!em.contains(bi)) {
			bi = em.merge(bi);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(bi);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(EntityManager em, BankingInfo bi) {
		if (!em.contains(bi)) {
			bi = em.merge(bi);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(bi);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(EntityManager em, BankingInfo bi) {
		if (!em.contains(bi)) {
			bi = em.merge(bi);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(bi);
		em.getTransaction().commit();
	}
}
