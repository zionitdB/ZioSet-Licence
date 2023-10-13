package com.ZioSet.Repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ZioSet.model.Licence;

public interface LincencceRepo extends JpaRepository<Licence, Integer>,LincencceCustomeRepo {
	@Query("From Licence l where  l.associate.associateName=?1 and  l.product.productName=?2 and l.licenceType=?3 ")
	Optional<Licence> getLicenceByKeyAndLicenceTy(String associate, String product,String key);
	@Query("From Licence l where l.licenceType=?1 ")
	List<Licence> getAllLicenceByType(String type);
	@Query("From Licence l where l.licenceType=?1 and l.product.productName=?2")
	List<Licence> getLicenceByTypeAndName(String type, String name);
	@Query("From Licence l where l.licenceStatus!='In-Stock'")
	List<Licence> getAllocatedLicence();
	@Query("From Licence l where l.licenceStatus='In-Stock'")
	List<Licence> getInStockLicence();
	
	@Query("select count(*) From Licence l where l.associate.associateName=?1")
	int getAllLicenceCountByPublisherName(String str);
	@Query("select count(*) From Licence l where l.associate.associateName=?1 and l.licenceStatus='In-Stock'")
	int getInStockLicenceCountByPublisherName(String str);
	@Query("select count(*) From Licence l where l.associate.associateName=?1 and l.licenceStatus!='In-Stock'")
	int getAssignedLicenceCountByPublisherName(String str);
	@Query("From Licence l where l.licenceType=?1 and l.associate.associateName=?2")
	List<Licence> getProductNameByTypeAndAssociate(String type, String associateName);
	@Query("From Licence l where l.licenceType=?1 and l.associate.associateName=?2 and  l.product.productName=?3")
	List<Licence> getAllLicencceByTypeAndAssociateAndProduct(String type, String associateName, String productName);
	@Query("From Licence l where l.licenceType=?1 and l.associate.associateName=?2 and  l.product.productName=?3 and l.licenceVersion=?4")
	List<Licence> getAllLicencceByTypeAndAssociateAndProductAndVersion(String type, String associateName,
			String productName, String version);
	@Query("From Licence l where l.associate.associateName=?1 and l.licenceType=?2")
	List<Licence> getAllLicenceByAssociateANdType(String associate, String type);

	@Query("From Licence l where l.licenceType=?1  and l.installlationDate!=null ")
	List<Licence> getInstalledLicenceByType(String type);
	
	@Query("select count(*) From Licence l where l.associate.associateName=?1")
	int getLicenceCountByAssociteName(String associateName);
	@Query("select count(*) From Licence l where l.product.productName=?1")
	int getAllLicenceCountByProductName(String productName);
	@Query("From Licence l where l.associate.id=?1 and l.product.id=?2 and l.licenceStatus='In-Stock'")
	List<Licence> getAvailableLicencceByAssociateAndProduct(int associateId, int productId);
	@Query("From Licence l where l.licenceExpiryDate<?1")
	List<Licence> getAllExpiryLicences(Date today);
	
	
	
	@Query("From Licence l where l.licenceExpiryDate<?1 and l.licenceType=?2")
	List<Licence> getAllExpiryLicencesByTypes(Date today,String type);
	@Query("From Licence l where l.licenceExpiryDate<?1 and l.licenceType=?2 and  l.associate.associateName=?3")
	List<Licence> getAllExpiryLicencesByTypesAndAssociation(Date today,String type, String association);
	@Query("From Licence l where l.licenceExpiryDate<?1 and l.licenceType=?2 and  l.associate.associateName=?3 and  l.product.productName=?4")
	List<Licence> getAllExpiryLicencesByTypesAndAssociationAndProduct(Date today,String type, String association, String product);
	@Query("From Licence l where   l.associate.associateName=?1 and  l.product.productName=?2 and l.licenceVersion=?3")
	List<Licence> getUploadedLicenceStockByPublisherProductAndRelease(String publisherName, String productName,
			String releaseName);
	@Query("From Licence l where l.product.productName=?1")
	List<Licence> getAllLicenceCostByProductName(String applicationName);
	
	@Query("From Licence l where l.associate.id=?1 and l.product.id=?2")

	List<Licence> getAllLicenceByPublisherAndProduct(int publisher, int product);
	
	
	@Query("From Licence l where  l.associate.associateName=?1 and  l.product.productName=?2 and l.licenceKey=?3 ")
	Optional<Licence> getLicenceByPublisherProductKey(String associate, String product,String key);
	
	@Query("select count(*) From Licence l where l.keyGenIndx is not null")
	int getMaxIndexIsPresent();
	@Query("select max(l.keyGenIndx) From Licence l where l.keyGenIndx is not null")

	String getMaxKeyIndex();
}
