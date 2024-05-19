package org.group21.insurance.models;

/**
 * This class represents the banking information of a user in the Insurance Claim Management System.
 * It includes the account number, name, and bank of the user.
 *
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

	/**
	 * Default constructor for the BankingInfo class.
	 */
	public BankingInfo() {
	}

	/**
	 * Constructor for the BankingInfo class.
	 *
	 * @param accountNumber The account number of the user.
	 */
	public BankingInfo(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * This method returns the account number of the user.
	 *
	 * @return The account number of the user.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * This method sets the account number of the user.
	 *
	 * @param accountNumber The account number of the user.
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * This method returns the name of the user.
	 *
	 * @return The name of the user.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of the user.
	 *
	 * @param name The name of the user.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method returns the bank of the user.
	 *
	 * @return The bank of the user.
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * This method sets the bank of the user.
	 *
	 * @param bank The bank of the user.
	 */
	public void setBank(String bank) {
		this.bank = bank;
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
		BankingInfo that = (BankingInfo) o;
		return Objects.equals(getAccountNumber(), that.getAccountNumber());
	}

	/**
	 * This method returns the hash code of the current object.
	 *
	 * @return The hash code of the current object.
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(getAccountNumber());
	}

	/**
	 * This method returns a string representation of the current object.
	 *
	 * @return A string representation of the current object.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Account number:'").append(accountNumber).append(",").append("\n");
		sb.append("Name='").append(name).append(",").append("\n");
		sb.append("Bank='").append(bank).append(",").append("\n");
		return sb.toString();
	}
}