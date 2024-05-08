package org.group21.insurance.models;

public class PolicyOwner extends Customer {
  private Beneficiaries beneficiaries;

  public PolicyOwner() {
    super();
    this.beneficiaries = new Beneficiaries();
  }

  public PolicyOwner(Beneficiaries beneficiaries) {
    super();
    this.beneficiaries = beneficiaries;
  }
  
  public Beneficiaries getBeneficiaries() {
    return beneficiaries;
  }
  
  public void setBeneficiaries(Beneficiaries beneficiaries) {
    this.beneficiaries = beneficiaries;
  }
}
