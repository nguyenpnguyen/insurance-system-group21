package org.group21.insurance.models;

public abstract class InsuranceProvider {
	private String id;
	private String username;
	private String password;
	private String fullName;
	private String email;
	private String phoneNumber;
	private InsuranceProviderRole role;

	public InsuranceProvider() {
		this.id = "";
		this.username = "";
		this.password = "";
		this.fullName = "";
		this.email = "";
		this.phoneNumber = "";
		this.role = null;
	}
	
	public InsuranceProvider(String id, String username, String password, String fullName, String email, String phoneNumber, InsuranceProviderRole role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
