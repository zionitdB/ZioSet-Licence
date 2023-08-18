package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.AssetEmployeeAssigned;


public interface AssetEmployeeAssignedCustomeRepo {
	List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimit(int page_no, int item_per_page, String dataReq);

	List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimitAndSearch(String searchText, int pageNo, int perPage, String dataReq);

	int getAssetEmployeeAssignedCount(String dataReq);

	int getAssetEmployeeAssignedCountAndSearch(String searchText, String dataReq);

}
