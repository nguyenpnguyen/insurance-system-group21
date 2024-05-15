package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.group21.insurance.models.SystemAdmin;

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
	
	public Optional<SystemAdmin> read(EntityManager em, String id) {
		return Optional.ofNullable(em.find(SystemAdmin.class, id));
	}
	
	@Override
	public List<SystemAdmin> readAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SystemAdmin> cq = cb.createQuery(SystemAdmin.class);
		cq.from(SystemAdmin.class);
		return em.createQuery(cq).getResultList();
	}
	
	@Override
	public void create(EntityManager em, SystemAdmin systemAdmin) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(systemAdmin);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(EntityManager em, SystemAdmin systemAdmin) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(systemAdmin);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(EntityManager em, SystemAdmin systemAdmin) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(systemAdmin);
		em.getTransaction().commit();
	}
	
	@Override
	public Optional<SystemAdmin> findByUsername(EntityManager em, String username) {
		try {
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
}
