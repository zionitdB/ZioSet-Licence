package com.ZioSet.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.Associate;
import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Licence;
import com.ZioSet.model.LicenceExpriry;
import com.ZioSet.model.LicenceLifeNotification;
import com.ZioSet.model.LicenceTypes;
import com.ZioSet.model.Product;
import com.ZioSet.model.Software;
import com.ZioSet.Repo.AssetLicenceRepo;
import com.ZioSet.Repo.AssociateRepo;
import com.ZioSet.Repo.InstallLicenceStockRepo;
import com.ZioSet.Repo.LicenceExpriryRepo;
import com.ZioSet.Repo.LicenceLifeNotificationRepo;
import com.ZioSet.Repo.LicenceTypesRepo;
import com.ZioSet.Repo.LincencceRepo;
import com.ZioSet.Repo.ProductRepo;
import com.ZioSet.Repo.SoftwareRepo;

@Service
public class LincencceManagementServiceImpl implements LincencceManagementService {
	@Autowired
	LincencceRepo lincencceRepo;
	
	@Autowired
	AssetLicenceRepo assetLicenceRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	AssociateRepo associateRepo;
	@Autowired
	LicenceTypesRepo licenceTypesRepo;
	@Autowired
	InstallLicenceStockRepo installLicenceStockRepo;
	
	@Autowired
	LicenceExpriryRepo licenceExpriryRepo;

	
	
	@Autowired
	LicenceLifeNotificationRepo licenceLifeNotificationRepo;
	
	
	@Autowired
	SoftwareRepo softwareRepo;

	@Override
	public void addLicence(Licence licence) {
		// TODO Auto-generated method stub
		System.out.println("Saving................ Licence");

		lincencceRepo.save(licence);
	}

	@Override
	public List<Licence> getLincencceByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return lincencceRepo.getLincencceByLimit(page_no,item_per_page);
	}

	@Override
	public List<Licence> getLicenceByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return lincencceRepo.getLicenceByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getLicenceCount() {
		// TODO Auto-generated method stub
		return lincencceRepo.getLicenceCount();
	}

	@Override
	public int getLicenceCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return lincencceRepo.getLicenceCountAndSearch(searchText);
	}

	@Override
	public Optional<Licence> getLicenceByKeyAndLicenceTy(String associate,String product, String key) {
		// TODO Auto-generated method stub
		return lincencceRepo.getLicenceByKeyAndLicenceTy(associate,product,key);
	}

	@Override
	public List<AssetLicence> getAssetLicenceByAssetId(int id) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAssetLicenceByAssetId(id);
	}

	@Override
	public List<Licence> getAllLicence() {
		// TODO Auto-generated method stub
		return lincencceRepo.findAll();
	}

	@Override
	public List<Licence> getAllLicenceByType(String type) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllLicenceByType(type);
	}

	@Override
	public List<Licence> LicenceByTypeAndName(String type, String name) {
		// TODO Auto-generated method stub
		return lincencceRepo.getLicenceByTypeAndName(type,name);
	}

	@Override
	public Optional<AssetLicence> getAssetLicenceByLicence(int id) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAssetLicenceByLicence(id);
	}

	@Override
	public void addAssetLicence(AssetLicence licence) {
		// TODO Auto-generated method stub
		assetLicenceRepo.save(licence);
	}

	@Override
	public List<AssetLicence> getAssetLicenceLincenceByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAssetLicenceLincenceByLimit(page_no,item_per_page);
	}

	@Override
	public List<AssetLicence> getAssetLicenceLicenceByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAssetLicenceLicenceByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getAssetLicenceLicenceCount() {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAssetLicenceLicenceCount();
	}

	@Override
	public int getAssetLicenceLicenceCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAssetLicenceLicenceCountAndSearch(searchText);
	}

	@Override
	public List<Licence> getAllocatedLicence() {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllocatedLicence();
	}

	@Override
	public List<Licence> getInStockLicence() {
		// TODO Auto-generated method stub
		return lincencceRepo.getInStockLicence();
	}

	@Override
	public int getAllLicenceCountByPublisherName(String str) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllLicenceCountByPublisherName(str);
	}

	@Override
	public int getInStockLicenceCountByPublisherName(String str) {
		// TODO Auto-generated method stub
		return lincencceRepo.getInStockLicenceCountByPublisherName(str);
	}

	@Override
	public int getAssignedLicenceCountByPublisherName(String str) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAssignedLicenceCountByPublisherName(str);
	}

	@Override
	public Associate addAssociate(Associate associate) {
		// TODO Auto-generated method stub
		return associateRepo.save(associate);
	}

	@Override
	public List<Associate> getAssociates() {
		// TODO Auto-generated method stub
		return associateRepo.findAll();
	}

	@Override
	public Optional<Associate> getAssociateByName(String associateName) {
		// TODO Auto-generated method stub
		return associateRepo.getAssociateByName(associateName);
	}

	@Override
	public Optional<Product> getProductsByName(String productName) {
		// TODO Auto-generated method stub
		return productRepo.getProductsByName(productName);
	}

	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		return productRepo.findAll();
	}

	@Override
	public Product addProduct(Product product) {
		// TODO Auto-generated method stub
		return productRepo.save(product);
	}

	@Override
	public List<Licence> getAllLicenceByTypeAndProduct(String type, String productName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Licence> getProductNameByTypeAndAssociate(String type, String associateName) {
		// TODO Auto-generated method stub
		return lincencceRepo.getProductNameByTypeAndAssociate(type,associateName);
	}

	@Override
	public List<Licence> getAllLicencceByTypeAndAssociateAndProduct(String type, String associateName,
			String productName) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllLicencceByTypeAndAssociateAndProduct(type,associateName,productName);
	}

	@Override
	public List<Licence> getAllLicencceByTypeAndAssociateAndProductAndVersion(String type, String associateName,
			String productName, String version) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllLicencceByTypeAndAssociateAndProductAndVersion(type,associateName,productName,version);
	}

	@Override
	public List<AssetLicence> getAllAssetLicencceByType(String type) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAllAssetLicencceByType(type);
	}

	@Override
	public List<AssetLicence> getAllAssetLicencceByTypeAndAssociate(String type, String associateName) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAllAssetLicencceByTypeAndAssociate(type,associateName);
	}

	@Override
	public List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProduct(String type, String associateName,
			String productName) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAllAssetLicencceByTypeAndAssociateAndProduct(type,associateName,productName);
	}

	@Override
	public List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersion(String type,
			String associateName, String productName, String version) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAllAssetLicencceByTypeAndAssociateAndProductAndVersion(type,associateName,productName,version);
	}

	@Override
	public List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId(String type,
			String associateName, String productName, String version, String assetId) {
		// TODO
		return assetLicenceRepo.getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId(type,associateName,productName,version,assetId);
	}

	@Override
	public void addLicenceTypes(LicenceTypes licenceTypes) {
		// TODO Auto-generated method stub
		licenceTypesRepo.save(licenceTypes);
	}

	@Override
	public List<LicenceTypes> getLicenceTypes() {
		// TODO Auto-generated method stub
		return licenceTypesRepo.findAll();
	}

	@Override
	public void addLicenceLifeNotification(LicenceLifeNotification licenceLifeNotification) {
		// TODO Auto-generated method stub
		licenceLifeNotificationRepo.save(licenceLifeNotification);
	}

	@Override
	public List<LicenceLifeNotification> getAllLicenceLifeNotification() {
		// TODO Auto-generated method stub
		return licenceLifeNotificationRepo.findAll();
	}

	@Override
	public void deleteLicenceLifeNotification(LicenceLifeNotification licenceLifeNotification) {
		// TODO Auto-generated method stub
		licenceLifeNotificationRepo.delete(licenceLifeNotification);
	}

	@Override
	public List<Licence> getAllLicenceByAssociateANdType(String associate, String type) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllLicenceByAssociateANdType(associate,type);
	}

	@Override
	public List<Licence> getInstockLicences() {
		// TODO Auto-generated method stub
		return lincencceRepo.getInStockLicence();
	}

	@Override
	public List<Licence> getAssignedLicences() {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllocatedLicence();
	}

	@Override
	public List<Licence> getInstalledLicenceByType(String type) {
		// TODO Auto-generated method stub
		return lincencceRepo.getInstalledLicenceByType(type);
	}

	@Override
	public Optional<LicenceLifeNotification> getActiveLicenceLifeNotificationByType(String type) {
		// TODO Auto-generated method stub
		return licenceLifeNotificationRepo.getActiveLicenceLifeNotificationByType(type);
	}

	@Override
	public List<LicenceLifeNotification> getAllLicenceLifeNotificationByType(String type) {
		// TODO Auto-generated method stub
		return licenceLifeNotificationRepo.getAllLicenceLifeNotificationByType(type);
	}

	@Override
	public int getLicenceCountByAssociteName(String associateName) {
		// TODO Auto-generated method stub
		return lincencceRepo.getLicenceCountByAssociteName(associateName);
	}

	@Override
	public int getInstallLicenceCountByAssociate(String associateName) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getInstallLicenceCountByAssociate(associateName);
	}

	@Override
	public int getAllLicenceCountByProductName(String productName) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllLicenceCountByProductName(productName);
	}

	@Override
	public int getInstallLicenceCountByProduct(String productName) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getInstallLicenceCountByProduct(productName);
	}

	@Override
	public Optional<LicenceTypes> getLicenceTypeByName(String type) {
		// TODO Auto-generated method stub
		return licenceTypesRepo.getLicenceTypeByName(type);
	}

	@Override
	public Optional<AssetLicence> getAllAssetLicencceByAssociateAndProductAndAssetId(int associateId, int productid, int assetId) {
		// TODO Auto-generated method stub
		return assetLicenceRepo.getAllAssetLicencceByAssociateAndProductAndAssetId(associateId,productid,assetId);
	}

	@Override
	public List<Licence> getAvailableLicencceByAssociateAndProduct(int associateId, int productId) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAvailableLicencceByAssociateAndProduct(associateId,productId);
	}

	@Override
	public List<Licence> getAllExpiryLicences() {
		// TODO Auto-generated method stub
		Date today = new Date();
		return lincencceRepo.getAllExpiryLicences(today);
	}

	@Override
	public List<Licence> getAllExpiryLicencesByTypes(String type) {
		// TODO Auto-generated method stub
		Date today = new Date();
		return lincencceRepo.getAllExpiryLicencesByTypes(today,type);
	}

	@Override
	public List<Licence> getAllExpiryLicencesByTypesAndAssociation(String type, String association) {
		// TODO Auto-generated method stub
		Date today = new Date();
		return lincencceRepo.getAllExpiryLicencesByTypesAndAssociation(today,type,association);
	}

	@Override
	public List<Licence> getAllExpiryLicencesByTypesAndAssociationAndProduct(String type, String association,
			String product) {
		// TODO Auto-generated method stub
		Date today = new Date();
		return lincencceRepo.getAllExpiryLicencesByTypesAndAssociationAndProduct(today,type,association,product);
	}

	@Override
	public List<InstallLicenceStock> getAllInstallLicenceStocks() {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.findAll();
	}

	@Override
	public List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNo(String serialNo) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getAllInstallLicenceStocksBySerialNo(serialNo);
	}

	@Override
	public List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAnsAssociate(String serialNo,
			String associateName) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getAllInstallLicenceStocksBySerialNoAnsAssociate(serialNo,associateName);
	}

	@Override
	public List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct(String serialNo,
			String associateName, String productName) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct(serialNo,associateName,productName);
	}

	@Override
	public List<InstallLicenceStock> getSystemLincencceByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getSystemLincencceByLimit(page_no,item_per_page);
	}

	@Override
	public List<InstallLicenceStock> getSystemLicenceByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getSystemLicenceByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getSystemLicenceCount() {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getSystemLicenceCount();
	}

	@Override
	public int getSystemLicenceCount(String searchText) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getSystemLicenceCount(searchText);
	}

	@Override
	public void addLicenceExpriry(LicenceExpriry licenceExpriry) {
		// TODO Auto-generated method stub
		licenceExpriryRepo.save(licenceExpriry);
	}

	@Override
	public Optional<LicenceExpriry> getLicenceExpiryByPublisherProductAndRelease(String publisherName,
			String productName, String releaseName) {
		// TODO Auto-generated method stub
		return licenceExpriryRepo.getLicenceExpiryByPublisherProductAndRelease(publisherName,productName,releaseName);
	}

	@Override
	public List<LicenceExpriry> getExpiredLicencesByDate(Date nextDate) {
		// TODO Auto-generated method stub
		return licenceExpriryRepo.getExpiredLicencesByDate(nextDate);
	}

	@Override
	public List<InstallLicenceStock> getInstallLicenceStockByPublisherProductAndRelease(String publisherName,
			String productName, String releaseName) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getInstallLicenceStockByPublisherProductAndRelease(publisherName,productName,releaseName);
	}

	@Override
	public List<Licence> getUploadedLicenceStockByPublisherProductAndRelease(String publisherName, String productName,
			String releaseName) {
		// TODO Auto-generated method stub
		return lincencceRepo.getUploadedLicenceStockByPublisherProductAndRelease(publisherName,productName,releaseName);
	}

	@Override
	public int getCountOfInstallLicenceByPublisherName(String str) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getCountOfInstallLicenceByPublisherName(str);
	}

	@Override
	public int getCountOfInstallLicenceByProductName(String str) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getCountOfInstallLicenceByProductName(str);
	}

	@Override
	public int getAllInstalledCountByProductName(String applicationName) {
		// TODO Auto-generated method stub
		return installLicenceStockRepo.getCountOfInstallLicenceByProductName(applicationName);
	}

	@Override
	public int getAssignedSAASLicenceCount() {
		// TODO Auto-generated method stub
		long count =assetLicenceRepo.count();
		return (int) count;
	}

	@Override
	public List<Licence> getTotalRenewalCount(Date nextDate) {
		// TODO Auto-generated method stub
		List<Licence> licences=lincencceRepo.findAll();
		List<Licence> list=new ArrayList<Licence>();
		Date today=new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(today); 
		c.add(Calendar.DATE, 30);
		Date comDate = c.getTime();
		for(Licence licence:licences){
			Date exDate = null;
			if(licence.getPurchaseDate()!=null){
				if(licence.getLicencePeriodUnit().equalsIgnoreCase("Year")){
					System.out.println("PURCHASE "+licence.getPurchaseDate() );
					c.setTime(licence.getPurchaseDate()); 
					c.add(Calendar.YEAR, licence.getLicencePeriod());
					exDate=c.getTime();
				}
				if(licence.getLicencePeriodUnit().equalsIgnoreCase("Month")){
					c.setTime(licence.getPurchaseDate()); 
					c.add(Calendar.MONTH, licence.getLicencePeriod());
					exDate=c.getTime();
				}
				if(licence.getLicencePeriodUnit().equalsIgnoreCase("Day")){
					c.setTime(licence.getPurchaseDate()); 
					c.add(Calendar.DATE, licence.getLicencePeriod());
					exDate=c.getTime();
				}
				if(comDate.compareTo(exDate)>0||comDate.compareTo(exDate)==0){
					list.add(licence);
				}
				System.out.println("EXT DATA ::   "+exDate);

			}
			

		}
		
		
		return list;
	}

	@Override
	public List<Licence> getAllLicenceCostByProductName(String applicationName) {
		// TODO Auto-generated method stub
		return lincencceRepo.getAllLicenceCostByProductName(applicationName);
	}

	@Override
	public List<Software> getTodaysFetchLicenceByLimit(int page_no, int item_per_page, Date date) {
		// TODO Auto-generated method stub
		return softwareRepo.getTodaysFetchLicenceByLimit(page_no,item_per_page,date);
	}

	@Override
	public int getTodayFetchInstallLicenceCount(Date date) {
		// TODO Auto-generated method stub
		return softwareRepo.getTodayFetchInstallLicenceCount(date);
	}

}
