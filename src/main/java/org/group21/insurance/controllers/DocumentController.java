package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.Document;

import java.util.List;
import java.util.Optional;

public class DocumentController implements GenericController<Document> {
	EntityManager em;
	
	private static DocumentController instance = null;
	
	private DocumentController(EntityManager em) {
		this.em = em;
	}
	
	public static DocumentController getInstance(EntityManager em) {
		if (instance == null) {
			instance = new DocumentController(em);
		}
		return instance;
	}
	
	public Optional<Document> findByFileName(String fileName) {
		return Optional.empty();
	}
	
	@Override
	public Optional<Document> read(String documentId) {
		return Optional.empty();
	}
	
	@Override
	public List<Document> readAll() {
		return List.of();
	}
	
	@Override
	public void create(Document document) {
	
	}
	
	@Override
	public void update(Document document) {
	
	}
	
	@Override
	public void delete(Document document) {
	
	}
}
