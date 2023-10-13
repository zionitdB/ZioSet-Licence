package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.Repo.AssetEmployeeAssignedRepo;
import com.ZioSet.Repo.EmployeeRepo;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.Employee;

@Service
public class EmployeeServicesImpl implements EmployeeServices {
	@Autowired
	EmployeeRepo employeeRepo;
	@Autowired
	AssetEmployeeAssignedRepo assetEmployeeAssignedRepo;

	@Override
	public void addNewEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employeeRepo.save(employee);
	}

	@Override
	public Optional<Employee> getEmployeeByNo(String employeeNo) {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeByNo(employeeNo);
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return employeeRepo.findAll();
	}

	@Override
	public List<Employee> getEmployeeByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeByLimit(page_no,item_per_page);
	}

	@Override
	public List<Employee> getEmployeeByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getEmployeeCount() {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeCount();
	}

	@Override
	public int getEmployeeCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeCountAndSearch(searchText);
	}

	@Override
	public List<AssetEmployeeAssigned> getEmployeeWiseAllocationReport(int employeeId) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getEmployeeWiseAllocationReport(employeeId);
	}

	@Override
	public void deleteEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employeeRepo.delete(employee);
	}

	@Override
	public List<Employee> getAllEmployeesByBranch(String branchName) {
		// TODO Auto-generated method stub
		return employeeRepo.getAllEmployeesByBranch(branchName);
	}

}
