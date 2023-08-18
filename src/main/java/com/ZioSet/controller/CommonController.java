package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Repo.BranchRepo;
import com.ZioSet.Repo.DepartmentRepo;
import com.ZioSet.dto.EmailDetials;
import com.ZioSet.dto.ResponceObject;
import com.ZioSet.model.Asset;
import com.ZioSet.model.Branch;
import com.ZioSet.model.Department;

@RestController
@CrossOrigin("*")
@RequestMapping("common")
public class CommonController {
	@Autowired
	BranchRepo branchRepo;
	@Autowired
	DepartmentRepo departmentRepo;
	
	@RequestMapping(value = "/getAllBranches", method = RequestMethod.GET)
	public @ResponseBody List<Branch> getAllAsset() {
		List<Branch> list= new ArrayList<Branch>();
		try {
			list=branchRepo.findAll();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllDepartments", method = RequestMethod.GET)
	public @ResponseBody List<Department> getAllDepartments() {
		List<Department> list= new ArrayList<Department>();
		try {
			list=departmentRepo.findAll();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/saveBranch", method = RequestMethod.POST)

	public @ResponseBody ResponceObject saveBranch(@RequestBody Branch branch) {
		ResponceObject status =new ResponceObject();
		try {
			
			
			branchRepo.save(branch);
			status.setCode(200);
			status.setMessage("Branch added Succesfully");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
		


}
