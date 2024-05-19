package org.group21.insurance.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * This class represents a policy owner in the Insurance Claim Management System.
 * It extends the Customer class and includes a list of beneficiaries.
 * Each policy owner can have multiple beneficiaries.
 *
 * @author Group 21
 */
@Entity(name = "PolicyOwner")
@Table(name = "policy_owners")
public class PolicyOwner extends Customer implements Serializable {

	@OneToMany(mappedBy = "policyOwner", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private List<Beneficiary> beneficiaries;

	/**
	 * Default constructor for the PolicyOwner class.
	 */
	public PolicyOwner() {
	}

	/**
	 * Constructor for the PolicyOwner class.
	 *
	 * @param id The ID of the policy owner.
	 * @param username The username of the policy owner.
	 * @param hashedPassword The hashed password of the policy owner.
	 * @param beneficiaries The list of beneficiaries of the policy owner.
	 */
	public PolicyOwner(String id, String username, String hashedPassword, List<Beneficiary> beneficiaries) {
		super(id, username, hashedPassword);
		this.beneficiaries = beneficiaries;
	}

	/**
	 * This method returns the list of beneficiaries of the policy owner.
	 *
	 * @return The list of beneficiaries of the policy owner.
	 */
	public List<Beneficiary> getBeneficiaries() {
		return beneficiaries;
	}

	/**
	 * This method sets the list of beneficiaries of the policy owner.
	 *
	 * @param beneficiaries The list of beneficiaries of the policy owner.
	 */
	public void setBeneficiaries(List<Beneficiary> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
}