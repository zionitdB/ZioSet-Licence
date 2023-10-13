package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>,EmployeeCustomeRepo {
	@Query("From Employee e where e.employeeNo=?1")
	Optional<Employee> getEmployeeByNo(String employeeNo);
	@Query("From Employee e where e.branch.branchName=?1")
	List<Employee> getAllEmployeesByBranch(String branchName);

	

}
