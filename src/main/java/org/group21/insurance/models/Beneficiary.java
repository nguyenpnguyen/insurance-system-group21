package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "Beneficiary")
@Table(name = "beneficiaries")
public class Beneficiary extends Customer implements Serializable {
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "insurance_card_number", referencedColumnName = "card_number")
	private InsuranceCard insuranceCard;
	
	@ManyToOne
	@JoinColumn(name = "policy_holder_id", referencedColumnName = "customer_id")
	private Beneficiary policyHolder;
	
	@OneToMany(mappedBy = "policyHolder", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Beneficiary> dependents;
	
	@OneToMany(mappedBy = "insuredPerson", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Claim> claims;
	
	@ManyToOne
	@JoinColumn(name = "policy_owner_id", referencedColumnName = "customer_id")
	private PolicyOwner policyOwner;
	
	@Column(name = "is_policy_holder", nullable = false)
	private boolean isPolicyHolder;
	
	public Beneficiary() {
	}

	public Beneficiary(String id, String username, String hashedPassword, boolean isPolicyHolder) {
			super(id, username, hashedPassword);
			this.isPolicyHolder = isPolicyHolder;
	}
	
	public InsuranceCard getInsuranceCard() {
		return insuranceCard;
	}
	
	public void setInsuranceCard(InsuranceCard insuranceCard) {
		this.insuranceCard = insuranceCard;
	}
	
	public Beneficiary getPolicyHolder() {
		if (isPolicyHolder) {
			return null;
		}
		return policyHolder;
	}
	
	public void setPolicyHolder(Beneficiary policyHolder) {
		if (!isPolicyHolder) {
			this.policyHolder = policyHolder;
		}
	}
	
	public List<Beneficiary> getDependents() {
		if (!isPolicyHolder) {
			return null;
		}
		return dependents;
	}
	
	public void setDependents(List<Beneficiary> dependents) {
		if (isPolicyHolder) {
			this.dependents = dependents;
		}
	}
	
	public List<Claim> getClaims() {
		return claims;
	}
	
	public void setClaims(List<Claim> claims) {
		this.claims = claims;
	}
	
	public PolicyOwner getPolicyOwner() {
		return policyOwner;
	}
	
	public void setPolicyOwner(PolicyOwner policyOwner) {
		this.policyOwner = policyOwner;
	}
	
	public boolean isPolicyHolder() {
		return isPolicyHolder;
	}
	
	public void setIsPolicyHolder(boolean policyHolder) {
		isPolicyHolder = policyHolder;
	}
}
