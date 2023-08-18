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
@Table(name="permissions_action")
public class PermissionAction {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permissions_action_id")
	private int permissionAsactionId;
	
	
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="permissions_id")
	private Permissions permissions;
	
	@Column(name="action_name")
	private String actionName;
	
	
	@Column(name="is_available")
	private boolean isAvailable;


	public int getPermissionAsactionId() {
		return permissionAsactionId;
	}


	public void setPermissionAsactionId(int permissionAsactionId) {
		this.permissionAsactionId = permissionAsactionId;
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


	public String getActionName() {
		return actionName;
	}


	public void setActionName(String actionName) {
		this.actionName = actionName;
	}


	public boolean isAvailable() {
		return isAvailable;
	}


	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	
	
	

}
