package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.AssetTransaction;
import com.ZioSet.Repo.AssetEmployeeAssignedRepo;
import com.ZioSet.Repo.AssetTransactionRepo;



@Service
public class AssetEmployeeMappeServicesImpl implements AssetEmployeeMappeServices{
	
	@Autowired
	AssetEmployeeAssignedRepo assetEmployeeAssignedRepo;
	@Autowired
	AssetTransactionRepo assetTransactionRepo;

	@Override
	public void mappedAsset(AssetEmployeeAssigned assetEmployeeAssigned) {
		// TODO Auto-generated method stub
		assetEmployeeAssignedRepo.save(assetEmployeeAssigned);
	}

	@Override
	public List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimit(int page_no, int item_per_page,String dataReq) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAssetEmployeeAssignedByLimit(page_no,item_per_page,dataReq);
	}

	@Override
	public List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimitAndSearch(String searchText, int pageNo,
			int perPage,String dataReq) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAssetEmployeeAssignedByLimitAndSearch(searchText,pageNo,perPage,dataReq);
	}

	@Override
	public int getAssetEmployeeAssignedCount(String dataReq) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAssetEmployeeAssignedCount(dataReq);
	}

	@Override
	public int getAssetEmployeeAssignedCountAndSearch(String searchText,String dataReq) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAssetEmployeeAssignedCountAndSearch(searchText,dataReq);
	}

	@Override
	public void releaseMappedAsset(AssetEmployeeAssigned assetEmployeeAssigned) {
		// TODO Auto-generated method stub
		assetEmployeeAssignedRepo.delete(assetEmployeeAssigned);
	}

	@Override
	public void saveAssetTransaction(AssetTransaction assetTransaction) {
		// TODO Auto-generated method stub
		assetTransactionRepo.save(assetTransaction);
	}

	@Override
	public AssetEmployeeAssigned getAllocationByAssetId(int id) {
		// TODO Auto-generated method stub
		Optional<AssetEmployeeAssigned> optional= assetEmployeeAssignedRepo.getAllocationByAssetId(id);
		System.out.println("EMOD................"+optional.isPresent()+"  "+id);
		return optional.isPresent()?optional.get():null;
	}

	@Override
	public List<AssetTransaction> getAllocationHistoryByAssetId(int id) {
		// TODO Auto-generated method stub
		return assetTransactionRepo.getAllocationHistoryByAssetId(id);
	}

	@Override
	public Optional<AssetEmployeeAssigned> getAssetEmployeeAllocationByAssetMAC(String tagMAC) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAssetEmployeeAllocationByAssetMAC(tagMAC);
	}

	@Override
	public void saveAssetEmployee(AssetEmployeeAssigned assetEmployeeAssigned) {
		// TODO Auto-generated method stub
		assetEmployeeAssignedRepo.save(assetEmployeeAssigned);
	}

	@Override
	public List<AssetEmployeeAssigned> getEmployeeWiseAllocationReport(int employeeId) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getEmployeeWiseAllocationReport(employeeId);
	}

	@Override
	public Optional<AssetEmployeeAssigned> getAllocationByAssetIdAndEmployee(String assetId, String username) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAllocationByAssetIdAndEmployee(assetId,username);
	}

	@Override
	public List<AssetEmployeeAssigned> getAllAssetEmployees() {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAllAssetEmployees();
	}

	@Override
	public Optional<AssetEmployeeAssigned> getAllocationByAssetId(String assetId) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAllocationByAssetId(assetId);
	}

	@Override
	public List<AssetEmployeeAssigned> getAssetEmpAssingedByAssetId(int id) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAssetEmpAssingedByAssetId(id);
	}

	@Override
	public void deleteAssetEmployeeAssigned(AssetEmployeeAssigned assetEmployeeAssigned) {
		// TODO Auto-generated method stub
		assetEmployeeAssignedRepo.delete(assetEmployeeAssigned);
	}

	@Override
	public Optional<AssetEmployeeAssigned> getAllocationByAsset(int id) {
		// TODO Auto-generated method stub
		return assetEmployeeAssignedRepo.getAllocationByAssetId(id);
	}

}
