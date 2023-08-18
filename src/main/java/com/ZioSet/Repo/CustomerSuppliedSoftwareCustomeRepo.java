package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.CustomerSuppliedSoftware;

public interface CustomerSuppliedSoftwareCustomeRepo {


	List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimit(int page_no, int item_per_page);

	List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimitAndSearch(String searchText, int pageNo,
			int perPage);

	int getCustomerSuppliedSoftwareCount();
	int getCustomerSuppliedSoftwareTotalCount();

	int getCustomerSuppliedSoftwareCountAndSearch(String searchText);
}
