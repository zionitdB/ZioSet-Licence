package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import com.ZioSet.model.Asset;

public interface AssetService {

	List<Asset> getAssetsByLimit(int page_no, int item_per_page);

	List<Asset> getAssetsByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getAssetsCount();

	int getAssetCountAndSearch(String searchText);

	int getTotalAssetCount();

	int getAssingedAssetCount();

	List<Asset> getAssignedAssetsByLimit(int page_no, int item_per_page);

	List<Asset> getAssinedAssetsByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getAssingedAssetsCount();

	int getAssingedAssetCountAndSearch(String searchText);

	List<Asset> getAllAsset();

	Optional<Asset> checkSerialNo(String serialNo);

	Optional<Asset> getAssetByIdOp(int id);

	void addNewAsset(Asset asset);

	List<Asset> getAllAssingedAsset();

	Optional<Asset> getAssetBySerialNo(String serialNo);

	List<Asset> getAvailableAssets();

	int getTotalAssignedAssetCount();

	int getAssetsCountByStatus(String str);

	int getAssetsCountByTypes(String str);

}
