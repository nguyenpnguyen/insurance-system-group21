package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.DocumentList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocumentListDAO implements Dao<DocumentList> {
	EntityManager entityManager;
	
	public DocumentListDAO() {
	}
	
	public DocumentListDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public Optional<DocumentList> get(String id) {
		return Optional.empty();
	}
	
	@Override
	public List<DocumentList> getAll() {
		return new ArrayList<>();
	}
	
	@Override
	public void save(DocumentList documentList) {
	
	}
	
	@Override
	public void update(DocumentList documentList) {
	
	}
	
	@Override
	public void delete(DocumentList documentList) {
	
	}
}
