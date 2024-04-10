package org.group21.insurance.models;

/**
 * Represents customers of the insurance company
 *
 * @author Group 21 - Semester 1 - 2024
 */
public abstract class Customer {
  /** Customer's ID */
  private String id;

  /** Customer's username */
  private String username;

  /** Customer's hashed password */
  private String password;

  /** Customer's name */
  private String fullName;

  private String phoneNumber;

  private String address;

  private String email;

  /** Default constructor for <code>Customer</code> class */
  public Customer() {
    this.id = "";
    this.fullName = "";
  }

  /**
   * Constructor for Customer
   *
   * @param id user ID
   * @param fullName user full name
   */
  public Customer(String id, String fullName) {
    this.id = id;
    this.fullName = fullName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
