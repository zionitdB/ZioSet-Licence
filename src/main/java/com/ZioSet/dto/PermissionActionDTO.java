package com.ZioSet.dto;

import java.util.List;

import com.ZioSet.model.PermissionAction;
import com.ZioSet.model.Permissions;
import com.ZioSet.model.Role;

public class PermissionActionDTO {
	private Role role;
	private Permissions permissions;
	List<PermissionAction> actions;
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
	public List<PermissionAction> getActions() {
		return actions;
	}
	public void setActions(List<PermissionAction> actions) {
		this.actions = actions;
	}
	
	
	

}
