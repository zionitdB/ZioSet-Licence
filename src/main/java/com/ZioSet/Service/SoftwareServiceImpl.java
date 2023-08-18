package com.ZioSet.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.Repo.AssetLicenceRepo;
import com.ZioSet.Repo.AssetRepo;
import com.ZioSet.Repo.AssociateRepo;
import com.ZioSet.Repo.InstallLicenceStockRepo;
import com.ZioSet.Repo.LincencceRepo;
import com.ZioSet.Repo.ProductRepo;
import com.ZioSet.Repo.SoftwareRepo;
import com.ZioSet.model.Asset;
import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.Associate;
import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Licence;
import com.ZioSet.model.Product;
import com.ZioSet.model.Software;
@Service
public class SoftwareServiceImpl implements SoftwareService {
	
	@Autowired
	SoftwareRepo softwareRepo;
	
	
	@Autowired 
	AssetRepo assetRepo;
	
	@Autowired
	AssociateRepo associateRepo;
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	AssetLicenceRepo assetLicenceRepo;
	@Autowired
	LincencceRepo lincencceRepo;
	
	@Autowired
	InstallLicenceStockRepo installLicenceStockRepo;
	
	
	
	@Override
	public void addSoftwareOnj( Software software) {
		// TODO Auto-generated method stub
				
				if (software.getApplicationName().matches(".*[a-z].*")) { 
					System.out.println("Publisher "+software.toString());

					System.out.println("Application "+software.getApplicationName());
					String publisher=software.getPublisher().replaceAll("\\s", "");
					String applicationName=software.getApplicationName().replaceAll("\\s", "");

					if(publisher.equalsIgnoreCase("")||publisher.equalsIgnoreCase(null)){
						System.out.println("Publisher  IS EMPTY");
					}else{
				    // Do something
					Asset asset=null;
					Associate associate= new Associate();
					Product product= new Product();
					System.out.println("Serial No:: "+software.getSerialNo());
					Optional<Asset> optional=assetRepo.checkSerialNo(software.getSerialNo());
					if(optional.isPresent()){
						software.setAsset(optional.get());
						asset=optional.get();
					
					Optional<Associate> optionalAsso=associateRepo.getAssociateByName(software.getPublisher());
				//	System.out.println("Associdate "+ optionalAsso.isPresent());
						if(!optionalAsso.isPresent()){
							Associate associateNew = new Associate();
							associateNew.setAssociateName(software.getPublisher());
							associate=associateRepo.save(associateNew);
							System.out.println("New Associdate  Added");

						}else{
							associate=optionalAsso.get();
						}
						Optional<Product> optionalProduct=productRepo.getProductsByName(software.getApplicationName());
						//System.out.println("Product "+ optionalProduct.isPresent());
							if(!optionalProduct.isPresent()){
								Product productNew = new Product();
								productNew.setProductName(software.getApplicationName());
								product=productRepo.save(productNew);
								System.out.println("New Associdate  Added");

							}else{
								product=optionalProduct.get();
							}
					software.setDetectedDate(new Date());
					software.setDetectedDatetime(new Date());
					
					String insDate=software.getInstallDate();

					Date InsDate = null;
					if(insDate.matches(".*\\d.*")){
						DateFormat df = new SimpleDateFormat("yyyyMMdd");
						try {
							InsDate = df.parse(software.getInstallDate());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						List<AssetLicence>assetLicences =assetLicenceRepo.getAssetLicencesByAssetIdAssociationProduct(asset.getId(),associate.getId(),product.getId());
							if(assetLicences.size()!=0){
								Licence licence=assetLicences.get(0).getLicence();
								licence.setInstalllationDate(InsDate);
								Calendar c = Calendar.getInstance();
								c.setTime(InsDate);
								Date Expiry = null;
								if(licence.getLicencePeriodUnit().equalsIgnoreCase("Year")){
									c.add(Calendar.YEAR, licence.getLicencePeriod());
									Expiry=c.getTime();
								}
								if(licence.getLicencePeriodUnit().equalsIgnoreCase("Month")){
									c.add(Calendar.MONTH, licence.getLicencePeriod());
									Expiry=c.getTime();		
								}
								if(licence.getLicencePeriodUnit().equalsIgnoreCase("Day")){
									c.add(Calendar.DAY_OF_YEAR, licence.getLicencePeriod());
									Expiry=c.getTime();		
								}
								licence.setLicenceExpiryDate(Expiry);
								lincencceRepo.save(licence);
							}
					}
					Optional<InstallLicenceStock> optianlLicenceStock=installLicenceStockRepo.checkInstallLicenceStock(asset.getId(),associate.getId(),product.getId(),software.getVersion());
					System.out.println("Asset ID "+asset.getId()+"  Product "+product.getId()+"   Publisher "+associate.getId()+" IS "+optianlLicenceStock.isPresent());
					if(!optianlLicenceStock.isPresent()){
						InstallLicenceStock installLicenceStock= new InstallLicenceStock();
						installLicenceStock.setAsset(asset);
						installLicenceStock.setComputerName(software.getComputeName());
						installLicenceStock.setEdition(software.getEdition());
						installLicenceStock.setAssociate(associate);
						installLicenceStock.setProduct(product);
						installLicenceStock.setDetectedDate(new Date());
						installLicenceStock.setInstallDate(InsDate);
						installLicenceStock.setProductVersion(software.getVersion());
						installLicenceStockRepo.save(installLicenceStock);
					}else{
						InstallLicenceStock installLicenceStock=optianlLicenceStock.get();
						installLicenceStock.setEdition(software.getEdition());

						installLicenceStock.setComputerName(software.getComputeName());
						installLicenceStockRepo.save(installLicenceStock);
					}
					Optional<Software> optionalSof=softwareRepo.checkSoftwareIsAvailable(software.getAsset().getId(),software.getApplicationName(),software.getVersion(),software.getDetectedDate());
					
							if(!optionalSof.isPresent()){
								softwareRepo.save(software);	
							}
					
						
					//softwaresRes.add(software);

					/*Optional<Software> optional2= softwarerRepo.findSoftwareByProductDateAndSerialNo(software.getSerialNo(),software.getApplicationName(),reqDate);
					if(optional2.isPresent()){

					}else{

					}
					*/
					
				}
				}
			
		//	
	}
		
	}

	@Override
	public Date getLatestDate() {
		// TODO Auto-generated method stub
		return softwareRepo.getLatestDate();
	}

	@Override
	public Date getSecondDate(Date latestDate) {
		// TODO Auto-generated method stub
		return softwareRepo.getSecondDate(latestDate);
	}

	@Override
	public int getFetchCountByDate(Date latestDate) {
		// TODO Auto-generated method stub
		return softwareRepo.getFetchCountByDate(latestDate);
	}

	@Override
	public int getFetchUniqueCountByDate(Date latestDate) {
		// TODO Auto-generated method stub
		return softwareRepo.getFetchUniqueCountByDate(latestDate);
	}

	@Override
	public List<Software> getInstallLiceneceByDate(Date date) {
		// TODO Auto-generated method stub
		return softwareRepo.getInstallLiceneceByDate(date);
	}

}
