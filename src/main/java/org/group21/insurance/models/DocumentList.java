package org.group21.insurance.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocumentList {
	private Claim claim;
	private List<File> documentList;
	
	public DocumentList() {
		this.claim = new Claim();
		this.documentList = new ArrayList<>();
	}
	
	public DocumentList(Claim claim, List<File> documentList) {
		this.claim = claim;
		this.documentList = documentList;
	}
	
	public Claim getClaim() {
		return claim;
	}
	
	public void setClaim(Claim claim) {
		this.claim = claim;
	}
	
	public List<File> getDocumentList() {
		return documentList;
	}
	
	public void setDocumentList(List<File> documentList) {
		this.documentList = documentList;
	}
}
