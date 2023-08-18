package com.ZioSet.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import com.ZioSet.Repo.CPUDetialsRepo;
import com.ZioSet.Repo.InstallLicenceStockRepo;
import com.ZioSet.Repo.MemoryDetailsRepo;
import com.ZioSet.Repo.OSDetialsRepo;
import com.ZioSet.Repo.RamTotalRepo;
import com.ZioSet.Repo.SoftwareRepo;
import com.ZioSet.Service.AssetEmployeeMappeServices;
import com.ZioSet.Service.AssetService;
import com.ZioSet.Service.BundleAndCategoryService;
import com.ZioSet.Service.CustomerSuppliedSoftwareService;
import com.ZioSet.Service.EmployeeServices;
import com.ZioSet.Service.LincencceManagementService;
import com.ZioSet.Service.SoftwareService;
import com.ZioSet.dto.AssetTypesCountDtoRes;
import com.ZioSet.dto.ComparisonGraphDTO;
import com.ZioSet.dto.CountDTO;
import com.ZioSet.dto.DBEditionCountDto;
import com.ZioSet.dto.LicencenPercentageDto;
import com.ZioSet.dto.RequestObj;
import com.ZioSet.dto.TableCount;
import com.ZioSet.model.Asset;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.Associate;
import com.ZioSet.model.Branch;
import com.ZioSet.model.Bundle;
import com.ZioSet.model.BundleApplications;
import com.ZioSet.model.CPUDetials;
import com.ZioSet.model.Category;
import com.ZioSet.model.CategoryApplications;
import com.ZioSet.model.DashboardOverviewCountDto;
import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Licence;
import com.ZioSet.model.LicenceExpriry;
import com.ZioSet.model.MemoryDetails;
import com.ZioSet.model.OSDetials;
import com.ZioSet.model.Product;
import com.ZioSet.model.RamTotal;
import com.ZioSet.model.Software;

@RestController
@CrossOrigin("*")
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	OSDetialsRepo oSDetialsRepo;
	@Autowired
	CPUDetialsRepo cPUDetialsRepo;
	@Autowired
	MemoryDetailsRepo memoryDetailsRepo;
	@Autowired
	LincencceManagementService lincencceManagementService;
	@Autowired
	InstallLicenceStockRepo installLicenceStockRepo;
	@Autowired
	EmployeeServices employeeServices;
	
	@Autowired
	AssetService assetService;
	@Autowired
	CustomerSuppliedSoftwareService customerSuppliedSoftwareService;

	@Autowired
	SoftwareService softwareService;
	
	@Autowired
	BundleAndCategoryService bundleAndCategoryService;
	
	@Autowired
	AssetEmployeeMappeServices assetEmployeeMappeServices;
	
	
	@Autowired
	SoftwareRepo softwareRepo;
	
	@Autowired
	RamTotalRepo ramTotalRepo;
	
	
	
	
	@RequestMapping(value = "/getListWorkerInstalledList", method = RequestMethod.GET)
	public @ResponseBody Set<Asset>  getListWorkerInstalledList() {
		Set<Asset> lists=new HashSet<Asset>();
		
		try {
		List<Integer> categories =installLicenceStockRepo.getListWorkerInstalledList();
		int srNo=1;
		for(Integer id:categories){
			System.out.println("ASSET ID "+id);
			Optional<Asset> assetOp =assetService.getAssetByIdOp(id);
			Asset asset=assetOp.get(); 
			Date latestDate=softwareService.getLatestDate();
			AssetEmployeeAssigned assetEmp=assetEmployeeMappeServices.getAllocationByAssetId(id);
			if(assetEmp !=null){
				asset.setAllocatedToName(assetEmp.getEmployee().getFirstName()+" "+assetEmp.getEmployee().getLastName());
				asset.setAllocatedToNo(assetEmp.getEmployee().getEmployeeNo());
			}
			List<Software> optionalSOF=softwareRepo.chekAssetFetchOnLastCycle(id,latestDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
			String strDate = dateFormat.format(latestDate);
			asset.setWorkerStatusDate(strDate);
			if(optionalSOF.size()!=0){
				asset.setWorkerStatus("Active");
			}else{
				asset.setWorkerStatus("InActive");
			}
			asset.setSrNo(srNo);
			
			lists.add(asset);
			srNo++;
		}
	
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	@RequestMapping(value = "/getListWorkerInstalledListINDateRange", method = RequestMethod.POST)
	public @ResponseBody Set<Asset>  getListWorkerInstalledList(@RequestBody RequestObj requestObj) {
		Set<Asset> lists=new HashSet<Asset>();
		
		try {
		List<Integer> categories =installLicenceStockRepo.getListWorkerInstalledList();
		int srNo=1;
		for(Integer id:categories){
			List<Software> optionalSOF=softwareRepo.chekAssetFetchOnINDateRange(id,requestObj.getFromdate(),requestObj.getTodate());
			
			
			/// Foe not PResent
			if(optionalSOF.size()==0){
				System.out.println("ASSET ID "+id);
				Optional<Asset> assetOp =assetService.getAssetByIdOp(id);
				Asset asset=assetOp.get(); 
				Date latestDate=softwareService.getLatestDate();
				AssetEmployeeAssigned assetEmp=assetEmployeeMappeServices.getAllocationByAssetId(id);
				if(assetEmp !=null){
					asset.setAllocatedToName(assetEmp.getEmployee().getFirstName()+" "+assetEmp.getEmployee().getLastName());
					asset.setAllocatedToNo(assetEmp.getEmployee().getEmployeeNo());
				}
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
				String strDate = dateFormat.format(latestDate);
				asset.setWorkerStatusDate(strDate);
				
				System.out.println("optionalSOF   "+optionalSOF.size());

				if(optionalSOF.size()!=0){
					asset.setWorkerStatus("Active");
				}else{
					asset.setWorkerStatus("InActive");
				}
				asset.setSrNo(srNo);
				
				lists.add(asset);
				srNo++;
			}
			
		}
	
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	@RequestMapping(value = "/getCategoryWiseSAASCost", method = RequestMethod.GET)
	public @ResponseBody List<TableCount>  getCategoryWiseSAASCost() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Category> categories =bundleAndCategoryService.getAllCategory();
		
		for(Category category:categories){
			double tootalCost=0;
			List<CategoryApplications> categoryApplications=bundleAndCategoryService.getCategoryApplicationsByCategory(category.getCategorId());	
					for (CategoryApplications applications:categoryApplications) {
						
						
						List<Licence> cost=lincencceManagementService.getAllLicenceCostByProductName(applications.getApplicationName());
						for(Licence licence:cost){
							tootalCost+=Double.parseDouble(licence.getCost());	
						}
						
					
				}
					TableCount tableCount= new TableCount();
					tableCount.setCost(tootalCost);
					tableCount.setName(category.getCategoryName());
					lists.add(tableCount);
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	@RequestMapping(value = "/getBundleWiseSAASCost", method = RequestMethod.GET)
	public @ResponseBody List<TableCount> getBundleWiseSAASCost() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Bundle> bundles =bundleAndCategoryService.getAllBundles();
		
		for(Bundle bundle:bundles){
			double tootalCost=0;
			List<BundleApplications> bundleApplications=bundleAndCategoryService.getApplicationByBundleId(bundle.getBundleId());
				for (BundleApplications applications:bundleApplications) {

					List<Licence> cost=lincencceManagementService.getAllLicenceCostByProductName(applications.getApplicationName());
					for(Licence licence:cost){
						tootalCost+=Double.parseDouble(licence.getCost());	
					}
					
					//tootalCost+=cost;
					
				}
				TableCount tableCount= new TableCount();
				tableCount.setCost(tootalCost);
				tableCount.setName(bundle.getBundleName());
				lists.add(tableCount);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	
	
	@RequestMapping(value = "/getWorkerPercentage", method = RequestMethod.GET)
	public @ResponseBody LicencenPercentageDto getWorkerPercentage() {
		LicencenPercentageDto count=new LicencenPercentageDto();
		try {
			int totalAssetInStock=installLicenceStockRepo.getDistictAssetCountInStock();
			System.out.println("TOTAL COUNT "+totalAssetInStock);
			Date latestDate=softwareService.getLatestDate();
			int countByFistDate=softwareService.getFetchUniqueCountByDate(latestDate);
			System.out.println(" COUNT "+countByFistDate);
			System.out.println(" Date "+latestDate);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
			String strDate = dateFormat.format(latestDate);
			count.setTotalCount(totalAssetInStock);
			count.setActiveCount(countByFistDate);
			count.setDate(strDate);
		/*	int installCount=installLicenceStockRepo.getSystemLicenceCount();
			int saasCount=lincencceManagementService.getLicenceCount();
			System.out.println("INSTALL COUNT "+installCount);
			System.out.println("SAAS COUNT "+saasCount);

			int total=installCount+saasCount;
			
			System.out.println("TOTAL COUNT "+total);
			double installPercentage=(installCount*100)/total;
			double saasPercentage=(saasCount*100)/total;

			System.out.println("Install Per "+installPercentage);
			System.out.println("SAAS Per "+saasPercentage);
			count.setInstalPercentage(installPercentage);
			count.setSaasPercentage(saasPercentage);
*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	
	
	@RequestMapping(value = "/getLicencePercentage", method = RequestMethod.GET)
	public @ResponseBody LicencenPercentageDto getLicencePercentage() {
		LicencenPercentageDto count=new LicencenPercentageDto();
		try {
			
			int installCount=installLicenceStockRepo.getSystemLicenceCount();
			int saasCount=lincencceManagementService.getLicenceCount();
			System.out.println("INSTALL COUNT "+installCount);
			System.out.println("SAAS COUNT "+saasCount);

			int total=installCount+saasCount;
			
			System.out.println("TOTAL COUNT "+total);
			double installPercentage=(installCount*100)/total;
			double saasPercentage=(saasCount*100)/total;
			double totalCount=saasCount+installCount;
			System.out.println("Install Per "+installPercentage);
			System.out.println("SAAS Per "+saasPercentage);
			count.setInstalPercentage(installCount);
			count.setSaasPercentage(saasCount);
			count.setTotalCount(totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getBundleWiseComparision", method = RequestMethod.GET)
	public @ResponseBody ComparisonGraphDTO getBundleWiseComparision() {
		ComparisonGraphDTO graphDTO=new ComparisonGraphDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> installCounts= new ArrayList<Integer>();
		List<Integer> saasCounts= new ArrayList<Integer>();

		Set<String> status=new HashSet<String>();
		try {
		List<Bundle> bundles =bundleAndCategoryService.getAllBundles();
		
		for(Bundle bundle:bundles){
			int installCount=0;
			int saasCount=0;
			List<BundleApplications> bundleApplications=bundleAndCategoryService.getApplicationByBundleId(bundle.getBundleId());
				for (BundleApplications applications:bundleApplications) {

					int count1=lincencceManagementService.getAllInstalledCountByProductName(applications.getApplicationName());
					installCount+=count1;
					int count2=lincencceManagementService.getAllLicenceCountByProductName(applications.getApplicationName());
					saasCount+=count2;
					System.out.println("Application Name :: "+applications.getApplicationName()+" = "+count1);
					
				}
				names.add(bundle.getBundleName());
				installCounts.add(installCount);
				saasCounts.add(saasCount);
		}
		graphDTO.setNames(names);
		graphDTO.setInstalled(installCounts);
		graphDTO.setSaas(saasCounts);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return graphDTO;
	}
	@RequestMapping(value = "/getCategoryWiseComparision", method = RequestMethod.GET)
	public @ResponseBody ComparisonGraphDTO getCategoryWiseComparision() {
		ComparisonGraphDTO graphDTO=new ComparisonGraphDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> installCounts= new ArrayList<Integer>();
		List<Integer> saasCounts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Category> categories =bundleAndCategoryService.getAllCategory();
		
		for(Category category:categories){
			int installCount=0;
			int saasCount=0;
			List<CategoryApplications> categoryApplications=bundleAndCategoryService.getCategoryApplicationsByCategory(category.getCategorId());	
					for (CategoryApplications applications:categoryApplications) {
						
						
						int count1=lincencceManagementService.getAllInstalledCountByProductName(applications.getApplicationName());
						installCount+=count1;
						int count2=lincencceManagementService.getAllLicenceCountByProductName(applications.getApplicationName());
						saasCount+=count2;
				}
					names.add(category.getCategoryName());
					installCounts.add(installCount);
					saasCounts.add(saasCount);
		}
		graphDTO.setNames(names);
		graphDTO.setInstalled(installCounts);
		graphDTO.setSaas(saasCounts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return graphDTO;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/getBundleWiseInstallLicenceList", method = RequestMethod.GET)
	public @ResponseBody List<TableCount> getBundleWiseInstallLicenceList() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Bundle> bundles =bundleAndCategoryService.getAllBundles();
		
		for(Bundle bundle:bundles){
			int allCount=0;
			List<BundleApplications> bundleApplications=bundleAndCategoryService.getApplicationByBundleId(bundle.getBundleId());
				for (BundleApplications applications:bundleApplications) {

					int count1=lincencceManagementService.getAllInstalledCountByProductName(applications.getApplicationName());
					allCount+=count1;
					System.out.println("Application Name :: "+applications.getApplicationName()+" = "+count1);
					
				}
				TableCount tableCount= new TableCount();
				tableCount.setCount(allCount);
				tableCount.setName(bundle.getBundleName());
				lists.add(tableCount);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	@RequestMapping(value = "/getCategoryWiseInstallLicenceList", method = RequestMethod.GET)
	public @ResponseBody List<TableCount> getCategoryWiseInstallLicenceList() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Category> categories =bundleAndCategoryService.getAllCategory();
		
		for(Category category:categories){
			int allcounts=0;
			List<CategoryApplications> categoryApplications=bundleAndCategoryService.getCategoryApplicationsByCategory(category.getCategorId());	
					for (CategoryApplications applications:categoryApplications) {
						
						
						int count1=lincencceManagementService.getAllInstalledCountByProductName(applications.getApplicationName());
						allcounts+=count1;
					
				}
					TableCount tableCount= new TableCount();
					tableCount.setCount(allcounts);
					tableCount.setName(category.getCategoryName());
					lists.add(tableCount);
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	
	
	@RequestMapping(value = "/getBundleWiseSAASLicenceList", method = RequestMethod.GET)
	public @ResponseBody List<TableCount> getBundleWiseSAASLicenceList() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Bundle> bundles =bundleAndCategoryService.getAllBundles();
		
		for(Bundle bundle:bundles){
			int allCount=0;
			List<BundleApplications> bundleApplications=bundleAndCategoryService.getApplicationByBundleId(bundle.getBundleId());
				for (BundleApplications applications:bundleApplications) {

					int count1=lincencceManagementService.getAllLicenceCountByProductName(applications.getApplicationName());
					allCount+=count1;
					System.out.println("Application Name :: "+applications.getApplicationName()+" = "+count1);
					
				}
				TableCount tableCount= new TableCount();
				tableCount.setCount(allCount);
				tableCount.setName(bundle.getBundleName());
				lists.add(tableCount);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	@RequestMapping(value = "/getCategoryWiseSAASLicenceList", method = RequestMethod.GET)
	public @ResponseBody List<TableCount>  getCategoryWiseSAASLicenceList() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Category> categories =bundleAndCategoryService.getAllCategory();
		
		for(Category category:categories){
			int allcounts=0;
			List<CategoryApplications> categoryApplications=bundleAndCategoryService.getCategoryApplicationsByCategory(category.getCategorId());	
					for (CategoryApplications applications:categoryApplications) {
						
						
						int count1=lincencceManagementService.getAllLicenceCountByProductName(applications.getApplicationName());
						allcounts+=count1;
					
				}
					TableCount tableCount= new TableCount();
					tableCount.setCount(allcounts);
					tableCount.setName(category.getCategoryName());
					lists.add(tableCount);
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/getBundleWiseInstallLicence", method = RequestMethod.GET)
	public @ResponseBody CountDTO getBundleWiseInstallLicence() {
		CountDTO count=new CountDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Bundle> bundles =bundleAndCategoryService.getAllBundles();
		
		for(Bundle bundle:bundles){
			int allCount=0;
			List<BundleApplications> bundleApplications=bundleAndCategoryService.getApplicationByBundleId(bundle.getBundleId());
				for (BundleApplications applications:bundleApplications) {

					int count1=lincencceManagementService.getAllInstalledCountByProductName(applications.getApplicationName());
					allCount+=count1;
					System.out.println("Application Name :: "+applications.getApplicationName()+" = "+count1);
					
				}
				counts.add(allCount);
				names.add(bundle.getBundleName());
		}
		count.setCounts(counts);
		count.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getCategoryWiseInstallLicence", method = RequestMethod.GET)
	public @ResponseBody CountDTO getCategoryWiseInstallLicence() {
		CountDTO count=new CountDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Category> categories =bundleAndCategoryService.getAllCategory();
		
		for(Category category:categories){
			int allcounts=0;
			List<CategoryApplications> categoryApplications=bundleAndCategoryService.getCategoryApplicationsByCategory(category.getCategorId());	
					for (CategoryApplications applications:categoryApplications) {
						
						
						int count1=lincencceManagementService.getAllInstalledCountByProductName(applications.getApplicationName());
						allcounts+=count1;
					
				}
					counts.add(allcounts);
					names.add(category.getCategoryName());
		}
		count.setCounts(counts);
		count.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	
	
	@RequestMapping(value = "/getBundleWiseSAASLicence", method = RequestMethod.GET)
	public @ResponseBody CountDTO getBundleWiseSAASLicence() {
		CountDTO count=new CountDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Bundle> bundles =bundleAndCategoryService.getAllBundles();
		
		for(Bundle bundle:bundles){
			int allCount=0;
			List<BundleApplications> bundleApplications=bundleAndCategoryService.getApplicationByBundleId(bundle.getBundleId());
				for (BundleApplications applications:bundleApplications) {

					int count1=lincencceManagementService.getAllLicenceCountByProductName(applications.getApplicationName());
					allCount+=count1;
					System.out.println("Application Name :: "+applications.getApplicationName()+" = "+count1);
					
				}
				counts.add(allCount);
				names.add(bundle.getBundleName());
		}
		count.setCounts(counts);
		count.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getCategoryWiseSAASLicence", method = RequestMethod.GET)
	public @ResponseBody CountDTO getCategoryWiseSAASLicence() {
		CountDTO count=new CountDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Category> categories =bundleAndCategoryService.getAllCategory();
		
		for(Category category:categories){
			int allcounts=0;
			List<CategoryApplications> categoryApplications=bundleAndCategoryService.getCategoryApplicationsByCategory(category.getCategorId());	
					for (CategoryApplications applications:categoryApplications) {
						
						
						int count1=lincencceManagementService.getAllLicenceCountByProductName(applications.getApplicationName());
						allcounts+=count1;
					
				}
					counts.add(allcounts);
					names.add(category.getCategoryName());
		}
		count.setCounts(counts);
		count.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getOverviewDashboardCount", method = RequestMethod.GET)
	public @ResponseBody DashboardOverviewCountDto getOverviewDashboardCount() {
		DashboardOverviewCountDto count=new DashboardOverviewCountDto();
		try {
			int customerSoftwareTotalCount=0;
			int total=assetService.getTotalAssetCount();
			int employeeC=employeeServices.getEmployeeCount();
			Date today=new Date();
			int assing=assetService.getTotalAssignedAssetCount();
			int totalInstock=lincencceManagementService.getSystemLicenceCount();
			int totalSaas=lincencceManagementService.getLicenceCount();
			int assignedSaas=lincencceManagementService.getAssignedSAASLicenceCount();
			int customerSoftware=customerSuppliedSoftwareService.getCustomerSuppliedSoftwareCount();
			int todayFetchCount=softwareRepo.getFetchCountByDate(today);
			if(customerSoftware!=0){
				 customerSoftwareTotalCount=customerSuppliedSoftwareService.getCustomerSuppliedSoftwareTotalCount();
	
			}
			System.out.println("totalInstock "+totalInstock);
			
			
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 30);
			Date nextDate = c.getTime();
			System.out.println("TODAY "+today);
			System.out.println("NEXT DAY "+nextDate);
				int installCount=0;
				int uploadedCount=0;
			List<LicenceExpriry> list=lincencceManagementService.getExpiredLicencesByDate(nextDate);
			for(LicenceExpriry expriry:list){
				//System.out.println("expriry "+expriry.getReleaseName());
				
				List<InstallLicenceStock> installLicenceStocks=lincencceManagementService.getInstallLicenceStockByPublisherProductAndRelease(expriry.getPublisherName(),expriry.getProductName(),expriry.getReleaseName()); 
				System.out.println("Expiring Install Count "+installLicenceStocks.size());
				installCount+=installLicenceStocks.size();
				
				List<Licence> uploadLicenceStocks=lincencceManagementService.getUploadedLicenceStockByPublisherProductAndRelease(expriry.getPublisherName(),expriry.getProductName(),expriry.getReleaseName()); 
				uploadedCount+=uploadLicenceStocks.size();
				System.out.println("Expiring Upload Count -"+uploadLicenceStocks.size());

			
			}
			int endOfLifeInstall=installCount;
			int endOfLifeSAAS=uploadedCount;
			List<Licence> renewal=lincencceManagementService.getTotalRenewalCount(nextDate);
			int renewalCount=renewal.size();
			
			
			count.setAssetCount(total);
			count.setEmployeeCount(employeeC);
			count.setAssignedAssetCount(assing);
			count.setAssignedSaas(assignedSaas);
			count.setCustomerSoftware(customerSoftware);
			count.setCustomerSoftwareTotalCount(customerSoftwareTotalCount);
			count.setEndOfLifeInstall(endOfLifeInstall);
			count.setEndOfLifeSAAS(endOfLifeSAAS);
			count.setRenewalCount(renewalCount);
			count.setTotalInstock(totalInstock);
			count.setTotalSaas(totalSaas);
			count.setTodayFetchCount(todayFetchCount);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@RequestMapping(value = "/getStatusWiseAsset", method = RequestMethod.GET)
	public @ResponseBody CountDTO getStatusWiseAsset() {
		CountDTO count=new CountDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Asset> assets =assetService.getAllAsset();
		for(Asset asset:assets){
			status.add(asset.getStatus());
		}
		for(String str:status){
			names.add(str);
			int count1=assetService.getAssetsCountByStatus(str);
			counts.add(count1);
		}
		count.setCounts(counts);
		count.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getTypeWiseAsset", method = RequestMethod.GET)
	public @ResponseBody CountDTO getTypeWiseAsset() {
		CountDTO count=new CountDTO();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> types=new HashSet<String>();
		try {
		List<Asset> assets =assetService.getAllAsset();
		for(Asset asset:assets){
			types.add(asset.getAssetType());
		}
		for(String str:types){
			names.add(str);
			int count1=assetService.getAssetsCountByTypes(str);
			counts.add(count1);
		}
		count.setCounts(counts);
		count.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getRenewalLicenceList", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getRenewalLicenceList() {
		List<Licence> licences= new ArrayList<>();
		try {
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 30);
			Date nextDate = c.getTime();
			licences=lincencceManagementService.getTotalRenewalCount(nextDate);
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
			int srNo=1;
			for(Licence licence:licences){
				String strDate = dateFormat.format(licence.getInstalllationDate());  
				String perDate = dateFormat.format(licence.getPurchaseDate());  
				String period=licence.getPeriod()+" "+licence.getLicencePeriodUnit();
				licence.setPurDate(perDate);
				licence.setInstallDate(strDate);
				licence.setPeriod(period);
				licence.setSrNo(srNo);
				srNo++;


			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return licences;
	}
	
	
	@RequestMapping(value = "/getCountOfInstallLicence", method = RequestMethod.GET)
	public @ResponseBody int getCountOfInstallLicence() {
		int count=0;
		try {
			long count1=installLicenceStockRepo.count();
			count=(int) count1;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	@RequestMapping(value = "/getInstalllicencesByPubslisherForGraph", method = RequestMethod.GET)
	public @ResponseBody CountDTO getInstalllicencesByPubslisherForGraph() {
		Set<String> strings = new HashSet<String>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> counts = new ArrayList<Integer>();
		CountDTO dtoRes = new CountDTO();
		try {
			//List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			List<Associate> associates= lincencceManagementService.getAssociates();
			for(Associate associate:associates){
				strings.add(associate.getAssociateName());
			}
			for (String str : strings) {

				int totalCount=lincencceManagementService.getCountOfInstallLicenceByPublisherName(str);
				names.add(str);
				counts.add(totalCount);
			}
			
			dtoRes.setCounts(counts);
			dtoRes.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoRes;
	}
	@RequestMapping(value = "/getInstalllicencesByProductForGraph", method = RequestMethod.GET)
	public @ResponseBody CountDTO getInstalllicencesByProductForGraph() {
		Set<String> strings = new HashSet<String>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> count = new ArrayList<Integer>();
		CountDTO dtoRes = new CountDTO();
		try {
			//List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			List<Product> products= lincencceManagementService.getProducts();
			for(Product product:products){
				strings.add(product.getProductName());
			}
			for (String str : strings) {

				int totalCount=lincencceManagementService.getCountOfInstallLicenceByProductName(str);
				names.add(str);
				count.add(totalCount);
			}
			
			dtoRes.setCounts(count);
			dtoRes.setNames(names);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoRes;
	}

	@RequestMapping(value = "/getOSWiseCount", method = RequestMethod.GET)
	public @ResponseBody CountDTO getOSWiseCount() {
		Set<String> osNames= new HashSet<String>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		CountDTO osCountDTO= new CountDTO();
		try {
			List<OSDetials> oslist=oSDetialsRepo.findAll();
			for(OSDetials detials:oslist){
				osNames.add(detials.getName());
			}
			for(String name:osNames){
				CountDTO oSCountDTO =new CountDTO();
				int count=oSDetialsRepo.getCountOFOSDetialsByOSName(name);
			
				names.add(name);
				counts.add(count);
			}
			osCountDTO.setCounts(counts);
			osCountDTO.setNames(names);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return osCountDTO;
	}
	@RequestMapping(value = "/getCPUWiseCount", method = RequestMethod.GET)
	public @ResponseBody CountDTO getCPUWiseCount() {
		Set<String> cpunames= new HashSet<String>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		CountDTO osCountDTO= new CountDTO();
		try {
			List<CPUDetials> list=cPUDetialsRepo.findAll();
			for(CPUDetials detials:list){
				cpunames.add(detials.getProcessorName());
			}
			for(String name:cpunames){
				CountDTO oSCountDTO =new CountDTO();
				int count=cPUDetialsRepo.getCountOFCPUDetialsByProcessorName(name);
			
				names.add(name);
				counts.add(count);
			}
			osCountDTO.setCounts(counts);
			osCountDTO.setNames(names);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return osCountDTO;
	}
	
	
	@RequestMapping(value = "/getDataBaseDetial", method = RequestMethod.GET)
	public @ResponseBody List<DBEditionCountDto> getDataBaseDetial() {
		Set<String> meomryNames= new HashSet<String>();
		List<String> mySQLeditions= new ArrayList<String>();
		List<String> msSQLeditions= new ArrayList<String>();
		List<DBEditionCountDto> list= new ArrayList<DBEditionCountDto>();
		CountDTO osCountDTO= new CountDTO();
		try {
			
			List<InstallLicenceStock>  mySQLs=installLicenceStockRepo.getListOfLicencesByProductName("MySQL Server 5.7");
			List<InstallLicenceStock>  msSQLs=installLicenceStockRepo.getListOfLicencesByProductName("Microsoft SQL Server 2019 (64-bit)");

			
			for(InstallLicenceStock stock:mySQLs){
				mySQLeditions.add(stock.getEdition());
			}
			
			for(InstallLicenceStock stock:msSQLs){
				msSQLeditions.add(stock.getEdition());
			}
			
			
			for(String name:mySQLeditions){
				DBEditionCountDto dBEditionCountDto= new DBEditionCountDto();
				int count=installLicenceStockRepo.getCountByEditionAndProductName(name,"MySQL Server 5.7");
				dBEditionCountDto.setDbName("MySQL Server");
				dBEditionCountDto.setEdition(name);
				dBEditionCountDto.setCount(count);
				list.add(dBEditionCountDto);
				
			}
			for(String name:msSQLeditions){
				DBEditionCountDto dBEditionCountDto= new DBEditionCountDto();
				int count=installLicenceStockRepo.getCountByEditionAndProductName(name,"Microsoft SQL Server 2019 (64-bit)");
				dBEditionCountDto.setDbName("Microsoft SQL Server 2019 (64-bit)");
				dBEditionCountDto.setEdition(name);
				dBEditionCountDto.setCount(count);
				list.add(dBEditionCountDto);
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@RequestMapping(value = "/getMemoryWiseCount", method = RequestMethod.GET)
	public @ResponseBody CountDTO getMemoryWiseCount() {
		Set<String> meomryNames= new HashSet<String>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		CountDTO osCountDTO= new CountDTO();
		try {
			
			
			
			List<Double> list=ramTotalRepo.getTotalRams();
			for(Double detials:list){
				meomryNames.add(detials.toString());
				//System.out.println("RAM "+detials);
			}
			for(String name:meomryNames){
				CountDTO oSCountDTO =new CountDTO();
				int count=ramTotalRepo.getCountOFRamBySize(Double.parseDouble(name));
				System.out.println("FOR "+name+" GB :: "+count);
				names.add(name+" GB");
				counts.add(count);
			}
			osCountDTO.setCounts(counts);
			osCountDTO.setNames(names);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return osCountDTO;
	}
	
	
	@RequestMapping(value = "/getPublisherWiseSAASLicenceList", method = RequestMethod.GET)
	public @ResponseBody List<TableCount>  getPublisherWiseSAASLicenceList() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
		List<Associate> associates =lincencceManagementService.getAssociates();
		
		for(Associate associate:associates){
			
			
			int count = lincencceManagementService.getAllLicenceCountByPublisherName(associate.getAssociateName());
			
					TableCount tableCount= new TableCount();
					tableCount.setCount(count);
					tableCount.setName(associate.getAssociateName());
					lists.add(tableCount);
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	@RequestMapping(value = "/getPublisherWiseInstallLicenceList", method = RequestMethod.GET)
	public @ResponseBody List<TableCount>  getPublisherWiseInstallLicenceList() {
		List<TableCount> lists=new ArrayList<TableCount>();
		List<String> names= new ArrayList<String>();
		List<Integer> counts= new ArrayList<Integer>();
		Set<String> status=new HashSet<String>();
		try {
			List<Associate> associates =lincencceManagementService.getAssociates();
			
			for(Associate associate:associates){
				
				
				int count = lincencceManagementService.getInstallLicenceCountByAssociate(associate.getAssociateName());
				
						TableCount tableCount= new TableCount();
						tableCount.setCount(count);
						tableCount.setName(associate.getAssociateName());
						lists.add(tableCount);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	@RequestMapping(value = "/getProductWiseInstallList", method = RequestMethod.GET)
	public @ResponseBody List<TableCount> getProductWiseInstallList() {
		List<TableCount>  list= new ArrayList<TableCount>();
		Set<String> strings = new HashSet<String>();
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Integer> count = new ArrayList<Integer>();
		CountDTO dtoRes = new CountDTO();
		try {
			//List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			List<Product> products= lincencceManagementService.getProducts();
			for(Product product:products){
				strings.add(product.getProductName());
			}
			for (String str : strings) {
				TableCount count2= new TableCount();
				int totalCount=lincencceManagementService.getCountOfInstallLicenceByProductName(str);
				count2.setName(str);
				count2.setCount(totalCount);
				list.add(count2);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
