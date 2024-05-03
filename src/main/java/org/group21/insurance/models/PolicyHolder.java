package org.group21.insurance.models;

import java.util.List;

public class PolicyHolder extends Customer {
  private List<Dependent> dependents;

  private List<Claim> claims;

  public PolicyHolder() {
    super();
    this.dependents = null;
    this.claims = null;
  }

  public PolicyHolder(List<Dependent> dependents, List<Claim> claims) {
    super();
    this.dependents = dependents;
    this.claims = claims;
  }
  
  public List<Dependent> getDependents() {
    return dependents;
  }
  
  public void setDependents(List<Dependent> dependents) {
    this.dependents = dependents;
  }
  
  public List<Claim> getClaims() {
    return claims;
  }
  
  public void setClaims(List<Claim> claims) {
    this.claims = claims;
  }
}
