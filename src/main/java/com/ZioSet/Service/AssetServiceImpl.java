package com.ZioSet.Service;

import java.util.List;

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

}
