package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.model.CustomerSuppliedSoftware;
import com.ZioSet.Repo.CustomerSuppliedSoftwareRepo;

@Service
public class CustomerSuppliedSoftwareServiceImpl implements CustomerSuppliedSoftwareService {
	
	@Autowired
	CustomerSuppliedSoftwareRepo customerSuppliedSoftwareRepo;

	@Override
	public List<CustomerSuppliedSoftware> getAllCustomerSuppliedSoftwares() {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.findAll();
	}

	@Override
	public void addNewCustomerSuppliedSoftware(CustomerSuppliedSoftware customerSuppliedSoftware) {
		// TODO Auto-generated method stub
		customerSuppliedSoftwareRepo.save(customerSuppliedSoftware);
	}

	@Override
	public List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.getCustomerSuppliedSoftwareByLimit(page_no,item_per_page);
	}

	@Override
	public List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimitAndSearch(String searchText, int pageNo,
			int perPage) {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.getCustomerSuppliedSoftwareByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getCustomerSuppliedSoftwareCount() {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.getCustomerSuppliedSoftwareCount();
	}

	@Override
	public int getCustomerSuppliedSoftwareCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.getCustomerSuppliedSoftwareCountAndSearch(searchText);
	}

	@Override
	public Optional<CustomerSuppliedSoftware> getCustomerSuppliedSoftware(String formSrNo) {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.getCustomerSuppliedSoftware(formSrNo);
	}

	@Override
	public void deleteCustomerSuppliedSoftware(CustomerSuppliedSoftware customerSuppliedSoftware) {
		// TODO Auto-generated method stub
		customerSuppliedSoftwareRepo.delete(customerSuppliedSoftware);
	}

	@Override
	public int getSuppliedSoftwareCount() {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.getSuppliedSoftwareCount();
	}

	@Override
	public int getCustomerSuppliedSoftwareTotalCount() {
		// TODO Auto-generated method stub
		return customerSuppliedSoftwareRepo.getCustomerSuppliedSoftwareTotalCount();
	}
	

}
