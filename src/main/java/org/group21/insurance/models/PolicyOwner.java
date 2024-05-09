package org.group21.insurance.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PolicyOwner")
public class PolicyOwner extends Customer {
  
  @OneToMany(mappedBy = "PolicyOwner", cascade = CascadeType.ALL)
  private List<Customer> beneficiaries;

  public PolicyOwner() {
    super();
    this.beneficiaries = new ArrayList<>();
  }

  public PolicyOwner(List<Customer> beneficiaries) {
    super();
    this.beneficiaries = beneficiaries;
  }
  
  public List<Customer> getBeneficiaries() {
    return beneficiaries;
  }
  
  public void setBeneficiaries(List<Customer> beneficiaries) {
    this.beneficiaries = beneficiaries;
  }
}
