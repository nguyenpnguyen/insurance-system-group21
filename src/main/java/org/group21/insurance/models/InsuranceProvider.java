package org.group21.insurance.models;

/**
 * This class represents an insurance provider in the Insurance Claim Management System.
 * It includes information such as insurance provider ID, username, hashed password, full name, phone number, email, and whether the provider is an insurance manager.
 *
 * @author Group 21
 */

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "InsuranceProvider")
@Table(name = "insurance_providers")
public class InsuranceProvider implements Serializable {
	@Id
	@GeneratedValue(generator = "insurance-provider-generator")
	@GenericGenerator(name = "insurance-provider-generator",
			strategy = "org.group21.insurance.utils.IdGenerator",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "prefix", value = "p"),
					@org.hibernate.annotations.Parameter(name = "length", value = "7")
			})
	@Column(name = "insurance_provider_id", nullable = false)
	private String insuranceProviderId;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String hashedPassword;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "is_insurance_manager", nullable = false)
	private boolean isInsuranceManager;

	/**
	 * Default constructor for the InsuranceProvider class.
	 */
	public InsuranceProvider() {
	}

	/**
	 * Constructor for the InsuranceProvider class.
	 *
	 * @param insuranceProviderId The ID of the insurance provider.
	 * @param username The username of the insurance provider.
	 * @param hashedPassword The hashed password of the insurance provider.
	 * @param isInsuranceManager Whether the provider is an insurance manager.
	 */
	public InsuranceProvider(String insuranceProviderId, String username, String hashedPassword, boolean isInsuranceManager) {
		this.insuranceProviderId = insuranceProviderId;
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.isInsuranceManager = isInsuranceManager;
	}

	/**
	 * This method returns the insurance provider ID.
	 *
	 * @return The insurance provider ID.
	 */
	public String getInsuranceProviderId() {
		return insuranceProviderId;
	}

	/**
	 * This method sets the insurance provider ID.
	 *
	 * @param insuranceProviderId The insurance provider ID.
	 */
	public void setInsuranceProviderId(String insuranceProviderId) {
		this.insuranceProviderId = insuranceProviderId;
	}

	/**
	 * This method returns the username of the insurance provider.
	 *
	 * @return The username of the insurance provider.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method sets the username of the insurance provider.
	 *
	 * @param username The username of the insurance provider.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method returns the hashed password of the insurance provider.
	 *
	 * @return The hashed password of the insurance provider.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * This method sets the hashed password of the insurance provider.
	 *
	 * @param hashedPassword The hashed password of the insurance provider.
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * This method returns the full name of the insurance provider.
	 *
	 * @return The full name of the insurance provider.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * This method sets the full name of the insurance provider.
	 *
	 * @param fullName The full name of the insurance provider.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * This method returns the phone number of the insurance provider.
	 *
	 * @return The phone number of the insurance provider.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * This method sets the phone number of the insurance provider.
	 *
	 * @param phoneNumber The phone number of the insurance provider.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * This method returns the email of the insurance provider.
	 *
	 * @return The email of the insurance provider.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method sets the email of the insurance provider.
	 *
	 * @param email The email of the insurance provider.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method returns whether the provider is an insurance manager.
	 *
	 * @return True if the provider is an insurance manager, false otherwise.
	 */
	public boolean isInsuranceManager() {
		return isInsuranceManager;
	}

	/**
	 * This method sets whether the provider is an insurance manager.
	 *
	 * @param insuranceManager True if the provider is an insurance manager, false otherwise.
	 */
	public void setInsuranceManager(boolean insuranceManager) {
		isInsuranceManager = insuranceManager;
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
		InsuranceProvider that = (InsuranceProvider) o;
		return Objects.equals(getInsuranceProviderId(), that.getInsuranceProviderId()) && Objects.equals(getUsername(), that.getUsername());
	}

	/**
	 * This method returns the hash code of the current object.
	 *
	 * @return The hash code of the current object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getInsuranceProviderId(), getUsername());
	}

	/**
	 * This method returns a string representation of the current object.
	 *
	 * @return A string representation of the current object.
	 */
	@Override
	public String toString() {
		if (this.isInsuranceManager) {
			return "Insurance Manager ID: " +
					insuranceProviderId + "," + "\n" +
					"Name: " + fullName;
		}
		return "Insurance Surveyor ID: " +
				insuranceProviderId + "," + "\n" +
				"Name: " + fullName;
	}
}