package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import com.ZioSet.model.PermissionAction;
import com.ZioSet.model.Permissions;
import com.ZioSet.model.Role;
import com.ZioSet.model.RolePermission;

public interface AccessService {

	Optional<Permissions> getPermissionsByName(String permissionsName);

	void addPermission(Permissions permissions);

	List<Permissions> getPermissionsByPagination(int page_no, int item_per_page);

	List<Permissions> getPermissionsByPaginationAndSerach(int page_no, int item_per_page, String search);

	int getPermissionsCount();

	int getPermissionsCountBySearch(String search);

	void deletePermission(Permissions permissions);

	List<Permissions> getAllPermission();

	Optional<RolePermission> getRolePermissionByRoleAndPermission(int roleId, int permissionsId);

	void saveRolePermission(RolePermission rolepermission);

	void deleteRolePermission(RolePermission rolePermission);

	List<RolePermission> getPermissionsByRole(int roleId);

	Optional<Permissions> getPermissionsByNameAndCategory(String category, String permissionsName);

	Optional<Role> getRoleByName(String roleName);

	void addRole(Role role);

	void savePermissionAction(PermissionAction action);

	List<PermissionAction> getPermissionActionByRoleAndPermission(int roleId, int permissionsId);

	void deleteAllPermissionAction(List<PermissionAction> permissionActions);

}
