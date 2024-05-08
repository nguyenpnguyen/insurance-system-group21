package org.group21.insurance.models;

public abstract class Customer {
	private String id;
	private String username;
	private String hashedPassword;
	private String fullName;
	private InsuranceCard insuranceCard;
	private String phoneNumber;
	private String address;
	private String email;
	
	public Customer() {
		this.id = "";
		this.username = "";
		this.hashedPassword = "";
		this.fullName = "";
		this.insuranceCard = new InsuranceCard();
		this.phoneNumber = "";
		this.address = "";
		this.email = "";
	}
	
	public Customer(String id, String username, String hashedPassword, String fullName, InsuranceCard insuranceCard, String phoneNumber, String address, String email) {
		this.id = id;
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.fullName = fullName;
		this.insuranceCard = insuranceCard;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.email = email;
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
	
	public InsuranceCard getInsuranceCard() {
		return insuranceCard;
	}
	
	public void setInsuranceCard(InsuranceCard insuranceCard) {
		this.insuranceCard = insuranceCard;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
