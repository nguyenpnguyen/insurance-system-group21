package org.group21.insurance.daos;

import jakarta.persistence.EntityManager;
import org.group21.insurance.models.DocumentList;

import java.util.List;
import java.util.Optional;

public class DocumentListDAO implements Dao<DocumentList>{
	EntityManager entityManager;
	
	@Override
	public Optional<DocumentList> get(String id) {
		// Get document list from database
		return Optional.empty();
	}
	
	@Override
	public List<DocumentList> getAll() {
		// Get all document lists from database
		return null;
	}
	
	@Override
	public void save(DocumentList documentList) {
		// Save document list to database
	}
	
	@Override
	public void update(DocumentList documentList) {
		// Update document list in database
	}
	
	@Override
	public void delete(DocumentList documentList) {
		// Delete document list from database
	}
}
