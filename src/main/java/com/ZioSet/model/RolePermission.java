package com.ZioSet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role_permissions")
public class RolePermission {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_permissions_id")
	private int rolePermissionsId;
	
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="permissions_id")
	private Permissions permissions;

	public int getRolePermissionsId() {
		return rolePermissionsId;
	}

	public void setRolePermissionsId(int rolePermissionsId) {
		this.rolePermissionsId = rolePermissionsId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permissions getPermissions() {
		return permissions;
	}

	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}
	
	
	
	
	
	
	

}
