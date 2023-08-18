package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.Repo.AssetRepo;
import com.ZioSet.model.Asset;

@Service
public class AssetServiceImpl implements AssetService {
	@Autowired
	AssetRepo assetRepo;

	@Override
	public List<Asset> getAssetsByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return assetRepo.getAssetsByLimit(page_no,item_per_page);
	}

	@Override
	public List<Asset> getAssetsByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return assetRepo.getAssetsByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getAssetsCount() {
		// TODO Auto-generated method stub
		return assetRepo.getAssetsCount();
	}

	@Override
	public int getAssetCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return assetRepo.getAssetCountAndSearch(searchText);
	}

	@Override
	public int getTotalAssetCount() {
		// TODO Auto-generated method stub
		return assetRepo.getTotalAssetCount();
	}

	@Override
	public int getAssingedAssetCount() {
		// TODO Auto-generated method stub
		return assetRepo.getAssingedAssetCount();
	}

	@Override
	public List<Asset> getAssignedAssetsByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return assetRepo.getAssignedAssetsByLimit(page_no,item_per_page);
	}

	@Override
	public List<Asset> getAssinedAssetsByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return assetRepo.getAssinedAssetsByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getAssingedAssetsCount() {
		// TODO Auto-generated method stub
		return assetRepo.getAssingedAssetsCount();
	}

	@Override
	public int getAssingedAssetCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return assetRepo.getAssingedAssetCountAndSearch(searchText);
	}

	@Override
	public List<Asset> getAllAsset() {
		// TODO Auto-generated method stub
		return assetRepo.findAll();
	}

	@Override
	public Optional<Asset> checkSerialNo(String serialNo) {
		// TODO Auto-generated method stub
		return assetRepo.checkSerialNo(serialNo);
	}

	@Override
	public Optional<Asset> getAssetByIdOp(int id) {
		// TODO Auto-generated method stub
		return assetRepo.findById(id);
	}

	@Override
	public void addNewAsset(Asset asset) {
		// TODO Auto-generated method stub
		assetRepo.save(asset);
	}

	@Override
	public List<Asset> getAllAssingedAsset() {
		// TODO Auto-generated method stub
		return assetRepo.getAllAssingedAsset();
	}

	@Override
	public Optional<Asset> getAssetBySerialNo(String serialNo) {
		// TODO Auto-generated method stub
		return assetRepo.checkSerialNo(serialNo);
	}

	@Override
	public List<Asset> getAvailableAssets() {
		// TODO Auto-generated method stub
		return assetRepo.getAvailableAssets();
	}

	@Override
	public int getTotalAssignedAssetCount() {
		// TODO Auto-generated method stub
		return assetRepo.getAssingedAssetCount();
	}

	@Override
	public int getAssetsCountByStatus(String str) {
		// TODO Auto-generated method stub
		return assetRepo.getAssetsCountbyStatus(str);
	}

	@Override
	public int getAssetsCountByTypes(String str) {
		// TODO Auto-generated method stub
		return assetRepo.getAssetsCountByTypes(str);
	}

}
