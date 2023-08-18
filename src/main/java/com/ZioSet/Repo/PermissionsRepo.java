package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Permissions;

public interface PermissionsRepo extends JpaRepository<Permissions, Integer> ,PermissionsCustomeRepo{
	@Query("From Permissions p where p.permissionsName=?1")
	Optional<Permissions> getPermissionsByName(String permissionsName);
	@Query("From Permissions p where p.category=?1 and p.permissionsName=?2")
	Optional<Permissions> getPermissionsByNameAndCategory(String category, String permissionsName);

}
