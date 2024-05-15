package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Document")
@Table(name = "document_list")
public class Document implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_list_seq")
	@SequenceGenerator(name = "document_list_seq", sequenceName = "document_list_seq", allocationSize = 1)
	@Column(name = "document_id", nullable = false)
	private long documentId;
	
	@ManyToOne(targetEntity = Claim.class)
	@JoinColumn(name = "claim_id", referencedColumnName = "claim_id")
	private Claim claim;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_url")
	private String fileUrl;
	
	public Document() {
	}
	
	public Document(Claim claim, String fileName, String fileUrl) {
		this.claim = claim;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
	}
	
	public long getDocumentId() {
		return documentId;
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
	
	public String getFileUrl() {
		return fileUrl;
	}
	
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
