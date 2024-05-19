package org.group21.insurance.models;

/**
 * @author Group 21
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "banking_infos")
public class BankingInfo implements Serializable {
	@Id
	@Column(name = "account_number", nullable = false)
	private String accountNumber;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "bank", nullable = false)
	private String bank;
	
	public BankingInfo() {
	}
	
	public BankingInfo(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BankingInfo that = (BankingInfo) o;
		return Objects.equals(getAccountNumber(), that.getAccountNumber());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getAccountNumber());
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Account number:'").append(accountNumber).append(",").append("\n");
		sb.append("Name='").append(name).append(",").append("\n");
		sb.append("Bank='").append(bank).append(",").append("\n");
		return sb.toString();
	}
}
