package org.group21.insurance.models;

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
	
	public SystemAdmin() {}
	
	public SystemAdmin(String username, String hashedPassword) {
		this.username = username;
		this.hashedPassword = hashedPassword;
	}
	
	public long getSysAdminId() {
		return sysAdminId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SystemAdmin that = (SystemAdmin) o;
		return getSysAdminId() == that.getSysAdminId() && Objects.equals(getUsername(), that.getUsername());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getSysAdminId(), getUsername());
	}
}
