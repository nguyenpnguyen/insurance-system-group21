package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "insurance_card")
public class InsuranceCard implements Serializable {
	@Id
	@Column(name = "card_number", nullable = false)
	private String cardNumber;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "policy_owner_id", referencedColumnName = "id")
	private PolicyOwner policyOwner;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "card_holder_id", referencedColumnName = "id")
	private Customer cardHolder;
	
	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	
	public InsuranceCard() {
		this.cardNumber = "";
		this.policyOwner = new PolicyOwner();
		this.cardHolder = null;
		this.expirationDate = LocalDate.now();
	}
	
	public InsuranceCard(String cardNumber, PolicyOwner policyOwner, Customer cardHolder, LocalDate expirationDate) {
		this.cardNumber = cardNumber;
		this.policyOwner = policyOwner;
		this.cardHolder = cardHolder;
		this.expirationDate = expirationDate;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public PolicyOwner getPolicyOwner() {
		return policyOwner;
	}
	
	public void setPolicyOwner(PolicyOwner policyOwner) {
		this.policyOwner = policyOwner;
	}
	
	public Customer getCardHolder() {
		return cardHolder;
	}
	
	public void setCardHolder(Customer cardHolder) {
		this.cardHolder = cardHolder;
	}
	
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
}
