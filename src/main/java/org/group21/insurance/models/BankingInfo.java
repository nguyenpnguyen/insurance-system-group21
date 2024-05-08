package org.group21.insurance.models;

public class BankingInfo {
	private String id;
	private String name;
	private String bank;
	private String accountNumber;
	
	public BankingInfo() {
		this.id = "";
		this.name = "";
		this.bank = "";
		this.accountNumber = "";
	}
	
	public BankingInfo(String id, String name, String bank, String accountNumber) {
		this.id = id;
		this.name = name;
		this.bank = bank;
		this.accountNumber = accountNumber;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
