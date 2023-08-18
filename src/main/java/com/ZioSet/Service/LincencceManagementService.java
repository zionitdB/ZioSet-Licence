package com.ZioSet.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.Associate;
import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Licence;
import com.ZioSet.model.LicenceExpriry;
import com.ZioSet.model.LicenceLifeNotification;
import com.ZioSet.model.LicenceTypes;
import com.ZioSet.model.Product;
import com.ZioSet.model.Software;

public interface LincencceManagementService {

	void addLicence(Licence licence);

	List<Licence> getLincencceByLimit(int page_no, int item_per_page);

	List<Licence> getLicenceByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getLicenceCount();

	int getLicenceCountAndSearch(String searchText);

	Optional<Licence> getLicenceByKeyAndLicenceTy(String associate, String product,String key);

	List<AssetLicence> getAssetLicenceByAssetId(int id);

	List<Licence> getAllLicence();

	List<Licence> getAllLicenceByType(String type);

	List<Licence> LicenceByTypeAndName(String type, String name);

	Optional<AssetLicence> getAssetLicenceByLicence(int id);

	void addAssetLicence(AssetLicence licence);

	List<AssetLicence> getAssetLicenceLincenceByLimit(int page_no, int item_per_page);

	List<AssetLicence> getAssetLicenceLicenceByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getAssetLicenceLicenceCount();

	int getAssetLicenceLicenceCountAndSearch(String searchText);

	List<Licence> getAllocatedLicence();

	List<Licence> getInStockLicence();

	int getAllLicenceCountByPublisherName(String str);

	int getInStockLicenceCountByPublisherName(String str);

	int getAssignedLicenceCountByPublisherName(String str);

	Associate addAssociate(Associate associate);

	List<Associate> getAssociates();

	Optional<Associate> getAssociateByName(String associateName);

	Optional<Product> getProductsByName(String productName);

	List<Product> getProducts();

	Product addProduct(Product product);

	List<Licence> getAllLicenceByTypeAndProduct(String type, String productName);

	List<Licence> getProductNameByTypeAndAssociate(String type, String associateName);

	List<Licence> getAllLicencceByTypeAndAssociateAndProduct(String type, String associateName, String productName);

	List<Licence> getAllLicencceByTypeAndAssociateAndProductAndVersion(String type, String associateName,
			String productName, String version);

	List<AssetLicence> getAllAssetLicencceByType(String type);

	List<AssetLicence> getAllAssetLicencceByTypeAndAssociate(String type, String associateName);

	List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProduct(String type, String associateName,
			String productName);

	List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersion(String type, String associateName,
			String productName, String version);

	List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId(String type,
			String associateName, String productName, String version, String assetId);

	void addLicenceTypes(LicenceTypes licenceTypes);

	List<LicenceTypes> getLicenceTypes();

	void addLicenceLifeNotification(LicenceLifeNotification licenceLifeNotification);

	List<LicenceLifeNotification> getAllLicenceLifeNotification();

	void deleteLicenceLifeNotification(LicenceLifeNotification licenceLifeNotification);

	List<Licence> getAllLicenceByAssociateANdType(String str, String type);

	List<Licence> getInstockLicences();

	List<Licence> getAssignedLicences();

	List<Licence> getInstalledLicenceByType(String type);

	Optional<LicenceLifeNotification> getActiveLicenceLifeNotificationByType(String type);

	List<LicenceLifeNotification> getAllLicenceLifeNotificationByType(String type);

	int getLicenceCountByAssociteName(String str);

	int getInstallLicenceCountByAssociate(String associateName);

	int getAllLicenceCountByProductName(String productName);

	int getInstallLicenceCountByProduct(String productName);

	Optional<LicenceTypes> getLicenceTypeByName(String type);

	Optional<AssetLicence> getAllAssetLicencceByAssociateAndProductAndAssetId(int id, int id2, int id3);

	List<Licence> getAvailableLicencceByAssociateAndProduct(int id, int id2);

	List<Licence> getAllExpiryLicences();

	List<Licence> getAllExpiryLicencesByTypes(String type);

	List<Licence> getAllExpiryLicencesByTypesAndAssociation(String type, String association);

	List<Licence> getAllExpiryLicencesByTypesAndAssociationAndProduct(String type, String association, String product);

	List<InstallLicenceStock> getAllInstallLicenceStocks();

	List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNo(String serialNo);

	List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAnsAssociate(String serialNo, String associateName);

	List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct(String serialNo,
			String associateName, String productName);

	List<InstallLicenceStock> getSystemLincencceByLimit(int page_no, int item_per_page);

	List<InstallLicenceStock> getSystemLicenceByLimitAndSearch(String searchText, int pageNo, int perPage);

	int getSystemLicenceCount();

	int getSystemLicenceCount(String searchText);

	void addLicenceExpriry(LicenceExpriry licenceExpriry);

	Optional<LicenceExpriry> getLicenceExpiryByPublisherProductAndRelease(String publisherName, String productName,
			String releaseName);

	List<LicenceExpriry> getExpiredLicencesByDate(Date nextDate);

	List<InstallLicenceStock> getInstallLicenceStockByPublisherProductAndRelease(String publisherName,
			String productName, String releaseName);

	List<Licence> getUploadedLicenceStockByPublisherProductAndRelease(String publisherName, String productName,
			String releaseName);

	int getCountOfInstallLicenceByPublisherName(String str);

	int getCountOfInstallLicenceByProductName(String str);

	int getAllInstalledCountByProductName(String applicationName);

	int getAssignedSAASLicenceCount();

	List<Licence> getTotalRenewalCount(Date nextDate);

	List<Licence> getAllLicenceCostByProductName(String applicationName);

	List<Software> getTodaysFetchLicenceByLimit(int page_no, int item_per_page, Date date);

	int getTodayFetchInstallLicenceCount(Date date);

}
