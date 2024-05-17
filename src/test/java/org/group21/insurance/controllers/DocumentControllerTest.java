package org.group21.insurance.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.group21.insurance.models.Claim;
import org.group21.insurance.models.Document;
import org.group21.insurance.utils.EntityManagerFactorySingleton;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DocumentControllerTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private DocumentController documentController;
	private List<Object> createdEntities;
	
	@BeforeAll
	static void setUpAll() {
		emf = EntityManagerFactorySingleton.getInstance();
	}
	
	@AfterAll
	static void tearDownAll() {
		EntityManagerFactorySingleton.close();
	}
	
	@BeforeEach
	void setUp() {
		em = emf.createEntityManager();
		createdEntities = new ArrayList<>();
		documentController = DocumentController.getInstance();
	}
	
	@AfterEach
	void tearDown() {
		try (EntityManager em = emf.createEntityManager()) {
			for (Object entity : createdEntities) {
				if (!em.getTransaction().isActive()) {
					em.getTransaction().begin();
				}
				em.remove(entity);
				em.getTransaction().commit();
			}
			createdEntities.clear();
			documentController = null;
		}
	}
	
	@Test
	void create() {
		Claim claim = new Claim();
		claim.setStatus(Claim.ClaimStatus.NEW);
		
		em.getTransaction().begin();
		em.persist(claim);
		em.getTransaction().commit();
		
		Document document = new Document();
		document.setFileName("FileName");
		document.setClaim(claim);
		
		documentController.create(document);
		
		createdEntities.add(document);
		
		Document savedDocument = em.find(Document.class, document.getDocumentId());
		
		assertEquals(document.getDocumentId(), savedDocument.getDocumentId());
		assertEquals(document.getFileName(), savedDocument.getFileName());
		assertEquals(document.getClaim().getClaimId(), savedDocument.getClaim().getClaimId());
	}
	
	@Test
	void findByFileName() {
		Document document = createAndPersist();
		
		String fileName = "FileName";
		
		Optional<Document> optionalDocument = Optional.empty();
		try {
			optionalDocument = documentController.findByFileName(fileName);
		} catch (GeneralSecurityException | IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		assertTrue(optionalDocument.isPresent());
		
		Document retrievedDocument = optionalDocument.get();
		assertEquals(document.getDocumentId(), retrievedDocument.getDocumentId());
		assertEquals(document.getClaim().getClaimId(), retrievedDocument.getClaim().getClaimId());
	}
	
	@Test
	void read() {
		Document savedDocument = createAndPersist();
		
		Optional<Document> optionalDocument = documentController.read(savedDocument.getDocumentId());
		
		assertTrue(optionalDocument.isPresent());
		
		Document retrievedDocument = optionalDocument.get();
		
		assertEquals(savedDocument.getDocumentId(), retrievedDocument.getDocumentId());
		assertEquals(savedDocument.getFileName(), retrievedDocument.getFileName());
		assertEquals(savedDocument.getClaim().getClaimId(), retrievedDocument.getClaim().getClaimId());
	}
	
	@Test
	void readAll() {
		Document d1 = createAndPersist();
		Document d2 = createAndPersist();
		Document d3 = createAndPersist();
		
		List<Document> documents = documentController.readAll();
		
		int expectedSize = 3;
		
		assertFalse(documents.isEmpty());
		assertEquals(expectedSize, documents.size());
		
		for (Document document : List.of(d1, d2, d3)) {
			assertTrue(documents.contains(document));
		}
	}
	
	@Test
	void update() {
		Document savedDocument = createAndPersist();
		
		savedDocument.setFileName("NewFileName");
		
		documentController.update(savedDocument);
		
		Document updatedDocument = em.find(Document.class, savedDocument.getDocumentId());
		
		assertEquals(savedDocument.getDocumentId(), updatedDocument.getDocumentId());
		assertEquals(savedDocument.getFileName(), updatedDocument.getFileName());
		assertEquals(savedDocument.getClaim().getClaimId(), updatedDocument.getClaim().getClaimId());
	}
	
	@Test
	void delete() {
		Document savedDocument = createAndPersist();
		
		documentController.delete(savedDocument);
		
		Document deletedDocument = em.find(Document.class, savedDocument.getDocumentId());
		
		assertNull(deletedDocument);
	}
	
	private Document createAndPersist() {
		Claim claim = new Claim();
		claim.setStatus(Claim.ClaimStatus.NEW);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(claim);
		em.getTransaction().commit();
		
		Document document = new Document();
		document.setFileName("FileName");
		document.setClaim(claim);
		
		createdEntities.add(claim);
		createdEntities.add(document);
		
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(document);
		em.getTransaction().commit();
		
		return document;
	}
}