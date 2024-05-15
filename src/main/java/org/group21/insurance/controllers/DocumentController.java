package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
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
		return Optional.empty();
	}
	
	@Override
	public Optional<Document> read(EntityManager em, String documentId) {
		return Optional.empty();
	}
	
	@Override
	public List<Document> readAll(EntityManager em) {
		return List.of();
	}
	
	@Override
	public void create(EntityManager em, Document document) {
	
	}
	
	@Override
	public void update(EntityManager em, Document document) {
	
	}
	
	@Override
	public void delete(EntityManager em, Document document) {
	
	}
}
