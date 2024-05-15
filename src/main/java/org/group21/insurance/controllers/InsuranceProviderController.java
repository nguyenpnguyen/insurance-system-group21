package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.InsuranceProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InsuranceProviderController implements GenericController<InsuranceProvider>, UserController<InsuranceProvider> {
	private static InsuranceProviderController instance = null;
	
	private InsuranceProviderController() {
	}
	
	public static InsuranceProviderController getInstance() {
		if (instance == null) {
			instance = new InsuranceProviderController();
		}
		return instance;
	}
	
	public Optional<InsuranceProvider> readInsuranceManager(EntityManager em, String insuranceManagerId) {
		Optional<InsuranceProvider> optionalIm = Optional.ofNullable(em.find(InsuranceProvider.class, insuranceManagerId));
		if (optionalIm.isPresent() && optionalIm.get().isInsuranceManager()) {
			return optionalIm;
		}
		return Optional.empty();
	}
	
	public Optional<InsuranceProvider> readInsuranceSurveyor(EntityManager em, String insuranceSurveyorId) {
		Optional<InsuranceProvider> optionalIs = Optional.ofNullable(em.find(InsuranceProvider.class, insuranceSurveyorId));
		if (optionalIs.isPresent() && !optionalIs.get().isInsuranceManager()) {
			return optionalIs;
		}
		return Optional.empty();
	}
	
	public List<InsuranceProvider> readAllInsuranceManagers(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			Root<InsuranceProvider> root = cq.from(InsuranceProvider.class);
			cq.select(root).where(cb.equal(root.get("is_insurance_manager"), true));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	public List<InsuranceProvider> readAllInsuranceSurveyors(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			Root<InsuranceProvider> root = cq.from(InsuranceProvider.class);
			cq.select(root).where(cb.equal(root.get("is_insurance_manager"), false));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public Optional<InsuranceProvider> read(EntityManager em, String insuranceProviderId) {
		return Optional.ofNullable(em.find(InsuranceProvider.class, insuranceProviderId));
	}
	
	@Override
	public List<InsuranceProvider> readAll(EntityManager em) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			cq.from(InsuranceProvider.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void create(EntityManager em, InsuranceProvider ip) {
		if (!em.contains(ip)) {
			ip = em.merge(ip);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(ip);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(EntityManager em, InsuranceProvider ip) {
		if (!em.contains(ip)) {
			ip = em.merge(ip);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(ip);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(EntityManager em, InsuranceProvider ip) {
		if (!em.contains(ip)) {
			ip = em.merge(ip);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(ip);
		em.getTransaction().commit();
	}
	
	@Override
	public Optional<InsuranceProvider> findByUsername(EntityManager em, String username) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InsuranceProvider> cq = cb.createQuery(InsuranceProvider.class);
			Root<InsuranceProvider> insuranceProviderRoot = cq.from(InsuranceProvider.class);
			cq.select(insuranceProviderRoot).where(cb.equal(insuranceProviderRoot.get("username"), username));
			return Optional.ofNullable(em.createQuery(cq).getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
