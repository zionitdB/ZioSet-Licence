package com.ZioSet.dto;

import java.util.List;

import com.ZioSet.model.Role;



public class RolePermissionDto {
	private Role role;
	List<PermissionDTO> permissions;
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<PermissionDTO> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

}
