package com.ZioSet.Service;

import java.util.List;

import com.ZioSet.model.Asset;

public interface AssetService {

	List<Asset> getAssetsByLimit(int page_no, int item_per_page);

	List<Asset> getAssetsByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getAssetsCount();

	int getAssetCountAndSearch(String searchText);

}
