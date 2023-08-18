package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	@Query("From Role r where r.roleName=?1")
	Optional<Role> getRoleByName(String roleName);

}
