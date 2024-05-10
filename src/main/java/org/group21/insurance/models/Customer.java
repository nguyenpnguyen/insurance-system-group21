package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Customer implements Serializable {
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
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String email;
	
	public Customer() {
		this.id = "";
		this.username = "";
		this.hashedPassword = "";
		this.fullName = "";
		this.phoneNumber = "";
		this.address = "";
		this.email = "";
	}
	
	public Customer(String id, String username, String hashedPassword, String fullName, String phoneNumber, String address, String email) {
		this.id = id;
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.fullName = fullName;
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
