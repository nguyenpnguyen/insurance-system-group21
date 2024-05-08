package org.group21.insurance.models;

import java.util.ArrayList;
import java.util.List;

public class Beneficiaries {
	private PolicyOwner policyOwner;
	private List<Customer> beneficiaries;
	
	public Beneficiaries() {
		this.policyOwner = new PolicyOwner();
		this.beneficiaries = new ArrayList<>();
	}
	
	public Beneficiaries(PolicyOwner policyOwner, List<Customer> beneficiaries) {
		this.policyOwner = policyOwner;
		this.beneficiaries = beneficiaries;
	}
	
	public PolicyOwner getPolicyOwner() {
		return policyOwner;
	}
	
	public void setPolicyOwner(PolicyOwner policyOwner) {
		this.policyOwner = policyOwner;
	}
	
	public List<Customer> getBeneficiaries() {
		return beneficiaries;
	}
	
	public void setBeneficiaries(List<Customer> beneficiaries) {
		this.beneficiaries = beneficiaries;
	}
}
