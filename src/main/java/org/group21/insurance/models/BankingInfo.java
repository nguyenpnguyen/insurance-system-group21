package org.group21.insurance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BankingInfo {
	@Id
	@Column(name = "account_number", nullable = false)
	private String accountNumber;
	
	@Column(name = "id", nullable = false)
	private String name;
	
	@Column(name = "bank", nullable = false)
	private String bank;
	
	public BankingInfo() {
		this.name = "";
		this.bank = "";
		this.accountNumber = "";
	}
	
	public BankingInfo(String name, String bank, String accountNumber) {
		this.name = name;
		this.bank = bank;
		this.accountNumber = accountNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBank() {
		return bank;
	}
	
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
}
