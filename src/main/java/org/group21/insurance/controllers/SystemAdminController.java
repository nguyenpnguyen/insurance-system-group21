package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.SystemAdmin;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

import java.util.List;
import java.util.Optional;

public class SystemAdminController implements GenericController<SystemAdmin>, UserController<SystemAdmin> {
	private static SystemAdminController instance = null;
	
	private SystemAdminController() {
	}
	
	public static SystemAdminController getInstance() {
		if (instance == null) {
			instance = new SystemAdminController();
		}
		return instance;
	}
	
	public Optional<SystemAdmin> read(String id) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			return Optional.ofNullable(em.find(SystemAdmin.class, id));
		}
	}
	
	@Override
	public List<SystemAdmin> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<SystemAdmin> cq = cb.createQuery(SystemAdmin.class);
			cq.from(SystemAdmin.class);
			return em.createQuery(cq).getResultList();
		}
	}
	
	@Override
	public void create(SystemAdmin systemAdmin) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			// merge entity if detached
			if (!em.contains(systemAdmin)) {
				systemAdmin = em.merge(systemAdmin);
			}
			em.persist(systemAdmin);
			
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void update(SystemAdmin systemAdmin) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(systemAdmin)) {
				em.merge(systemAdmin);
			}
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void delete(SystemAdmin systemAdmin) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			if (!em.contains(systemAdmin)) {
				systemAdmin = em.merge(systemAdmin);
			}
			
			em.remove(systemAdmin);
			em.getTransaction().commit();
		}
	}
	
	@Override
	public Optional<SystemAdmin> findByUsername(String username) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<SystemAdmin> cq = cb.createQuery(SystemAdmin.class);
			Root<SystemAdmin> admin = cq.from(SystemAdmin.class);
			cq.select(admin).where(cb.equal(admin.get("username"), username));
			SystemAdmin result = em.createQuery(cq).getSingleResult();
			return Optional.ofNullable(result);
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
			em.createQuery("DELETE FROM SystemAdmin").executeUpdate();
			em.getTransaction().commit();
		}
	}
}
