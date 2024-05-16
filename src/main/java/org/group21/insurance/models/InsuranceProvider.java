package org.group21.insurance.models;

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
	
	public InsuranceProvider() {
	}
	
	public InsuranceProvider(String insuranceProviderId, String username, String hashedPassword, boolean isInsuranceManager) {
		this.insuranceProviderId = insuranceProviderId;
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.isInsuranceManager = isInsuranceManager;
	}
	
	public String getInsuranceProviderId() {
		return insuranceProviderId;
	}
	
	public void setInsuranceProviderId(String insuranceProviderId) {
		this.insuranceProviderId = insuranceProviderId;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		InsuranceProvider that = (InsuranceProvider) o;
		return Objects.equals(getInsuranceProviderId(), that.getInsuranceProviderId()) && Objects.equals(getUsername(), that.getUsername());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getInsuranceProviderId(), getUsername());
	}
}