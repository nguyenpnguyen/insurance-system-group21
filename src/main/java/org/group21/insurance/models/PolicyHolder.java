package org.group21.insurance.models;

import java.util.ArrayList;
import java.util.List;

public class PolicyHolder extends Customer {
  private List<Dependent> dependents;

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
