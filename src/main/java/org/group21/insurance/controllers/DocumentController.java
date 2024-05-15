package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.Document;

import java.util.List;
import java.util.Optional;

public class DocumentController implements GenericController<Document> {
	private static DocumentController instance = null;
	
	private DocumentController() {
	}
	
	public static DocumentController getInstance() {
		if (instance == null) {
			instance = new DocumentController();
		}
		return instance;
	}
	
	public Optional<Document> findByFileName(EntityManager em, String fileName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Document> cq = cb.createQuery(Document.class);
		cq.from(Document.class);
		return em.createQuery(cq).getResultList().stream().filter(d -> d.getFileName().equals(fileName)).findFirst();
	}
	
	@Override
	public Optional<Document> read(EntityManager em, String documentId) {
		return Optional.ofNullable(em.find(Document.class, documentId));
	}
	
	@Override
	public List<Document> readAll(EntityManager em) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Document> cq = cb.createQuery(Document.class);
		cq.from(Document.class);
		return em.createQuery(cq).getResultList();
	}
	
	@Override
	public void create(EntityManager em, Document document) {
		if (!em.contains(document)) {
			document = em.merge(document);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(document);
		em.getTransaction().commit();
	}
	
	@Override
	public void update(EntityManager em, Document document) {
		if (!em.contains(document)) {
			document = em.merge(document);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		em.persist(document);
		em.getTransaction().commit();
	}
	
	@Override
	public void delete(EntityManager em, Document document) {
		if (!em.contains(document)) {
			document = em.merge(document);
		}
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		
		em.remove(document);
		em.getTransaction().commit();
	}
}
