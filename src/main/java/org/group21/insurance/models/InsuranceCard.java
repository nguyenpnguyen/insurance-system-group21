package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "InsuranceCard")
@Table(name = "insurance_cards")
public class InsuranceCard implements Serializable {
	@Id
	@Column(name = "card_number")
	private String cardNumber;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "policy_owner_id", referencedColumnName = "customer_id")
	private PolicyOwner policyOwner;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "card_holder_id", referencedColumnName = "customer_id")
	private Beneficiary cardHolder;
	
	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	
	public InsuranceCard() {
	}
	
	public InsuranceCard(String cardNumber) {
		this.cardNumber = cardNumber;
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
	
	public Beneficiary getCardHolder() {
		return cardHolder;
	}
	
	public void setCardHolder(Beneficiary cardHolder) {
		this.cardHolder = cardHolder;
	}
	
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
}
