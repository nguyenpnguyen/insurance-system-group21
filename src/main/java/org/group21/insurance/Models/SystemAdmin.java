package org.group21.insurance.Models;

public class SystemAdmin {
  private SystemAdmin systemAdmin;

  private SystemAdmin() {}

  public SystemAdmin getSystemAdmin() {
    if (systemAdmin == null) {
      systemAdmin = new SystemAdmin();
    }
    return systemAdmin;
  }
}
