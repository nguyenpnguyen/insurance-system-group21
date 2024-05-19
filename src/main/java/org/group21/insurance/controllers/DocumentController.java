package org.group21.insurance.controllers;

/**
 * @author Group 21
 */

import com.google.api.services.drive.Drive;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.group21.insurance.models.Claim;
import org.group21.insurance.models.Document;
import org.group21.insurance.utils.EntityManagerFactorySingleton;

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
	
	public Optional<Document> findByFileName(String fileName) throws GeneralSecurityException, IOException {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Document> cq = cb.createQuery(Document.class);
			cq.from(Document.class);
			
			Optional<Document> optionalDocument = em.createQuery(cq).getResultList().stream().filter(d -> d.getFileName().equals(fileName)).findFirst();
			if (optionalDocument.isPresent() && fileExistsInDrive(optionalDocument.get().getDocumentId())) {
				return optionalDocument;
			}
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Document> read(String documentId) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			Optional<Document> optionalDocument = Optional.ofNullable(em.find(Document.class, documentId));
			try {
				if (optionalDocument.isPresent() && fileExistsInDrive(documentId)) {
					return optionalDocument;
				}
			} catch (GeneralSecurityException | IOException e) {
				System.err.println("An error occurred during file existence check: " + e.getMessage());
			}
			return Optional.empty();
		}
	}
	
	@Override
	public List<Document> readAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Document> cq = cb.createQuery(Document.class);
			cq.from(Document.class);
			List<Document> documentListFromDb = em.createQuery(cq).getResultList();
			for (Document document : documentListFromDb) {
				try {
					if (!fileExistsInDrive(document.getDocumentId())) {
						delete(document);
					}
				} catch (GeneralSecurityException | IOException e) {
					System.err.println("An error occurred during file existence check: " + e.getMessage());
				}
			}
			return documentListFromDb;
		}
	}
	
	@Override
	public void create(Document document) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.persist(document);
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void update(Document document) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.merge(document);
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void delete(Document document) {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			em.remove(document);
			em.getTransaction().commit();
		}
	}
	
	@Override
	public void deleteAll() {
		EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
		
		try (EntityManager em = emf.createEntityManager()) {
			List<Document> documentList = readAll();
			
			for (Document document : documentList) {
				try {
					Drive service = getDriveService();
					deleteFile(service, document.getDocumentId());
				} catch (IOException e) {
					System.err.println("An error occurred during file deletion: " + e.getMessage());
				} catch (GeneralSecurityException e) {
					System.err.println("An error occurred during Google Drive service initialization: " + e.getMessage());
				}
			}
			
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();
			}
			
			em.createQuery("DELETE FROM Document").executeUpdate();
			em.getTransaction().commit();
		}
	}
	
	public void uploadDocument(File file, Claim claim) throws IOException, GeneralSecurityException {
		Drive service = getDriveService();
		String fileId = uploadFile(service, file);
		
		String fileName = file.getName();
		
		Document document = new Document();
		document.setDocumentId(fileId);
		document.setClaim(claim);
		document.setFileName(fileName);
		
		create(document);
	}
	
	public void downloadDocument(String documentId, String destinationPath) throws IOException, GeneralSecurityException {
		Drive service = getDriveService();
		Document document = read(documentId).orElseThrow();
		
		String fileName = document.getFileName();
		
		downloadFile(service, documentId, destinationPath + separator + fileName);
	}
	
	public void deleteDocument(String documentId) throws IOException, GeneralSecurityException {
		Drive service = getDriveService();
		Document document = read(documentId).orElseThrow();
		
		deleteFile(service, documentId);
		delete(document);
	}
	
	public void replaceDocument(String documentId, File newFile) throws IOException, GeneralSecurityException {
		Drive service = getDriveService();
		Document document = read(documentId).orElseThrow();
		
		String fileId = document.getDocumentId();
		
		String newFileId = replaceFile(service, fileId, newFile);
		
		document.setDocumentId(newFileId);
		document.setFileName(newFile.getName());
		update(document);
	}
	
	private boolean fileExistsInDrive(String documentId) throws GeneralSecurityException, IOException {
		return !findFileById(getDriveService(), documentId).isEmpty();
	}
}
