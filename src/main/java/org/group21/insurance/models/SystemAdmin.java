package org.group21.insurance.models;

public class SystemAdmin {
  private static SystemAdmin instance = null;

  private SystemAdmin() {}

  public static SystemAdmin getInstance() {
    if (instance == null) {
      instance = new SystemAdmin();
    }
    return instance;
  }
}
