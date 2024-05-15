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
	EntityManager em;
	private static SystemAdminController instance = null;
	
	private SystemAdminController(EntityManager em) {
		this.em = em;
	}
	
	public static SystemAdminController getInstance(EntityManager em) {
		if (instance == null) {
			instance = new SystemAdminController(em);
		}
		return instance;
	}
	
	public Optional<SystemAdmin> read(String id) {
		return Optional.ofNullable(em.find(SystemAdmin.class, id));
	}
	
	@Override
	public List<SystemAdmin> readAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SystemAdmin> cq = cb.createQuery(SystemAdmin.class);
		cq.from(SystemAdmin.class);
		return em.createQuery(cq).getResultList();
	}
	
	@Override
	public void create(SystemAdmin systemAdmin) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(systemAdmin);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(SystemAdmin systemAdmin) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(systemAdmin);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(SystemAdmin systemAdmin) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.remove(systemAdmin);
		em.getTransaction().commit();
	}
	
	@Override
	public Optional<SystemAdmin> findByUsername(String username) {
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
