package com.ZioSet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permissions_mst")
public class Permissions {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permissions_id")
	private int permissionsId;
	
	@Column(name="category")
	private String category;
	
	@Column(name="permissions_name")
	private String permissionsName;
		
	@Column(name="active")
	private int active;

	public int getPermissionsId() {
		return permissionsId;
	}

	public void setPermissionsId(int permissionsId) {
		this.permissionsId = permissionsId;
	}

	public String getPermissionsName() {
		return permissionsName;
	}

	public void setPermissionsName(String permissionsName) {
		this.permissionsName = permissionsName;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
