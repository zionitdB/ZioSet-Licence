package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.Repo.PermissionActionRepo;
import com.ZioSet.Repo.PermissionsRepo;
import com.ZioSet.Repo.RolePermissionRepo;
import com.ZioSet.Repo.RoleRepo;
import com.ZioSet.model.PermissionAction;
import com.ZioSet.model.Permissions;
import com.ZioSet.model.Role;
import com.ZioSet.model.RolePermission;

@Service
public class AccessServiceImpl implements AccessService {
	
	@Autowired
	PermissionsRepo permissionsRepo;
	@Autowired
	RolePermissionRepo rolPermissionsRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	PermissionActionRepo permissionActionRepo;

	@Override
	public Optional<Permissions> getPermissionsByName(String permissionsName) {
		// TODO Auto-generated method stub
		return permissionsRepo.getPermissionsByName(permissionsName);
	}

	@Override
	public void addPermission(Permissions permissions) {
		// TODO Auto-generated method stub
		permissionsRepo.save(permissions);
	}

	@Override
	public List<Permissions> getPermissionsByPagination(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return permissionsRepo.getPermissionsByPagination(page_no,item_per_page);
	}

	@Override
	public List<Permissions> getPermissionsByPaginationAndSerach(int page_no, int item_per_page, String search) {
		// TODO Auto-generated method stub
		return permissionsRepo.getPermissionsByPaginationAndSerach(page_no,item_per_page,search);
	}

	@Override
	public int getPermissionsCount() {
		// TODO Auto-generated method stub
		return permissionsRepo.getPermissionsCount();
	}

	@Override
	public int getPermissionsCountBySearch(String search) {
		// TODO Auto-generated method stub
		return permissionsRepo.getPermissionsCountBySearch(search);
	}

	@Override
	public void deletePermission(Permissions permissions) {
		// TODO Auto-generated method stub
		permissionsRepo.delete(permissions);
	}

	@Override
	public List<Permissions> getAllPermission() {
		// TODO Auto-generated method stub
		return permissionsRepo.findAll();
	}

	@Override
	public Optional<RolePermission> getRolePermissionByRoleAndPermission(int roleId, int permissionsId) {
		// TODO Auto-generated method stub
		return rolPermissionsRepo.getRolePermissionByRoleAndPermission(roleId,permissionsId);
	}

	@Override
	public void saveRolePermission(RolePermission rolepermission) {
		// TODO Auto-generated method stub
		rolPermissionsRepo.save(rolepermission);
	}

	@Override
	public void deleteRolePermission(RolePermission rolePermission) {
		// TODO Auto-generated method stub
		rolPermissionsRepo.delete(rolePermission);
	}

	@Override
	public List<RolePermission> getPermissionsByRole(int roleId) {
		// TODO Auto-generated method stub
		return rolPermissionsRepo.getPermissionsByRole(roleId);
	}

	@Override
	public Optional<Permissions> getPermissionsByNameAndCategory(String category, String permissionsName) {
		// TODO Auto-generated method stub
		return permissionsRepo.getPermissionsByNameAndCategory(category,permissionsName);
	}

	@Override
	public Optional<Role> getRoleByName(String roleName) {
		// TODO Auto-generated method stub
		return roleRepo.getRoleByName(roleName);
	}

	@Override
	public void addRole(Role role) {
		// TODO Auto-generated method stub
		roleRepo.save(role);
	}

	@Override
	public void savePermissionAction(PermissionAction action) {
		// TODO Auto-generated method stub
		permissionActionRepo.save(action);
	}

	@Override
	public List<PermissionAction> getPermissionActionByRoleAndPermission(int roleId, int permissionsId) {
		// TODO Auto-generated method stub
		return permissionActionRepo.getPermissionActionByRoleAndPermission(roleId,permissionsId);
	}

	@Override
	public void deleteAllPermissionAction(List<PermissionAction> permissionActions) {
		// TODO Auto-generated method stub
		permissionActionRepo.deleteAll(permissionActions);
	}

}
