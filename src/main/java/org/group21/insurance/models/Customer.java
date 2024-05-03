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
    this.username = "";
    this.password = "";
    this.fullName = "";
    this.phoneNumber = "";
    this.address = "";
    this.email = "";
  }
  
/**
   * Constructor for <code>Customer</code> class
   *
   * @param id Customer's ID
   * @param username Customer's username
   * @param password Customer's hashed password
   * @param fullName Customer's name
   * @param phoneNumber Customer's phone number
   * @param address Customer's address
   * @param email Customer's email
   */
  public Customer(String id, String username, String password, String fullName, String phoneNumber, String address, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.fullName = fullName;
    this.phoneNumber = phoneNumber;
    this.address = address;
    this.email = email;
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getFullName() {
    return fullName;
  }
  
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
}
