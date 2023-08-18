package com.ZioSet.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
	@Query("From Department d where d.departmentName=?1")
	Optional<Department> getDepartmentByName(String depName);
	@Query("SELECT MAX(departmentId) AS maxId FROM Department")
	int getMaxDepartmentId();

}
