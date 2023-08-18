package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.Asset;

public interface AssetCustomeRepo {
	List<Asset> getAssetsByLimit(int page_no, int item_per_page);

	List<Asset> getAssetsByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getAssetsCount();

	int getAssetCountAndSearch(String searchText);
	
	List<Asset> getAssignedAssetsByLimit(int page_no, int item_per_page);
	List<Asset> getAssinedAssetsByLimitAndSearch(String searchText, int pageNo, int perPage);
	int getAssingedAssetsCount();
	int getAssingedAssetCountAndSearch(String searchText);


}
