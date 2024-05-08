package org.group21.insurance.models;

public class InsuranceProvider {
	private String id;
	private String username;
	private String hashedPassword;
	private String fullName;
	private String phoneNumber;
	private String email;
	private InsuranceProviderRole role;
	
	public InsuranceProvider() {
		this.id = "";
		this.username = "";
		this.hashedPassword = "";
		this.fullName = "";
		this.phoneNumber = "";
		this.email = "";
		this.role = InsuranceProviderRole.INSURANCE_MANAGER;
	}
	
	public InsuranceProvider(String id, String username, String hashedPassword, String fullName, String phoneNumber, String email, InsuranceProviderRole role) {
		this.id = id;
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.role = role;
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
	
	public InsuranceProviderRole getRole() {
		return role;
	}
	
	public void setRole(InsuranceProviderRole role) {
		this.role = role;
	}
	
	public enum InsuranceProviderRole {
		INSURANCE_MANAGER,
		INSURANCE_SURVEYOR
	}
}