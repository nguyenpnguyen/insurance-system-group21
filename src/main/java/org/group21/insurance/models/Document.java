package org.group21.insurance.models;

/**
 * This class represents a document in the Insurance Claim Management System.
 * It includes information such as document ID, associated claim, and file name.
 * Each document is associated with a specific claim.
 *
 * @author Group 21
 */

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "Document")
@Table(name = "document_list")
public class Document implements Serializable {
	@Id
	@Column(name = "document_id", nullable = false)
	private String documentId;

	@ManyToOne(targetEntity = Claim.class)
	@JoinColumn(name = "claim_id", referencedColumnName = "claim_id")
	private Claim claim;

	@Column(name = "file_name")
	private String fileName;

	/**
	 * Default constructor for the Document class.
	 */
	public Document() {
	}

	/**
	 * Constructor for the Document class.
	 *
	 * @param documentId The ID of the document.
	 * @param claim The associated claim.
	 * @param fileName The file name of the document.
	 */
	public Document(String documentId, Claim claim, String fileName) {
		this.documentId = documentId;
		this.claim = claim;
		this.fileName = fileName;
	}

	/**
	 * This method returns the document ID.
	 *
	 * @return The document ID.
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * This method sets the document ID.
	 *
	 * @param documentId The document ID.
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/**
	 * This method returns the associated claim.
	 *
	 * @return The associated claim.
	 */
	public Claim getClaim() {
		return claim;
	}

	/**
	 * This method sets the associated claim.
	 *
	 * @param claim The associated claim.
	 */
	public void setClaim(Claim claim) {
		this.claim = claim;
	}

	/**
	 * This method returns the file name of the document.
	 *
	 * @return The file name of the document.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * This method sets the file name of the document.
	 *
	 * @param fileName The file name of the document.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * This method checks if the current object is equal to the given object.
	 *
	 * @param o The object to be compared with the current object.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Document document = (Document) o;
		return Objects.equals(getDocumentId(), document.getDocumentId());
	}

	/**
	 * This method returns the hash code of the current object.
	 *
	 * @return The hash code of the current object.
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getDocumentId());
	}

	/**
	 * This method returns a string representation of the current object.
	 *
	 * @return A string representation of the current object.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("File name: ");
		sb.append(fileName);
		return sb.toString();
	}
}