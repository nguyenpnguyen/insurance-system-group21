package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "InsuranceProvider")
@Table(name = "insurance_providers")
public class InsuranceProvider implements Serializable {
	@Id
	@Column(name = "id", nullable = false)
	private String id;
	
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
	
	public InsuranceProvider() {
		this.id = "";
		this.username = "";
		this.hashedPassword = "";
		this.fullName = "";
		this.phoneNumber = "";
		this.email = "";
		this.isInsuranceManager = false;
	}
	
	public InsuranceProvider(String id, String username, String hashedPassword, String fullName, String phoneNumber, String email, boolean isInsuranceManager) {
		this.id = id;
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.isInsuranceManager = isInsuranceManager;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isInsuranceManager() {
		return isInsuranceManager;
	}
	
	public void setInsuranceManager(boolean insuranceManager) {
		isInsuranceManager = insuranceManager;
	}
}