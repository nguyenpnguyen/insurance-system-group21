package org.group21.insurance.models;

import java.time.LocalDate;

public class Claim {
	private String id;
	private LocalDate claimDate;
	private Customer insuredPerson;
	private LocalDate examDate;
	private InsuranceCard insuranceCard;
	private DocumentList documentList;
	private long claimAmount;
	private BankingInfo receiverBankingInfo;
	private ClaimStatus status;
	
	public Claim() {
		this.id = "";
		this.claimDate = LocalDate.now();
		this.insuredPerson = null;
		this.examDate = LocalDate.now();
		this.insuranceCard = new InsuranceCard();
		this.documentList = new DocumentList();
		this.claimAmount = 0;
		this.receiverBankingInfo = new BankingInfo();
		this.status = ClaimStatus.NEW;
	}
	
	public Claim(String id, LocalDate claimDate, Customer insuredPerson, LocalDate examDate, InsuranceCard insuranceCard, DocumentList documentList, long claimAmount, BankingInfo receiverBankingInfo, ClaimStatus status) {
		this.id = id;
		this.claimDate = claimDate;
		this.insuredPerson = insuredPerson;
		this.examDate = examDate;
		this.insuranceCard = insuranceCard;
		this.documentList = documentList;
		this.claimAmount = claimAmount;
		this.receiverBankingInfo = receiverBankingInfo;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public LocalDate getClaimDate() {
		return claimDate;
	}
	
	public void setClaimDate(LocalDate claimDate) {
		this.claimDate = claimDate;
	}
	
	public Customer getInsuredPerson() {
		return insuredPerson;
	}
	
	public void setInsuredPerson(Customer insuredPerson) {
		this.insuredPerson = insuredPerson;
	}
	
	public LocalDate getExamDate() {
		return examDate;
	}
	
	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}
	
	public InsuranceCard getInsuranceCard() {
		return insuranceCard;
	}
	
	public void setInsuranceCard(InsuranceCard insuranceCard) {
		this.insuranceCard = insuranceCard;
	}
	
	public DocumentList getDocumentList() {
		return documentList;
	}
	
	public void setDocumentList(DocumentList documentList) {
		this.documentList = documentList;
	}
	
	public long getClaimAmount() {
		return claimAmount;
	}
	
	public void setClaimAmount(long claimAmount) {
		this.claimAmount = claimAmount;
	}
	
	public BankingInfo getReceiverBankingInfo() {
		return receiverBankingInfo;
	}
	
	public void setReceiverBankingInfo(BankingInfo receiverBankingInfo) {
		this.receiverBankingInfo = receiverBankingInfo;
	}
	
	public ClaimStatus getStatus() {
		return status;
	}
	
	public void setStatus(ClaimStatus status) {
		this.status = status;
	}
	
	public enum ClaimStatus {
		NEW,
		PROCESSING,
		DONE
	}
}
