package org.group21.insurance.models;

import java.time.LocalDate;

public class InsuranceCard {
	private String cardNumber;
	private PolicyOwner policyOwner;
	private Customer cardHolder;
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
