package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.Beneficiary;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BeneficiaryController implements GenericController<Beneficiary>, UserController<Beneficiary> {
	private static BeneficiaryController instance = null;
	
	private BeneficiaryController() {
	}
	
	public static BeneficiaryController getInstance() {
		if (instance == null) {
			instance = new BeneficiaryController();
		}
		return instance;
	}
	
	public Optional<Beneficiary> readPolicyHolder(EntityManager em, String policyHolderId) {
		Optional<Beneficiary> optionalPh = Optional.ofNullable(em.find(Beneficiary.class, policyHolderId));
		if (optionalPh.isPresent() && optionalPh.get().isPolicyHolder()) {
			return optionalPh;
		}
		return Optional.empty();
	}
	
	public Optional<Beneficiary> readDependent(EntityManager em, String dependentId) {
		Optional<Beneficiary> optionalDependent = Optional.ofNullable(em.find(Beneficiary.class, dependentId));
		if (optionalDependent.isPresent() && !optionalDependent.get().isPolicyHolder()) {
			return optionalDependent;
		}
		return Optional.empty();
	}
	
	public List<Beneficiary> readAllPolicyHolders(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> root = cq.from(Beneficiary.class);
			cq.select(root).where(cb.equal(root.get("is_policy_holder"), true));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	public List<Beneficiary> readAllDependents(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> root = cq.from(Beneficiary.class);
			cq.select(root).where(cb.equal(root.get("is_policy_holder"), false));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public Optional<Beneficiary> read(EntityManager em, String beneficiaryId) {
		return Optional.ofNullable(em.find(Beneficiary.class, beneficiaryId));
	}
	
	@Override
	public List<Beneficiary> readAll(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			cq.from(Beneficiary.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void create(EntityManager em, Beneficiary b) {
		if (!em.contains(b)) {
			b = em.merge(b);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(b);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(EntityManager em, Beneficiary b) {
		if (!em.contains(b)) {
			b = em.merge(b);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(b);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(EntityManager em, Beneficiary b) {
		if (!em.contains(b)) {
			b = em.merge(b);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(b);
		em.getTransaction().commit();
	}
	
	@Override
	public Optional<Beneficiary> findByUsername(EntityManager em, String username) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> beneficiaryRoot = cq.from(Beneficiary.class);
			cq.select(beneficiaryRoot).where(cb.equal(beneficiaryRoot.get("username"), username));
			return Optional.ofNullable(em.createQuery(cq).getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
