package org.group21.insurance.controllers;

import com.google.api.services.drive.Drive;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.Claim;
import org.group21.insurance.models.Document;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

import static java.io.File.separator;
import static org.group21.insurance.utils.FileHandler.*;

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
	
	public void uploadDocument(EntityManager em, File filePath, Claim claim) throws IOException, GeneralSecurityException {
		try {
			Drive service = getDriveService();
			String fileId = uploadFile(service, filePath);
			
			String fileName = filePath.getName();
			
			Document document = new Document();
			document.setDocumentId(fileId);
			document.setClaim(claim);
			document.setFileName(fileName);
			
			create(em, document);
		} catch (IOException e) {
			// Handle IOException (e.g., logging, user notification)
			System.err.println("An error occurred during file upload: " + e.getMessage());
			throw e;
		} catch (GeneralSecurityException e) {
			// Handle GeneralSecurityException (e.g., logging, user notification)
			System.err.println("A security error occurred: " + e.getMessage());
			throw e;
		}
	}
	
	public void downloadDocument(EntityManager em, String documentId, String destinationPath) throws IOException, GeneralSecurityException {
		try {
			Drive service = getDriveService();
			Document document = read(em, documentId).orElseThrow();
			
			String fileName = document.getFileName();
			
			downloadFile(service, documentId, destinationPath + separator + fileName);
		} catch (IOException e) {
			// Handle IOException (e.g., logging, user notification)
			System.err.println("An error occurred during file download: " + e.getMessage());
			throw e;
		} catch (GeneralSecurityException e) {
			// Handle GeneralSecurityException (e.g., logging, user notification)
			System.err.println("A security error occurred: " + e.getMessage());
			throw e;
		}
	}
	
	public void deleteDocument(EntityManager em, String documentId) throws IOException, GeneralSecurityException {
		try {
			Drive service = getDriveService();
			Document document = read(em, documentId).orElseThrow();
			
			deleteFile(service, documentId);
			delete(em, document);
		} catch (IOException e) {
			// Handle IOException (e.g., logging, user notification)
			System.err.println("An error occurred during file deletion: " + e.getMessage());
			throw e;
		} catch (GeneralSecurityException e) {
			// Handle GeneralSecurityException (e.g., logging, user notification)
			System.err.println("A security error occurred: " + e.getMessage());
			throw e;
		}
	}
	
	public void replaceDocument(EntityManager em, String documentId, File newFilePath) throws IOException, GeneralSecurityException {
		try {
			Drive service = getDriveService();
			Document document = read(em, documentId).orElseThrow();
			
			String fileId = document.getDocumentId();
			
			String newFileId = replaceFile(service, fileId, newFilePath);
			
			document.setDocumentId(newFileId);
			document.setFileName(newFilePath.getName());
			update(em, document);
		} catch (IOException e) {
			// Handle IOException (e.g., logging, user notification)
			System.err.println("An error occurred during file replacement: " + e.getMessage());
			throw e;
		} catch (GeneralSecurityException e) {
			// Handle GeneralSecurityException (e.g., logging, user notification)
			System.err.println("A security error occurred: " + e.getMessage());
			throw e;
		}
	}
}
