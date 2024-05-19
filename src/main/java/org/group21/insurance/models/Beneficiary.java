package org.group21.insurance.models;

/**
 * This class represents a beneficiary in the Insurance Claim Management System.
 * It extends the Customer class and includes additional information such as insurance card, policy holder, dependents, claims, policy owner, insurance rate, and whether the beneficiary is a policy holder.
 *
 * @author Group 21
 */

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Beneficiary")
@Table(name = "beneficiaries")
public class Beneficiary extends Customer implements Serializable {

	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "insurance_card_number", referencedColumnName = "card_number")
	private InsuranceCard insuranceCard;

	@ManyToOne
	@JoinColumn(name = "policy_holder_id", referencedColumnName = "customer_id")
	private Beneficiary policyHolder;

	@OneToMany(mappedBy = "policyHolder", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private List<Beneficiary> dependents;

	@OneToMany(mappedBy = "insuredPerson", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private List<Claim> claims;

	@ManyToOne
	@JoinColumn(name = "policy_owner_id", referencedColumnName = "customer_id")
	private PolicyOwner policyOwner;

	@Column(name = "insurance_rate")
	private long insuranceRate;

	@Column(name = "is_policy_holder", nullable = false)
	private boolean isPolicyHolder;

	/**
	 * Default constructor for the Beneficiary class.
	 */
	public Beneficiary() {
		super();
	}

	/**
	 * Constructor for the Beneficiary class.
	 *
	 * @param id The ID of the beneficiary.
	 * @param username The username of the beneficiary.
	 * @param hashedPassword The hashed password of the beneficiary.
	 * @param isPolicyHolder Whether the beneficiary is a policy holder.
	 */
	public Beneficiary(String id, String username, String hashedPassword, boolean isPolicyHolder) {
		super(id, username, hashedPassword);
		this.isPolicyHolder = isPolicyHolder;
	}

	/**
	 * This method returns the insurance card of the beneficiary.
	 *
	 * @return The insurance card of the beneficiary.
	 */
	public InsuranceCard getInsuranceCard() {
		return insuranceCard;
	}

	/**
	 * This method sets the insurance card of the beneficiary.
	 *
	 * @param insuranceCard The insurance card of the beneficiary.
	 */
	public void setInsuranceCard(InsuranceCard insuranceCard) {
		this.insuranceCard = insuranceCard;
	}

	/**
	 * This method returns the policy holder of the beneficiary.
	 *
	 * @return The policy holder of the beneficiary.
	 */
	public Beneficiary getPolicyHolder() {
		if (isPolicyHolder) {
			return null;
		}
		return policyHolder;
	}

	/**
	 * This method sets the policy holder of the beneficiary.
	 *
	 * @param policyHolder The policy holder of the beneficiary.
	 */
	public void setPolicyHolder(Beneficiary policyHolder) {
		if (!isPolicyHolder) {
			this.policyHolder = policyHolder;
		}
	}

	/**
	 * This method returns the dependents of the beneficiary.
	 *
	 * @return The dependents of the beneficiary.
	 */
	public List<Beneficiary> getDependents() {
		if (!isPolicyHolder) {
			return null;
		}
		return dependents;
	}

	/**
	 * This method sets the dependents of the beneficiary.
	 *
	 * @param dependents The dependents of the beneficiary.
	 */
	public void setDependents(List<Beneficiary> dependents) {
		if (isPolicyHolder) {
			this.dependents = dependents;
		}
	}

	/**
	 * This method returns the claims of the beneficiary.
	 *
	 * @return The claims of the beneficiary.
	 */
	public List<Claim> getClaims() {
		return claims;
	}

	/**
	 * This method sets the claims of the beneficiary.
	 *
	 * @param claims The claims of the beneficiary.
	 */
	public void setClaims(List<Claim> claims) {
		this.claims = claims;
	}

	/**
	 * This method returns the policy owner of the beneficiary.
	 *
	 * @return The policy owner of the beneficiary.
	 */
	public PolicyOwner getPolicyOwner() {
		return policyOwner;
	}

	/**
	 * This method sets the policy owner of the beneficiary.
	 *
	 * @param policyOwner The policy owner of the beneficiary.
	 */
	public void setPolicyOwner(PolicyOwner policyOwner) {
		this.policyOwner = policyOwner;
	}

	/**
	 * This method returns whether the beneficiary is a policy holder.
	 *
	 * @return True if the beneficiary is a policy holder, false otherwise.
	 */
	public boolean isPolicyHolder() {
		return isPolicyHolder;
	}

	/**
	 * This method sets whether the beneficiary is a policy holder.
	 *
	 * @param policyHolder True if the beneficiary is a policy holder, false otherwise.
	 */
	public void setIsPolicyHolder(boolean policyHolder) {
		isPolicyHolder = policyHolder;
	}

	/**
	 * This method returns the insurance rate of the beneficiary.
	 *
	 * @return The insurance rate of the beneficiary.
	 */
	public long getInsuranceRate() {
		return insuranceRate;
	}

	/**
	 * This method sets the insurance rate of the beneficiary.
	 * If the beneficiary is not a policy holder, the insurance rate is set to 60% of the policy holder's insurance rate.
	 */
	public void setInsuranceRate() {
		// Insurance rate for dependents is 60% of the policy holder's insurance rate
		if (!isPolicyHolder) {
			this.insuranceRate = Math.round(getPolicyHolder().getInsuranceRate() * 0.6);
		}
	}

	/**
	 * This method sets the insurance rate of the beneficiary.
	 *
	 * @param insuranceRate The insurance rate of the beneficiary.
	 */
	public void setInsuranceRate(long insuranceRate) {
		if (isPolicyHolder) {
			this.insuranceRate = insuranceRate;
		}
	}
}