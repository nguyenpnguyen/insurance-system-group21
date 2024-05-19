package org.group21.insurance.models;

/**
 * This class represents a claim in the Insurance Claim Management System.
 * It includes information such as claim ID, claim date, insured person, exam date, insurance card, documents, claim amount, receiver banking info, and claim status.
 *
 * @author Group 21
 */

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity(name = "Claim")
@Table(name = "claims")
public class Claim implements Serializable {
	@Id
	@GeneratedValue(generator = "claim-id-generator")
	@GenericGenerator(name = "claim-id-generator",
			strategy = "org.group21.insurance.utils.IdGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "prefix", value = "f"),
					@org.hibernate.annotations.Parameter(name = "length", value = "10")
			})
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

	@OneToMany(mappedBy = "claim", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Document> document;

	@Column(name = "claim_amount")
	private long claimAmount;

	@ManyToOne(targetEntity = BankingInfo.class)
	@JoinColumn(name = "receiver_account_number", referencedColumnName = "account_number")
	private BankingInfo receiverBankingInfo;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ClaimStatus status;

	/**
	 * Default constructor for the Claim class.
	 */
	public Claim() {
	}

	/**
	 * Constructor for the Claim class.
	 *
	 * @param claimId The ID of the claim.
	 */
	public Claim(String claimId) {
		this.claimId = claimId;
	}

	/**
	 * This method returns the claim ID.
	 *
	 * @return The claim ID.
	 */
	public String getClaimId() {
		return claimId;
	}

	/**
	 * This method sets the claim ID.
	 *
	 * @param claimId The claim ID.
	 */
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

	/**
	 * This method returns the claim date.
	 *
	 * @return The claim date.
	 */
	public LocalDate getClaimDate() {
		return claimDate;
	}

	/**
	 * This method sets the claim date.
	 *
	 * @param claimDate The claim date.
	 */
	public void setClaimDate(LocalDate claimDate) {
		this.claimDate = claimDate;
	}

	/**
	 * This method returns the insured person.
	 *
	 * @return The insured person.
	 */
	public Beneficiary getInsuredPerson() {
		return insuredPerson;
	}

	/**
	 * This method sets the insured person.
	 *
	 * @param insuredPerson The insured person.
	 */
	public void setInsuredPerson(Beneficiary insuredPerson) {
		this.insuredPerson = insuredPerson;
	}

	/**
	 * This method returns the exam date.
	 *
	 * @return The exam date.
	 */
	public LocalDate getExamDate() {
		return examDate;
	}

	/**
	 * This method sets the exam date.
	 *
	 * @param examDate The exam date.
	 */
	public void setExamDate(LocalDate examDate) {
		this.examDate = examDate;
	}

	/**
	 * This method returns the insurance card.
	 *
	 * @return The insurance card.
	 */
	public InsuranceCard getInsuranceCard() {
		return insuranceCard;
	}

	/**
	 * This method sets the insurance card.
	 *
	 * @param insuranceCard The insurance card.
	 */
	public void setInsuranceCard(InsuranceCard insuranceCard) {
		this.insuranceCard = insuranceCard;
	}

	/**
	 * This method returns the list of documents.
	 *
	 * @return The list of documents.
	 */
	public List<Document> getDocumentList() {
		return document;
	}

	/**
	 * This method sets the list of documents.
	 *
	 * @param document The list of documents.
	 */
	public void setDocumentList(List<Document> document) {
		this.document = document;
	}

	/**
	 * This method returns the claim amount.
	 *
	 * @return The claim amount.
	 */
	public long getClaimAmount() {
		return claimAmount;
	}

	/**
	 * This method sets the claim amount.
	 *
	 * @param claimAmount The claim amount.
	 */
	public void setClaimAmount(long claimAmount) {
		this.claimAmount = claimAmount;
	}

	/**
	 * This method returns the receiver banking info.
	 *
	 * @return The receiver banking info.
	 */
	public BankingInfo getReceiverBankingInfo() {
		return receiverBankingInfo;
	}

	/**
	 * This method sets the receiver banking info.
	 *
	 * @param receiverBankingInfo The receiver banking info.
	 */
	public void setReceiverBankingInfo(BankingInfo receiverBankingInfo) {
		this.receiverBankingInfo = receiverBankingInfo;
	}

	/**
	 * This method returns the claim status.
	 *
	 * @return The claim status.
	 */
	public ClaimStatus getStatus() {
		return status;
	}

	/**
	 * This method sets the claim status.
	 *
	 * @param status The claim status.
	 */
	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	/**
	 * This enum represents the possible statuses of a claim.
	 */
	public enum ClaimStatus {
		NEW,
		PROCESSING,
		DONE
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
		Claim claim = (Claim) o;
		return Objects.equals(getClaimId(), claim.getClaimId());
	}

	/**
	 * This method returns the hash code of the current object.
	 *
	 * @return The hash code of the current object.
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getClaimId());
	}

	/**
	 * This method returns a string representation of the current object.
	 *
	 * @return A string representation of the current object.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Claim ID:");
		sb.append(claimId);
		return sb.toString();
	}
}