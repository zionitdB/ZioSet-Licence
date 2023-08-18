package com.ZioSet.dto;

public class PermissionDTO {
private String permissionName;
private boolean permissionAvailable;
private boolean  editTab ;
public String getPermissionName() {
	return permissionName;
}
public void setPermissionName(String permissionName) {
	this.permissionName = permissionName;
}
public boolean isPermissionAvailable() {
	return permissionAvailable;
}
public void setPermissionAvailable(boolean permissionAvailable) {
	this.permissionAvailable = permissionAvailable;
}
public boolean isEditTab() {
	return editTab;
}
public void setEditTab(boolean editTab) {
	this.editTab = editTab;
}

}
