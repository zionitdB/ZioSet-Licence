package com.ZioSet.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.InstallLicenceStock;

public interface InstallLicenceStockRepo   extends JpaRepository<InstallLicenceStock, Integer>,InstallLicenceStockCustomeRepo{
	@Query("From InstallLicenceStock i where i.asset.Id=?1 and i.associate.id=?2 and i.product.id=?3 and i.productVersion=?4")
	Optional<InstallLicenceStock> checkInstallLicenceStock(int assetId, int associateId, int productId,String version);
	@Query("select count(*)From InstallLicenceStock i where i.associate.associateName=?1 ")
	int getInstallLicenceCountByAssociate(String associateName);
	@Query("select count(*)From InstallLicenceStock i where i.product.productName=?1 ")
	int getInstallLicenceCountByProduct(String productName);
	@Query("From InstallLicenceStock i where i.associate.associateName=?1 ")
	List<InstallLicenceStock> getAllInstalledLicencceReportByAssociate(String associateName);
	@Query("From InstallLicenceStock i where i.associate.associateName=?1  and i.product.productName=?2 ")
	List<InstallLicenceStock> getAllInstalledLicencceReportByAssociateAndProduct(String associateName, String productName);
	
	@Query("From InstallLicenceStock i where i.asset.serialNo=?1 ")
	List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNo(String serialNo);

	@Query("From InstallLicenceStock i where i.asset.serialNo=?1 and i.associate.associateName=?2 ")
	List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAnsAssociate(String serialNo, String associateName);
	
	@Query("From InstallLicenceStock i where i.asset.serialNo=?1 and i.associate.associateName=?2  and i.product.productName=?3 ")
	List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct(String serialNo,
			String associateName, String productName);
	@Query("From InstallLicenceStock i where  i.associate.associateName=?1  and i.product.productName=?2 and i.productVersion=?3 ")
	List<InstallLicenceStock> getInstallLicenceStockByPublisherProductAndRelease(String publisherName,
			String productName, String releaseName);
	
	@Query("select count (i) From InstallLicenceStock i where  i.associate.associateName=?1 ")
	int getCountOfInstallLicenceByPublisherName(String str);
	@Query("select count (i) From InstallLicenceStock i where  i.product.productName=?1 ")
	int getCountOfInstallLicenceByProductName(String str);
	@Query("select count (distinct i.asset.Id) From InstallLicenceStock i")
	int getDistictAssetCountInStock();
	
	//@Query("select  (distinct i.asset.Id) From InstallLicenceStock i")
	@Query(value = "SELECT distinct asset_id FROM licence_install_stock;", nativeQuery = true)
	List<Integer> getListWorkerInstalledList();
	
	@Query("select count(*) From InstallLicenceStock i where i.associate.associateName=?1 ")
	int getCountOfInstallLicenceStock(String str);
	
	@Query("From InstallLicenceStock i where i.product.productName=?1 ")
	List<InstallLicenceStock> getListOfLicencesByProductName(String string);
	/*@Query("From InstallLicenceStock i where i.product.productName=?1 ")
	List<InstallLicenceStock> getListOfLicencesByProductName(String string);
	*/
	/*@Query("select count(*) From InstallLicenceStock i where i.edition=?1 and i.product.productName LIKE :2 ")
	int getCountByEditionAndProductName(String name, String string);
*/	@Query("From InstallLicenceStock i where i.asset.id=?1 ")
	List<InstallLicenceStock> getInstallLicenceStockebyAssetId(int assetId);
	@Query("select count(*) From InstallLicenceStock i where i.product.productName=?1 ")

	int getCountOfInstallLicenceStockByproduct(String applicationName);
	@Query("From InstallLicenceStock i where i.asset.id=?1  and i.product.id=?2")
	List<InstallLicenceStock> getInstallLicenceStockebyAssetIdAndProduct(int id, int id2);
}
