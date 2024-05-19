package org.group21.insurance.models;

/**
 * This class represents a system administrator in the Insurance Claim Management System.
 * It includes information such as system administrator ID, username, and hashed password.
 * The system administrator has the highest level of access and can manage all aspects of the system.
 *
 * @author Group 21
 */

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity(name = "SystemAdmin")
@Table(name = "system_admin")
public class SystemAdmin implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_admin_seq")
	@SequenceGenerator(name = "system_admin_seq", sequenceName = "system_admin_seq", allocationSize = 1)
	@Column(name = "sys_admin_id", nullable = false)
	private long sysAdminId;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String hashedPassword;

	/**
	 * Default constructor for the SystemAdmin class.
	 */
	public SystemAdmin() {}

	/**
	 * Constructor for the SystemAdmin class.
	 *
	 * @param username The username of the system administrator.
	 * @param hashedPassword The hashed password of the system administrator.
	 */
	public SystemAdmin(String username, String hashedPassword) {
		this.username = username;
		this.hashedPassword = hashedPassword;
	}

	/**
	 * This method returns the system administrator ID.
	 *
	 * @return The system administrator ID.
	 */
	public long getSysAdminId() {
		return sysAdminId;
	}

	/**
	 * This method returns the username of the system administrator.
	 *
	 * @return The username of the system administrator.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method sets the username of the system administrator.
	 *
	 * @param username The username of the system administrator.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method returns the hashed password of the system administrator.
	 *
	 * @return The hashed password of the system administrator.
	 */
	public String getHashedPassword() {
		return hashedPassword;
	}

	/**
	 * This method sets the hashed password of the system administrator.
	 *
	 * @param hashedPassword The hashed password of the system administrator.
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	/**
	 * This method checks if the current object is equal to the given object.
	 *
	 * @param o The object to be compared with the current object.
	 * @return True if the objects are equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SystemAdmin that = (SystemAdmin) o;
		return getSysAdminId() == that.getSysAdminId() && Objects.equals(getUsername(), that.getUsername());
	}

	/**
	 * This method returns the hash code of the current object.
	 *
	 * @return The hash code of the current object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getSysAdminId(), getUsername());
	}

	/**
	 * This method returns a string representation of the current object.
	 *
	 * @return A string representation of the current object.
	 */
	@Override
	public String toString() {
		return "System Admin";
	}
}