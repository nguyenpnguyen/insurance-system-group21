package org.group21.insurance.controllers;


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

/**
 * Controller class for Document entities.
 *
 * @author Group 21
 */
public class DocumentController implements GenericController<Document> {
	// Singleton
	private static DocumentController instance = null;
	
	private DocumentController() {
	}
	
	/**
	 * Get the singleton instance of the DocumentController.
	 *
	 * @return the instance of the DocumentController
	 */
	public static DocumentController getInstance() {
		if (instance == null) {
			instance = new DocumentController();
		}
		return instance;
	}
	
	/**
	 * Find a Document entity by its file name.
	 *
	 * @param fileName the file name of the Document entity
	 *
	 * @return the Document entity with the given file name
	 */
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
	
	/**
	 * Find a Document entity by its ID.
	 *
	 * @param documentId the ID of the Document entity
	 *
	 * @return the Document entity with the given ID
	 */
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
	
	/**
	 * Read all Document entities.
	 *
	 * @return a list of all Document entities
	 */
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
	
	/**
	 * Create a new Document entity.
	 *
	 * @param document the Document entity to create
	 */
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
	
	/**
	 * Update an existing Document entity.
	 *
	 * @param document the Document entity to update
	 */
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
	
	/**
	 * Delete a Document entity.
	 *
	 * @param document the Document entity to delete
	 */
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
	
	/**
	 * Delete all Document entities.
	 */
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
	
	/**
	 * Upload a document to Google Drive and create a new Document entity.
	 *
	 * @param file  the file to upload
	 * @param claim the claim the document belongs to
	 */
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
	
	/**
	 * Download a document from Google Drive.
	 *
	 * @param documentId      the ID of the document to download
	 * @param destinationPath the path to save the downloaded document
	 */
	public void downloadDocument(String documentId, String destinationPath) throws IOException, GeneralSecurityException {
		Drive service = getDriveService();
		Document document = read(documentId).orElseThrow();
		
		String fileName = document.getFileName();
		
		downloadFile(service, documentId, destinationPath + separator + fileName);
	}
	
	/**
	 * Delete a document from Google Drive and the database.
	 *
	 * @param documentId the ID of the document to delete
	 */
	public void deleteDocument(String documentId) throws IOException, GeneralSecurityException {
		Drive service = getDriveService();
		Document document = read(documentId).orElseThrow();
		
		deleteFile(service, documentId);
		delete(document);
	}
	
	/**
	 * Replace a document in Google Drive and update the database.
	 *
	 * @param documentId the ID of the document to replace
	 * @param newFile    the new file to upload
	 */
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
