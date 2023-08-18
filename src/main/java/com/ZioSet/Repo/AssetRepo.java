package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Asset;

public interface AssetRepo extends JpaRepository<Asset, Integer>,AssetCustomeRepo{
	@Query("select count(*) From Asset ")
	int getTotalAssetCount();
	@Query("select count(*) From Asset a where a.availableStatus=0 ")
	int getAssingedAssetCount();
	
	@Query("From Asset a where a.serialNo=?1 ")
	Optional<Asset> checkSerialNo(String serialNo);
	@Query("From Asset a where a.availableStatus=0 ")
	List<Asset> getAllAssingedAsset();
	@Query("From Asset a where a.availableStatus=1 ")
	List<Asset> getAvailableAssets();
	@Query("select count(*) From Asset a where a.status=?1")
	int getAssetsCountbyStatus(String str);
	
	@Query("select count(*) From Asset a where a.assetType=?1")
	int getAssetsCountByTypes(String str);
	
	
}
