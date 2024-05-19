package org.group21.insurance.models;

/**
 * This abstract class represents a customer in the Insurance Claim Management System.
 * It includes information such as customer ID, username, hashed password, full name, phone number, address, and email.
 * This class is extended by other classes to represent specific types of customers.
 *
 * @author Group 21
 */

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Customer implements Serializable {
	@Id
	@GeneratedValue(generator = "customer-id-generator")
	@GenericGenerator(name = "customer-id-generator",
			strategy = "org.group21.insurance.utils.IdGenerator",
			parameters = {
					@Parameter(name = "prefix", value = "c"),
					@Parameter(name = "length", value = "7")
			})
	@Column(name = "customer_id", nullable = false)
	private String customerId;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String hashedPassword;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	/**
	 * Default constructor for the Customer class.
	 */
	public Customer() {
	}

	/**
	 * Constructor for the Customer class.
	 *
	 * @param customerId The ID of the customer.
	 * @param username The username of the customer.
	 * @param hashedPassword The hashed password of the customer.
	 */
	public Customer(String customerId, String username, String hashedPassword) {
		this.customerId = customerId;
		this.username = username;
		this.hashedPassword = hashedPassword;
	}

	/**
	 * This method returns the customer ID.
	 *
	 * @return The customer ID.
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * This method sets the customer ID.
	 *
	 * @param customerId The customer ID.
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * This method returns the username of the customer.
	 *
	 * @return The username of the customer.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method sets the username of the customer.
	 *
	 * @param username The username of the customer.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method returns the hashed password of the customer.
	 *
	 * @return The hashed password of the customer.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * This method sets the hashed password of the customer.
	 *
	 * @param hashedPassword The hashed password of the customer.
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * This method returns the full name of the customer.
	 *
	 * @return The full name of the customer.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * This method sets the full name of the customer.
	 *
	 * @param fullName The full name of the customer.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * This method returns the phone number of the customer.
	 *
	 * @return The phone number of the customer.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * This method sets the phone number of the customer.
	 *
	 * @param phoneNumber The phone number of the customer.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * This method returns the address of the customer.
	 *
	 * @return The address of the customer.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * This method sets the address of the customer.
	 *
	 * @param address The address of the customer.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * This method returns the email of the customer.
	 *
	 * @return The email of the customer.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method sets the email of the customer.
	 *
	 * @param email The email of the customer.
	 */
	public void setEmail(String email) {
		this.email = email;
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
		Customer customer = (Customer) o;
		return Objects.equals(getCustomerId(), customer.getCustomerId()) && Objects.equals(getUsername(), customer.getUsername());
	}

	/**
	 * This method returns the hash code of the current object.
	 *
	 * @return The hash code of the current object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getCustomerId(), getUsername());
	}

	/**
	 * This method returns a string representation of the current object.
	 *
	 * @return A string representation of the current object.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Customer ID: ");
		sb.append(customerId).append(",").append('\n');
		sb.append("Customer name: ").append(fullName);
		return sb.toString();
	}
}