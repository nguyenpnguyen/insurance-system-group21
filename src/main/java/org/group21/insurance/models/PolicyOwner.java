package org.group21.insurance.models;

import java.util.List;

public class PolicyOwner extends Customer {
  private final List<Customer> beneficiaries;

  public PolicyOwner() {
    this.beneficiaries = null;
  }

  public PolicyOwner(List<Customer> beneficiaries) {
    this.beneficiaries = beneficiaries;
  }
}
