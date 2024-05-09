package org.group21.insurance.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("Dependent")
public class Dependent extends Customer {
  @ManyToOne
  @JoinColumn(name = "policy_holder_id", referencedColumnName = "id", nullable = false)
  private PolicyHolder policyHolder;
  
  @ManyToOne
  @JoinColumn(name = "policy_owner_id", referencedColumnName = "id", nullable = false)
  private PolicyOwner policyOwner;

  public Dependent() {
    super();
    this.policyHolder = new PolicyHolder();
    this.policyOwner = new PolicyOwner();
  }
  
  public Dependent(PolicyHolder policyHolder, PolicyOwner policyOwner) {
    super();
    this.policyHolder = policyHolder;
    this.policyOwner = policyOwner;
  }
  
  public PolicyHolder getPolicyHolder() {
    return policyHolder;
  }
  
  public void setPolicyHolder(PolicyHolder policyHolder) {
    this.policyHolder = policyHolder;
  }
  
  public PolicyOwner getPolicyOwner() {
    return policyOwner;
  }
  
  public void setPolicyOwner(PolicyOwner policyOwner) {
    this.policyOwner = policyOwner;
  }
}
