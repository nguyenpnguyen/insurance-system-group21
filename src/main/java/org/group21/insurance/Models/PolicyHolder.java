package org.group21.insurance.Models;

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
}
