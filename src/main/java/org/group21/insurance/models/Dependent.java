package org.group21.insurance.Models;

import java.util.List;

public class Dependent extends Customer {
  private List<Claim> claims;

  public Dependent() {
    super();
    this.claims = null;
  }

  public Dependent(PolicyHolder policyHolder, List<Claim> claims) {
    super();
    this.claims = claims;
  }
}
