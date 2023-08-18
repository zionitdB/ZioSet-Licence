package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Permissions;
import com.ZioSet.model.RolePermission;

public interface RolePermissionRepo extends JpaRepository<RolePermission, Integer>{
	@Query("from  RolePermission p where p.role.roleId=?1 and p.permissions.permissionsId=?2")
	Optional<RolePermission> getRolePermissionByRoleAndPermission(int roleId, int permissionsId);
	@Query("from  RolePermission p where p.role.roleId=?1")
	List<RolePermission> getPermissionsByRole(int roleId);

}
