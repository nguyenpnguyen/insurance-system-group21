package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Claim")
@Table(name = "claims")
public class Claim implements Serializable {
	@Id
	@Column(name = "claim_id")
	private String claimId;
	
	@Column(name = "claim_date")
	private LocalDate claimDate;
	
	@ManyToOne(targetEntity = Beneficiary.class)
	@JoinColumn(name = "insured_person_id", referencedColumnName = "customer_id")
	private Beneficiary insuredPerson;
	
	@Column(name = "exam_date")
	private LocalDate examDate;
	
	@ManyToOne(targetEntity = InsuranceCard.class)
	@JoinColumn(name = "insurance_card_number", referencedColumnName = "card_number")
	private InsuranceCard insuranceCard;
	
	@OneToMany(mappedBy = "claim", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Document> document;
	
	@Column(name = "claim_amount")
	private long claimAmount;
	
	@ManyToOne(targetEntity = BankingInfo.class)
	@JoinColumn(name = "receiver_account_number", referencedColumnName = "account_number")
	private BankingInfo receiverBankingInfo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ClaimStatus status;
	
	public Claim() {
	}
	
	public Claim(String claimId) {
		this.claimId = claimId;
	}
	
	public String getClaimId() {
		return claimId;
	}
	
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	
	public LocalDate getClaimDate() {
		return claimDate;
	}
	
	public void setClaimDate(LocalDate claimDate) {
		this.claimDate = claimDate;
	}
	
	public Beneficiary getInsuredPerson() {
		return insuredPerson;
	}
	
	public void setInsuredPerson(Beneficiary insuredPerson) {
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
	
	public List<Document> getDocumentList() {
		return document;
	}
	
	public void setDocumentList(List<Document> document) {
		this.document = document;
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
