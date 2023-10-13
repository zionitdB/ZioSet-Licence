package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.InstallLicenceStock;

public interface InstallLicenceStockCustomeRepo {
	List<InstallLicenceStock> getSystemLincencceByLimit(int page_no, int item_per_page);
	List<InstallLicenceStock> getSystemLicenceByLimitAndSearch(String searchText, int pageNo, int perPage);
	int getSystemLicenceCount();
	int getSystemLicenceCount(String searchText);
	List<InstallLicenceStock> getListOfLicencesByProductName(String string);
	int getCountByEditionAndProductName(String name, String string);


}
