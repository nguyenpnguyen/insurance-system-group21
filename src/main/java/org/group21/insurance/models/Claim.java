package org.group21.insurance.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

@Entity
public class Claim {
	@Id
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name = "claim_date")
	private LocalDate claimDate;
	
	@ManyToOne(targetEntity = Customer.class)
	private Customer insuredPerson;
	
	@Column(name = "exam_date")
	private LocalDate examDate;
	
	@ManyToOne(targetEntity = InsuranceCard.class)
	private InsuranceCard insuranceCard;
	
	@ElementCollection(targetClass = File.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "document_list", joinColumns = @JoinColumn(name = "claim_id"))
	@Column(name = "document", nullable = false)
	private List<File> documentList;
	
	@Column(name = "claim_amount")
	private long claimAmount;
	
	@ManyToOne(targetEntity = BankingInfo.class)
	private BankingInfo receiverBankingInfo;
	
	@Enumerated(EnumType.STRING)
	private ClaimStatus status;
	
	public Claim() {
		this.id = "";
		this.claimDate = LocalDate.now();
		this.insuredPerson = null;
		this.examDate = LocalDate.now();
		this.insuranceCard = new InsuranceCard();
		this.documentList = new ArrayList<>();
		this.claimAmount = 0;
		this.receiverBankingInfo = new BankingInfo();
		this.status = ClaimStatus.NEW;
	}
	
	public Claim(String id, LocalDate claimDate, Customer insuredPerson, LocalDate examDate, InsuranceCard insuranceCard, List<File> documentList, long claimAmount, BankingInfo receiverBankingInfo, ClaimStatus status) {
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
	
	public List<File> getDocumentList() {
		return documentList;
	}
	
	public void setDocumentList(List<File> documentList) {
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
