package org.group21.insurance.models;

public class Dependent extends Customer {
  private PolicyHolder policyHolder;
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
