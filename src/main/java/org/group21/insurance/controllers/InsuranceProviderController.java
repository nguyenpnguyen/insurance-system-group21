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
	
	@Override
	public Optional<InsuranceProvider> read(String insuranceProviderId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(InsuranceProvider.class, insuranceProviderId));
		}
	}
	
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
	
	@Override
	public void batchCreate(List<InsuranceProvider> insuranceProviders) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			for (InsuranceProvider ip : insuranceProviders) {
				if (!em.contains(ip)) {
					ip = em.merge(ip);
				}
				em.persist(ip);
			}
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void batchDelete(List<InsuranceProvider> insuranceProviders) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			for (InsuranceProvider ip : insuranceProviders) {
				if (!em.contains(ip)) {
					ip = em.merge(ip);
				}
				em.remove(ip);
			}
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void batchUpdate(List<InsuranceProvider> insuranceProviders) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			for (InsuranceProvider ip : insuranceProviders) {
				if (!em.contains(ip)) {
					em.merge(ip);
				}
			}
			em.getTransaction().commit();
		}
	}
}
