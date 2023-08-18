package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import com.ZioSet.model.CustomerSuppliedSoftware;

public interface CustomerSuppliedSoftwareService {

	List<CustomerSuppliedSoftware> getAllCustomerSuppliedSoftwares();

	void addNewCustomerSuppliedSoftware(CustomerSuppliedSoftware customerSuppliedSoftware);

	List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimit(int page_no, int item_per_page);

	List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimitAndSearch(String searchText, int pageNo,
			int perPage);

	int getCustomerSuppliedSoftwareCount();

	int getCustomerSuppliedSoftwareCountAndSearch(String searchText);

	Optional<CustomerSuppliedSoftware> getCustomerSuppliedSoftware(String formSrNo);

	void deleteCustomerSuppliedSoftware(CustomerSuppliedSoftware customerSuppliedSoftware);

	int getSuppliedSoftwareCount();

	int getCustomerSuppliedSoftwareTotalCount();

}
