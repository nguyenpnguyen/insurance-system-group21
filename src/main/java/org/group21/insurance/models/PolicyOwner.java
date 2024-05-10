package org.group21.insurance.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PolicyOwner extends Customer implements Serializable {
  
  @OneToMany(mappedBy = "policyOwner", cascade = CascadeType.ALL)
  private List<Beneficiary> beneficiaries;

  public PolicyOwner() {
    super();
    this.beneficiaries = new ArrayList<>();
  }

  public PolicyOwner(List<Beneficiary> beneficiaries) {
    super();
    this.beneficiaries = beneficiaries;
  }
  
  public List<Beneficiary> getBeneficiaries() {
    return beneficiaries;
  }
  
  public void setBeneficiaries(List<Beneficiary> beneficiaries) {
    this.beneficiaries = beneficiaries;
  }
}
