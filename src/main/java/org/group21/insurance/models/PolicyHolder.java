package org.group21.insurance.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PolicyHolder")
public class PolicyHolder extends Customer {
  @OneToMany(mappedBy = "policyHolder", cascade = CascadeType.ALL)
  private List<Dependent> dependents;

  @ManyToOne
  @JoinColumn(name = "policy_owner_id", nullable = false)
  private PolicyOwner policyOwner;

  public PolicyHolder() {
    super();
    this.dependents = new ArrayList<>();
    this.policyOwner = new PolicyOwner();
  }

  public PolicyHolder(List<Dependent> dependents, PolicyOwner policyOwner) {
    super();
    this.dependents = dependents;
    this.policyOwner = policyOwner;
  }
  
  public List<Dependent> getDependents() {
    return dependents;
  }
  
  public void setDependents(List<Dependent> dependents) {
    this.dependents = dependents;
  }
  
  public PolicyOwner getPolicyOwner() {
    return policyOwner;
  }
  
  public void setPolicyOwner(PolicyOwner policyOwner) {
    this.policyOwner = policyOwner;
  }
}
