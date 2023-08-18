package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.AssetEmployeeAssigned;


public interface AssetEmployeeAssignedRepo extends JpaRepository<AssetEmployeeAssigned, Integer>,AssetEmployeeAssignedCustomeRepo{
	@Query("From AssetEmployeeAssigned a where a.asset.Id=?1")
	Optional<AssetEmployeeAssigned> getAllocationByAssetId(int id);
	@Query("From AssetEmployeeAssigned a where a.employee.employeeId=?1")
	List<AssetEmployeeAssigned> getEmployeeWiseAllocationReport(int empId);
	
	@Query("From AssetEmployeeAssigned a where a.asset.tagCode=?1")
	Optional<AssetEmployeeAssigned> getAssetEmployeeAllocationByAssetMAC(String tagMAC);
	
	@Query("From AssetEmployeeAssigned a where a.asset.tagCode=?1")
	List<AssetEmployeeAssigned> getAssetWiseAllocationReport(String tagCode);
	
	@Query("From AssetEmployeeAssigned a where a.asset.branch.branchId=?1")
	List<AssetEmployeeAssigned> getALLAssetAllocationBranchWise(int branchId);
	@Query("From AssetEmployeeAssigned a where a.asset.assetId=?1 and a.employee.username=?2")
	Optional<AssetEmployeeAssigned> getAllocationByAssetIdAndEmployee(String assetId, String username);
	@Query("From AssetEmployeeAssigned a where a.asset.assetId=?1")
	Optional<AssetEmployeeAssigned> getAsseingAssetByAssetId(String hostname);
	@Query("From AssetEmployeeAssigned")
	List<AssetEmployeeAssigned> getAllAssetEmployees();
	
	@Query("select COUNT(DISTINCT e.employee.employeeId) From AssetEmployeeAssigned e ")
	int getTotalAssingedEmployeeCount();
	
	@Query("From AssetEmployeeAssigned a where a.asset.assetId=?1")
	Optional<AssetEmployeeAssigned> getAllocationByAssetId(String assetId);
	
	@Query("From AssetEmployeeAssigned a where a.asset.Id=?1")
	List<AssetEmployeeAssigned> getAssetEmpAssingedByAssetId(int id);
	

	
}
