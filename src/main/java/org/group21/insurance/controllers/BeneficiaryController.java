package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.Beneficiary;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

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
	
	public Optional<Beneficiary> readPolicyHolder(String policyHolderId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			Optional<Beneficiary> optionalPh = Optional.ofNullable(em.find(Beneficiary.class, policyHolderId));
			if (optionalPh.isPresent() && optionalPh.get().isPolicyHolder()) {
				return optionalPh;
			}
			return Optional.empty();
		}
	}
	
	public Optional<Beneficiary> readDependent(String dependentId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			Optional<Beneficiary> optionalDependent = Optional.ofNullable(em.find(Beneficiary.class, dependentId));
			if (optionalDependent.isPresent() && !optionalDependent.get().isPolicyHolder()) {
				return optionalDependent;
			}
			return Optional.empty();
		}
	}
	
	public List<Beneficiary> readAllPolicyHolders() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> root = cq.from(Beneficiary.class);
			cq.select(root).where(cb.equal(root.get("isPolicyHolder"), true));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	public List<Beneficiary> readAllDependents() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> root = cq.from(Beneficiary.class);
			cq.select(root).where(cb.equal(root.get("isPolicyHolder"), false));
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public Optional<Beneficiary> read(String beneficiaryId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(Beneficiary.class, beneficiaryId));
		}
	}
	
	@Override
	public List<Beneficiary> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			cq.from(Beneficiary.class);
			return em.createQuery(cq).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void create(Beneficiary b) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(b)) {
				b = em.merge(b);
			}
			
			em.persist(b);
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void update(Beneficiary b) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			if (!em.contains(b)) {
				em.merge(b);
			}
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void delete(Beneficiary b) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(b)) {
				b = em.merge(b);
			}
			
			em.remove(b);
			em.getTransaction().commit();
		}
	}
	
	@Override
	public Optional<Beneficiary> findByUsername(String username) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Beneficiary> cq = cb.createQuery(Beneficiary.class);
			Root<Beneficiary> beneficiaryRoot = cq.from(Beneficiary.class);
			cq.select(beneficiaryRoot).where(cb.equal(beneficiaryRoot.get("username"), username));
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
			em.createQuery("DELETE FROM Beneficiary").executeUpdate();
			em.getTransaction().commit();
		}
	}
}
