package org.group21.insurance.models;

/**
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
	
	public Document() {
	}
	
	public Document(String documentId, Claim claim, String fileName) {
		this.documentId = documentId;
		this.claim = claim;
		this.fileName = fileName;
	}
	
	public String getDocumentId() {
		return documentId;
	}
	
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
	public Claim getClaim() {
		return claim;
	}
	
	public void setClaim(Claim claim) {
		this.claim = claim;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Document document = (Document) o;
		return Objects.equals(getDocumentId(), document.getDocumentId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getDocumentId());
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("File name: ");
		sb.append(fileName);
		return sb.toString();
	}
}
