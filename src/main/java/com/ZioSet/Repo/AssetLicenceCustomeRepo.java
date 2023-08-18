package com.ZioSet.Repo;

import java.util.List;

import com.ZioSet.model.AssetLicence;

public interface AssetLicenceCustomeRepo {
	List<AssetLicence> getAssetLicenceLincenceByLimit(int page_no, int item_per_page);
	List<AssetLicence> getAssetLicenceLicenceByLimitAndSearch(String searchText, int pageNo, int perPage);
	int getAssetLicenceLicenceCount();
	int getAssetLicenceLicenceCountAndSearch(String searchText);
}
