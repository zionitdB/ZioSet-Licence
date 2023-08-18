package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.AssetTransaction;


public interface AssetEmployeeMappeServices {

	void mappedAsset(AssetEmployeeAssigned assetEmployeeAssigned);

	List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimit(int page_no, int item_per_page, String dataReq);

	List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimitAndSearch(String searchText, int pageNo, int perPage, String dataReq);

	int getAssetEmployeeAssignedCount(String dataReq);

	int getAssetEmployeeAssignedCountAndSearch(String searchText, String dataReq);

	void releaseMappedAsset(AssetEmployeeAssigned assetEmployeeAssigned);

	void saveAssetTransaction(AssetTransaction assetTransaction);

	AssetEmployeeAssigned getAllocationByAssetId(int id);

	List<AssetTransaction> getAllocationHistoryByAssetId(int id);

	Optional<AssetEmployeeAssigned> getAssetEmployeeAllocationByAssetMAC(String tagMAC);

	void saveAssetEmployee(AssetEmployeeAssigned assetEmployeeAssigned);

	List<AssetEmployeeAssigned> getEmployeeWiseAllocationReport(int employeeId);

	Optional<AssetEmployeeAssigned> getAllocationByAssetIdAndEmployee(String assetId, String username);

	List<AssetEmployeeAssigned> getAllAssetEmployees();

	Optional<AssetEmployeeAssigned> getAllocationByAssetId(String assetId);

	List<AssetEmployeeAssigned> getAssetEmpAssingedByAssetId(int id);

	void deleteAssetEmployeeAssigned(AssetEmployeeAssigned assetEmployeeAssigned);

	Optional<AssetEmployeeAssigned> getAllocationByAsset(int id);

}
