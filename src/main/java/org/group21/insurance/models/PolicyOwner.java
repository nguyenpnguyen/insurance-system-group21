package org.group21.insurance.models;

/**
 * @author Group 21
 */

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "PolicyOwner")
@Table(name = "policy_owners")
public class PolicyOwner extends Customer implements Serializable {
	
	@OneToMany(mappedBy = "policyOwner", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private List<Beneficiary> beneficiaries;
	
	public PolicyOwner() {
	}
	
	public PolicyOwner(String id, String username, String hashedPassword, List<Beneficiary> beneficiaries) {
		super(id, username, hashedPassword);
		this.beneficiaries = beneficiaries;
	}
	
	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}
	
	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
}
