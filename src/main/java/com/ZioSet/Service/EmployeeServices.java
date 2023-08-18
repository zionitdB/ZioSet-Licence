package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import com.ZioSet.model.Employee;

public interface EmployeeServices {

	void addNewEmployee(Employee employee);

	Optional<Employee> getEmployeeByNo(String employeeNo);

	List<Employee> getAllEmployees();

	List<Employee> getEmployeeByLimit(int page_no, int item_per_page);

	List<Employee> getEmployeeByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getEmployeeCount();

	int getEmployeeCountAndSearch(String searchText);


}
