package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.AssetLicence;

public interface AssetLicenceRepo extends JpaRepository<AssetLicence, Integer>,AssetLicenceCustomeRepo{
	@Query("From AssetLicence a where a.licence.id=?1")
	Optional<AssetLicence> getAssetLicenceByLicence(int id);
	@Query("From AssetLicence a where a.asset.id=?1")
	List<AssetLicence> getAssetLicenceByAssetId(int id);
	
	@Query("From AssetLicence a where a.licence.licenceType=?1")
	List<AssetLicence> getAllAssetLicencceByType(String type);
	
	@Query("From AssetLicence a where a.licence.licenceType=?1 and a.licence.associate.associateName=?2")
	List<AssetLicence> getAllAssetLicencceByTypeAndAssociate(String type, String associateName);
	
	@Query("From AssetLicence a where a.licence.licenceType=?1 and a.licence.associate.associateName=?2 and a.licence.product.productName=?3")
	List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProduct(String type, String associateName,
			String productName);
	@Query("From AssetLicence a where a.licence.licenceType=?1 and a.licence.associate.associateName=?2 and a.licence.product.productName=?3 and a.licence.licenceVersion=?4")
	List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersion(String type, String associateName,
			String productName, String version);
	
	@Query("From AssetLicence a where a.licence.licenceType=?1 and a.licence.associate.associateName=?2 and a.licence.product.productName=?3 and a.licence.licenceVersion=?4 and a.asset.assetId=?5")
	List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId(String type, String associateName,
			String productName, String version, String assetId);
	@Query("From AssetLicence a where a.asset.Id=?1 and a.licence.associate.id=?2 and a.licence.product.id=?3")
	List<AssetLicence> getAssetLicencesByAssetIdAssociationProduct(int assetId, int associateId, int productId);
	@Query("From AssetLicence a where a.licence.associate.id=?1 and a.licence.product.id=?2 and a.asset.Id=?3")
	Optional<AssetLicence> getAllAssetLicencceByAssociateAndProductAndAssetId(int associateId, int productid,
			int assetId);
	
	
	

}
