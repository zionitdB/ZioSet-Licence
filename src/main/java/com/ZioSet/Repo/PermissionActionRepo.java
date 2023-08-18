package com.ZioSet.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.PermissionAction;

public interface PermissionActionRepo extends JpaRepository<PermissionAction, Integer> {
	@Query("From PermissionAction p where p.role.roleId=?1 and p.permissions.permissionsId=?2")
	List<PermissionAction> getPermissionActionByRoleAndPermission(int roleId, int permissionsId);

}
