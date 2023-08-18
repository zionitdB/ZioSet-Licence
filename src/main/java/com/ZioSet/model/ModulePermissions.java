package com.ZioSet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="module_permission")
public class ModulePermissions {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="permission_id")
	private int permissionId;
	
	@Column(name="permission_module")
	private String permissionModule;

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionModule() {
		return permissionModule;
	}

	public void setPermissionModule(String permissionModule) {
		this.permissionModule = permissionModule;
	}

	
	
		
}
