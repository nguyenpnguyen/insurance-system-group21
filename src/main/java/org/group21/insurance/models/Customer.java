package org.group21.insurance.models;

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
	
	public Customer() {
	}
	
	public Customer(String customerId, String username, String hashedPassword) {
		this.customerId = customerId;
		this.username = username;
		this.hashedPassword = hashedPassword;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(getCustomerId(), customer.getCustomerId()) && Objects.equals(getUsername(), customer.getUsername());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getCustomerId(), getUsername());
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Customer ID: ");
		sb.append(customerId).append(",").append('\n');
		sb.append("Customer name: ").append(fullName);
		return sb.toString();
	}
}
