package com.ZioSet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ZioSet.dto.AssetTypesCountDtoRes;
import com.ZioSet.dto.ExpiringLicenceCountDto;
import com.ZioSet.dto.LicenceAssociationGraph;
import com.ZioSet.dto.LicenceByPubslisherDto;
import com.ZioSet.dto.LicenceCountDto;
import com.ZioSet.dto.LicenceDashboardCountDto;
import com.ZioSet.dto.RequestObj;
import com.ZioSet.dto.ResponceObject;
import com.ZioSet.dto.Status;
import com.ZioSet.model.Asset;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.AssetLicence;
import com.ZioSet.model.Associate;
import com.ZioSet.model.Branch;
import com.ZioSet.model.Department;
import com.ZioSet.model.Employee;
import com.ZioSet.model.ExpiryUpdate;
import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.Licence;
import com.ZioSet.model.LicenceExpriry;
import com.ZioSet.model.LicenceLifeNotification;
import com.ZioSet.model.LicenceTypes;
import com.ZioSet.model.Product;
import com.ZioSet.model.Software;
import com.ZioSet.Repo.AssociateRepo;
import com.ZioSet.Repo.BranchRepo;
import com.ZioSet.Repo.ExpiryUpdateRepo;
import com.ZioSet.Repo.InstallLicenceStockRepo;
import com.ZioSet.Repo.LincencceRepo;
import com.ZioSet.Repo.ProductRepo;
import com.ZioSet.Service.AssetEmployeeMappeServices;
import com.ZioSet.Service.AssetService;
import com.ZioSet.Service.LincencceManagementService;

@RestController
@CrossOrigin("*")
@RequestMapping("licence")
public class LincencceManagementController {
	@Autowired
	LincencceManagementService lincencceManagementService;
	
	@Autowired
	AssetEmployeeMappeServices assetEmployeeMappeServices;
	
	
	@Autowired
	AssetService assetService;
	@Autowired
	BranchRepo branchRepo;
	
	@Autowired
	ExpiryUpdateRepo expiryUpdateRepo;
	@Autowired
	AssociateRepo associateRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	LincencceRepo lincencceRepo;
	@Autowired
	InstallLicenceStockRepo installLicenceStockRepo;
	
	@RequestMapping(value = "/getAllLicenceByDuplicateProductByAssetId", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getAllLicenceByDuplicateProductByAssetId(@RequestParam("assetId") int assetId) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Asset> assets= assetService.getAllAsset();
			List<Product> products=productRepo.findAll();
			
				for(Product  product:products){
					List<InstallLicenceStock> lis2= installLicenceStockRepo.getInstallLicenceStockebyAssetIdAndProduct(assetId,product.getId());
					if(lis2.size()>1){
						System.out.println("Duplicatiee");
						list.addAll(lis2);
					}
				}
			
			int srNo=1;
			for(InstallLicenceStock  installLicenceStock:list){
				installLicenceStock.setSrNo(srNo);
				srNo++;
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getAllLicenceByDuplicateProduct", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getAllLicenceByDuplicateProduct() {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Asset> assets= assetService.getAllAsset();
			List<Product> products=productRepo.findAll();
			for(Asset  asset:assets){
				for(Product  product:products){
					List<InstallLicenceStock> lis2= installLicenceStockRepo.getInstallLicenceStockebyAssetIdAndProduct(asset.getId(),product.getId());
					if(lis2.size()>1){
						System.out.println("Duplicatiee");
						list.addAll(lis2);
					}
				}
			}
			int srNo=1;
			for(InstallLicenceStock  installLicenceStock:list){
				installLicenceStock.setSrNo(srNo);
				srNo++;
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/AssociatedAndCountByAssetId", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> AssociatedAndCount(@RequestParam("assetId") int assetId) {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> associtesStr= new  HashSet<String>();

		try {	
			List<InstallLicenceStock> associates= installLicenceStockRepo.getInstallLicenceStockebyAssetId(assetId);
			
			for(InstallLicenceStock  installLicenceStock:associates){
				associtesStr.add(installLicenceStock.getAssociate().getAssociateName());
			} 
			for(String str:associtesStr){
				LicenceCountDto licenceCountDto= new LicenceCountDto();
				int count=installLicenceStockRepo.getCountOfInstallLicenceStock(str);
				licenceCountDto.setLicenceType(str);
				licenceCountDto.setCount(count);
				list.add(licenceCountDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/AssociatedAndCount", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> AssociatedAndCount() {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> associtesStr= new  HashSet<String>();

		try {	
			List<Associate> associates= associateRepo.findAll();
			
			for(Associate associate:associates){
				associtesStr.add(associate.getAssociateName());
			} 
			for(String str:associtesStr){
				LicenceCountDto licenceCountDto= new LicenceCountDto();
				int count=installLicenceStockRepo.getCountOfInstallLicenceStock(str);
				licenceCountDto.setLicenceType(str);
				licenceCountDto.setCount(count);
				list.add(licenceCountDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getProductWithCountByAssociates", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getProductWithCountByAssociates(@RequestParam("associateName") String associateName) {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<InstallLicenceStock> products= installLicenceStockRepo.getAllInstalledLicencceReportByAssociate(associateName);
			
			for(InstallLicenceStock product:products){
				types.add(product.getProduct().getProductName());
			} 
			for(String str:types){
				LicenceCountDto licenceCountDto= new LicenceCountDto();
				int count =installLicenceStockRepo.getCountOfInstallLicenceByProductName(str);
				licenceCountDto.setAssociateName(str);
				licenceCountDto.setCount(count);
				list.add(licenceCountDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getInstallLicenceBYProductNameProduct", method = RequestMethod.POST)
	public @ResponseBody List<InstallLicenceStock> getInstallLicenceBYProductNameProduct(@RequestBody RequestObj  requestObj) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();
		Set<String> types= new  HashSet<String>();

		try {	
			list= installLicenceStockRepo.getListOfLicencesByProductName(requestObj.getInputStr());
			int srNo=1;
			for(InstallLicenceStock installLicenceStock:list){
				installLicenceStock.setSrNo(srNo);
				srNo++;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/addExpiryUpdate", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addExpiryUpdate(@RequestBody ExpiryUpdate expiryUpdate) {
		ResponceObject responceObject =new ResponceObject();
		try {
			
			expiryUpdate.setExistingExpiryDate(expiryUpdate.getLicence().getLicenceExpiryDate());
			expiryUpdate.setNewExpiryDate(expiryUpdate.getNewExpDate());
			Licence licence= expiryUpdate.getLicence();
			licence.setLicenceExpiryDate(expiryUpdate.getNewExpDate());
			lincencceRepo.save(licence);
			expiryUpdateRepo.save(expiryUpdate);
			responceObject.setCode(200);
			responceObject.setMessage("Expiry Updated ......... Successfully");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	
	@RequestMapping(value = "/getAllPublisher", method = RequestMethod.GET)
	public @ResponseBody List<Associate> getAllPublisher() {
		List<Associate> list= new  ArrayList<Associate>();
		try {	
			
				list=associateRepo.findAll();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAllProduct", method = RequestMethod.GET)
	public @ResponseBody List<Product> getAllProduct() {
		List<Product> list= new  ArrayList<Product>();
		try {	
			
				list=productRepo.findAll();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getLicenceByPublisherAndProduct", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getLicenceByPublisherAndProduct(@RequestParam("publisher") int publisher,@RequestParam("product") int product) {
		List<Licence> list= new  ArrayList<Licence>();
		try {	
			
				list=lincencceRepo.getAllLicenceByPublisherAndProduct(publisher,product);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getExpiringLicencesSAASPagination/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getExpiringLicencesSAASPagination(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Licence> list= new  ArrayList<Licence>();
		try {	
			
				list=lincencceManagementService.getExpiringLicencesSAASPagination(page_no,item_per_page);
			for(Licence licence:list){
				Optional<AssetLicence> optional= lincencceManagementService.getAssetLicenceByLicence(licence.getId());
				if(optional.isPresent()){
					licence.setAsset(optional.get().getAsset());
				}
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getExpiringLicencesSAASSearchPagination", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getExpiringLicencesSAASSearchPagination(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Licence> list= new  ArrayList<Licence>();
		try {	
			
			list=lincencceManagementService.getExpiringLicencesSAASSearchPagination(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllCountExpiringLicencesSAAS", method = RequestMethod.GET)
	public @ResponseBody int  getAllCountExpiringLicencesSAAS() {
		int  count= 0;
		try {
			count= lincencceManagementService.getAllCountExpiringLicencesSAAS();

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getSearchCountExpiringLicencesSAAS", method = RequestMethod.GET)
	public @ResponseBody int  getSearchCountExpiringLicencesSAAS(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= lincencceManagementService.getSearchCountExpiringLicencesSAAS(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getAvailanleLicenceExpiryPagination/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<LicenceExpriry> getAvailanleLicenceExpiryPagination(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<LicenceExpriry> list= new  ArrayList<LicenceExpriry>();
		try {	
			
				list=lincencceManagementService.getAvailanleLicenceExpiryPagination(page_no,item_per_page);
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAvailanleLicenceExpirySearchPagination", method = RequestMethod.GET)
	public @ResponseBody List<LicenceExpriry> getAvailanleLicenceExpirySearchPagination(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<LicenceExpriry> list= new  ArrayList<LicenceExpriry>();
		try {	
			
			list=lincencceManagementService.getAvailanleLicenceExpirySearchPagination(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllCountAvailanleLicenceExpiry", method = RequestMethod.GET)
	public @ResponseBody int  getAllCountAvailanleLicenceExpiry() {
		int  count= 0;
		try {
			count= lincencceManagementService.getAllCountAvailanleLicenceExpiry();

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getSearchCountAvailanleLicenceExpiry", method = RequestMethod.GET)
	public @ResponseBody int  getSearchCountAvailanleLicenceExpiry(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= lincencceManagementService.getSearchCountAvailanleLicenceExpiry(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/getTodaysFetchLicenceByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Software> getTodaysFetchLicenceByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Software> list= new  ArrayList<Software>();
		try {	
			Date date= new Date();
				list=lincencceManagementService.getTodaysFetchLicenceByLimit(page_no,item_per_page,date);
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getTodayFetchInstallLicenceCount", method = RequestMethod.GET)
	public @ResponseBody int  getTodayFetchInstallLicenceCount() {
		int  count= 0;
		try {
			Date date= new Date();
			count= lincencceManagementService.getTodayFetchInstallLicenceCount(date);

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	@RequestMapping(value = "/getCounrOfExpiringLicencce", method = RequestMethod.GET)
	public @ResponseBody ExpiringLicenceCountDto  getListOfExpiringLicencce() {
		ExpiringLicenceCountDto count= new ExpiringLicenceCountDto();
		int  uploadedCount = 0;
		int  installCount = 0;
		try {
			Date today=new Date();
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 30);
			Date nextDate = c.getTime();
			System.out.println("TODAY "+today);
			System.out.println("NEXT DAY "+nextDate);
			
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
			count.setInstallCount(installCount);
			count.setUploadedCount(uploadedCount);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getEOLInstall", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock>  getListOfInstalledExpiringLicencce() {
		List<InstallLicenceStock> licenceStocks= new ArrayList<InstallLicenceStock>();
	
		try {
			Date today=new Date();
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 30);
			Date nextDate = c.getTime();
			System.out.println("TODAY "+today);
			System.out.println("NEXT DAY "+nextDate);
			
			List<LicenceExpriry> list=lincencceManagementService.getExpiredLicencesByDate(nextDate);
			System.out.println("SIZE "+list.size());

			for(LicenceExpriry expriry:list){
				System.out.println("expriry "+expriry.toString());
				
				List<InstallLicenceStock> installLicenceStocks=lincencceManagementService.getInstallLicenceStockByPublisherProductAndRelease(expriry.getPublisherName(),expriry.getProductName(),expriry.getReleaseName()); 
				System.out.println("Expiring Install Count "+installLicenceStocks.size());
				for(InstallLicenceStock installLicenceStock:installLicenceStocks){
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
					String expDate = dateFormat.format(expriry.getRetirementDate());  
					installLicenceStock.setExpiryDate(expDate);
					licenceStocks.add(installLicenceStock);
				}
				
				
			}
			int srNo=1;
			for(InstallLicenceStock installLicenceStock:licenceStocks){
				DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");  
				String expDate = dateFormat.format(installLicenceStock.getExpiryDate());  
				String installDate = dateFormat.format(installLicenceStock.getInstallDate());  
				installLicenceStock.setSrNo(srNo);
				installLicenceStock.setInsDate(installDate);
				srNo++;

			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return licenceStocks;
	}
	@RequestMapping(value = "/getEOLSAAS", method = RequestMethod.GET)
	public @ResponseBody List<Licence>  getEOLSAAS() {
		List<Licence> licenceStocks= new ArrayList<Licence>();
	
		try {
			Date today=new Date();
			Date dt = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(dt); 
			c.add(Calendar.DATE, 30);
			Date nextDate = c.getTime();
			System.out.println("TODAY "+today);
			System.out.println("NEXT DAY "+nextDate);
			
			List<LicenceExpriry> list=lincencceManagementService.getExpiredLicencesByDate(nextDate);
			System.out.println("list  "+list.size());
			for(LicenceExpriry expriry:list){
				
				List<Licence> uploadLicenceStocks=lincencceManagementService.getUploadedLicenceStockByPublisherProductAndRelease(expriry.getPublisherName(),expriry.getProductName(),expriry.getReleaseName()); 
				System.out.println("Expiring Uploaded Count "+uploadLicenceStocks.size());
				for(Licence licence:uploadLicenceStocks){
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
					String expDate = dateFormat.format(expriry.getRetirementDate());  
					licence.setExpDate(expDate);
					licenceStocks.add(licence);
				}
				
				
			}
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return licenceStocks;
	}
	@RequestMapping(value = "/addLicenceExpriry", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addLicenceExpriry(@RequestBody LicenceExpriry licenceExpriry) {
		ResponceObject responceObject =new ResponceObject();
		try {
				Optional<LicenceExpriry> optional=lincencceManagementService.getLicenceExpiryByPublisherProductAndRelease(licenceExpriry.getPublisherName(),licenceExpriry.getProductName(),licenceExpriry.getReleaseName());
			System.out.println("Publisher "+licenceExpriry.getPublisherName());
			System.out.println("Product "+licenceExpriry.getProductName());
			System.out.println("Release "+licenceExpriry.getReleaseName());
				
				if(optional.isPresent()){
				responceObject.setCode(500);
				responceObject.setMessage("Licence Expriry already Exits");

			}else{
				lincencceManagementService.addLicenceExpriry(licenceExpriry);
				responceObject.setCode(200);
				responceObject.setMessage("Licence Expriry added .......... Successfully !!!");
				
				lincencceManagementService.addLicenceExpriry(licenceExpriry);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/getSystemLincencceByAssetId", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getSystemLincencceByAssetId(@RequestParam("assetId") int assetId) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();
		try {	
			
				list=lincencceManagementService.getSystemLincencceByAssetId(assetId);
			int srNo=1;
			for(InstallLicenceStock installLicenceStock : list){
				installLicenceStock.setSrNo(srNo);
				srNo++;
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getSystemLincencceByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getSystemLincencceByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();
		try {	
			
				list=lincencceManagementService.getSystemLincencceByLimit(page_no,item_per_page);
			
			for(InstallLicenceStock installLicenceStock : list){
				Optional<AssetEmployeeAssigned>  optional= assetEmployeeMappeServices.getAllocationByAsset(installLicenceStock.getAsset().getId());
				if(optional.isPresent()){
					installLicenceStock.setEmployee(optional.get().getEmployee());
				}
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getSystemLicenceByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getSystemLicenceByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();
		try {	
			
			list=lincencceManagementService.getSystemLicenceByLimitAndSearch(searchText,pageNo,perPage);
			for(InstallLicenceStock installLicenceStock : list){
				Optional<AssetEmployeeAssigned>  optional= assetEmployeeMappeServices.getAllocationByAsset(installLicenceStock.getAsset().getId());
				if(optional.isPresent()){
					installLicenceStock.setEmployee(optional.get().getEmployee());
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getSystemLicenceCount", method = RequestMethod.GET)
	public @ResponseBody int  getSystemLicenceCount() {
		int  count= 0;
		try {
			count= lincencceManagementService.getSystemLicenceCount();

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getSystemLicenceCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getSystemLicenceCountAndSearch(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= lincencceManagementService.getSystemLicenceCount(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/getAssetUnalllocatedLicence", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAssetUnalllocatedLicence() {
		List<Asset> list= new  ArrayList<Asset>();
		try {	
			list= assetService.getAllAsset();
			/*for(Asset asset:assets){
				List<AssetLicence> optional  =lincencceManagementService.getAssetLicenceByAssetId(asset.getId());
			}
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAllInstalledLicenceName", method = RequestMethod.GET)
	public @ResponseBody Set<String> getAllInstalledLicenceName() {
		Set<String> list= new  HashSet<String>();

		try {	
			List<InstallLicenceStock> installLicencces= lincencceManagementService.getAllInstallLicenceStocks();
			int srNo=1;
			for(InstallLicenceStock licence:installLicencces){
				list.add(licence.getProduct().getProductName());
				
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@RequestMapping(value = "/getAllInstallLicenceStocks", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getAllInstallLicenceStocks() {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();

		try {	
			list= lincencceManagementService.getAllInstallLicenceStocks();
			int srNo=1;
			for(InstallLicenceStock licence:list){
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				if(licence.getInstallDate()!=null){
					String instDate = dateFormat.format(licence.getInstallDate()); 
					licence.setInsDate(instDate);

				}
				
				String exDate = dateFormat.format(licence.getDetectedDate()); 
				licence.setDetDate(exDate);
				srNo++;
				
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllInstallLicenceStocksBySerialNo", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNo(@RequestParam("serialNo") String serialNo) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();

		try {	
			list= lincencceManagementService.getAllInstallLicenceStocksBySerialNo(serialNo);
			int srNo=1;
			for(InstallLicenceStock licence:list){
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				String instDate = dateFormat.format(licence.getInstallDate());  
				String exDate = dateFormat.format(licence.getDetectedDate()); 
				licence.setInsDate(instDate);
			
				srNo++;
				
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllInstallLicenceStocksBySerialNoAnsAssociate", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAnsAssociate(@RequestParam("serialNo") String serialNo,@RequestParam("associateName") String associateName) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();

		try {	
			list= lincencceManagementService.getAllInstallLicenceStocksBySerialNoAnsAssociate(serialNo,associateName);
			int srNo=1;
			for(InstallLicenceStock licence:list){
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				String instDate = dateFormat.format(licence.getInstallDate());  
				String exDate = dateFormat.format(licence.getDetectedDate()); 
				licence.setInsDate(instDate);
			
				srNo++;
				
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct", method = RequestMethod.GET)
	public @ResponseBody List<InstallLicenceStock> getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct(@RequestParam("serialNo") String serialNo,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName) {
		List<InstallLicenceStock> list= new  ArrayList<InstallLicenceStock>();

		try {	
			list= lincencceManagementService.getAllInstallLicenceStocksBySerialNoAndAssociateAndProduct(serialNo,associateName,productName);
			int srNo=1;
			for(InstallLicenceStock licence:list){
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				String instDate = dateFormat.format(licence.getInstallDate());  
				String exDate = dateFormat.format(licence.getDetectedDate()); 
				licence.setInsDate(instDate);
			
				srNo++;
				
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllExpiryLicences", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllExpiryLicences() {
		List<Licence> list= new  ArrayList<Licence>();

		try {	
			list= lincencceManagementService.getAllExpiryLicences();
			int srNo=0;
			for(Licence licence:list){
				srNo++;
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				if(licence.getInstalllationDate()!=null){
					String instDate = dateFormat.format(licence.getInstalllationDate());  
					licence.setInstallDate(instDate);

				}
				if(licence.getPurchaseDate()!=null){
					String purDate = dateFormat.format(licence.getPurchaseDate()); 
					licence.setPurDate(purDate);


				}
				if(licence.getLicenceExpiryDate()!=null){
					String exDate = dateFormat.format(licence.getLicenceExpiryDate()); 
					licence.setExpDate(exDate);
				}
				
				
				
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllExpiryLicencesByTypes", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllExpiryLicencesByTypes(@RequestParam("type") String type) {
		List<Licence> list= new  ArrayList<Licence>();

		try {	
			list= lincencceManagementService.getAllExpiryLicencesByTypes(type);
			int srNo=1;
			for(Licence licence:list){
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				String purDate = dateFormat.format(licence.getPurchaseDate()); 
				String instDate = dateFormat.format(licence.getInstalllationDate());  
				String exDate = dateFormat.format(licence.getLicenceExpiryDate()); 
				licence.setPurDate(purDate);
				licence.setInstallDate(instDate);
				licence.setExpDate(exDate);
				srNo++;
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllExpiryLicencesByTypesAndAssociation", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllExpiryLicencesByTypesAndAssociation(@RequestParam("type") String type,@RequestParam("association") String association) {
		List<Licence> list= new  ArrayList<Licence>();

		try {	
			list= lincencceManagementService.getAllExpiryLicencesByTypesAndAssociation(type,association);
			int srNo=1;
			for(Licence licence:list){
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				String purDate = dateFormat.format(licence.getPurchaseDate()); 
				String instDate = dateFormat.format(licence.getInstalllationDate());  
				String exDate = dateFormat.format(licence.getLicenceExpiryDate()); 
				licence.setPurDate(purDate);
				licence.setInstallDate(instDate);
				licence.setExpDate(exDate);
				srNo++;
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllExpiryLicencesByTypesAndAssociationAndProduct", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllExpiryLicencesByTypesAndAssociationAndProduct(@RequestParam("type") String type,@RequestParam("association") String association,@RequestParam("product") String product) {
		List<Licence> list= new  ArrayList<Licence>();

		try {	
			list= lincencceManagementService.getAllExpiryLicencesByTypesAndAssociationAndProduct(type,association,product);
			int srNo=1;
			for(Licence licence:list){
				licence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
				String purDate = dateFormat.format(licence.getPurchaseDate()); 
				String instDate = dateFormat.format(licence.getInstalllationDate());  
				String exDate = dateFormat.format(licence.getLicenceExpiryDate()); 
				licence.setPurDate(purDate);
				licence.setInstallDate(instDate);
				licence.setExpDate(exDate);
				srNo++;
			} 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getlicencesStockByPubslisher", method = RequestMethod.GET)
	public @ResponseBody List<LicenceByPubslisherDto> getlicencesStockByPubslisher() {
		List<LicenceByPubslisherDto> list= new  ArrayList<LicenceByPubslisherDto>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Associate> associates= lincencceManagementService.getAssociates();
			
			for(Associate associate:associates){
				LicenceByPubslisherDto byPubslisherDto= new LicenceByPubslisherDto();
				int totalCount=lincencceManagementService.getAllLicenceCountByPublisherName(associate.getAssociateName());
				int installCount=lincencceManagementService.getInstallLicenceCountByAssociate(associate.getAssociateName());
				//int inStockCount=lincencceManagementService.getInStockLicenceCountByPublisherName(str);
				byPubslisherDto.setTotal(totalCount);;
				byPubslisherDto.setInstall(installCount);;
				byPubslisherDto.setTotal(totalCount);
				byPubslisherDto.setPublisher(associate.getAssociateName());
				list.add(byPubslisherDto);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getlicencesStockByProduct", method = RequestMethod.GET)
	public @ResponseBody List<LicenceByPubslisherDto> getlicencesStockByProduct() {
		List<LicenceByPubslisherDto> list= new  ArrayList<LicenceByPubslisherDto>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Product> products= lincencceManagementService.getProducts();
			
			for(Product product:products){
				LicenceByPubslisherDto byPubslisherDto= new LicenceByPubslisherDto();
				int totalCount=lincencceManagementService.getAllLicenceCountByProductName(product.getProductName());
				int installCount=lincencceManagementService.getInstallLicenceCountByProduct(product.getProductName());
				//int inStockCount=lincencceManagementService.getInStockLicenceCountByPublisherName(str);
				byPubslisherDto.setTotal(totalCount);;
				byPubslisherDto.setInstall(installCount);;
				byPubslisherDto.setTotal(totalCount);
				byPubslisherDto.setProduct(product.getProductName());
				list.add(byPubslisherDto);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getlicencesCountsByAssociate", method = RequestMethod.GET)
	public @ResponseBody LicenceAssociationGraph getlicencesCountsByAssociate() {
		LicenceAssociationGraph licenceAssociationGraph= new  LicenceAssociationGraph();
		Set<String> types= new  HashSet<String>();
		Set<String> associtaes= new  HashSet<String>();
		Set<Integer> counts= new  HashSet<Integer>();

		try {	
			List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			
			for(Licence licence:allLicencce){
				types.add(licence.getAssociate().getAssociateName());
			} 
			

			for(String str:types){
				associtaes.add(str);
				int count=lincencceManagementService.getLicenceCountByAssociteName(str);
				counts.add(count);
			}
			licenceAssociationGraph.setAssociateName(associtaes);
			licenceAssociationGraph.setCount(counts);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return licenceAssociationGraph;
	}
	
	@RequestMapping(value = "/getAllEndOfLifeLicence", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllEndOfLifeLicence() {
		List<Licence> listRes=  new ArrayList<Licence>();
		Set<String> assetTypes=new HashSet<String>();
 		try {	
 			List<Licence> list= lincencceManagementService.getAllLicence();
			for(Licence licence:list){
				assetTypes.add(licence.getLicenceType());
			}
			
			for(String type:assetTypes){
				System.out.println("TYPE "+type);
				List<Licence> licences=lincencceManagementService.getInstalledLicenceByType(type);
				Optional<LicenceLifeNotification> assetLife=lincencceManagementService.getActiveLicenceLifeNotificationByType(type);
					if(assetLife.isPresent()){
						for(Licence licence:licences){
							
							//System.out.println("licence  "+licence.toString());
							
							Date installationDate=licence.getInstalllationDate();
							Date expiryDate=licence.getLicenceExpiryDate();
							
							 Date today = new Date();
							  Calendar cal1 = Calendar.getInstance();
							  cal1.setTime(today);
							  cal1.add(Calendar.DAY_OF_YEAR, assetLife.get().getNotificationBeforeDays());
							  Date newToday = cal1.getTime();
							  System.out.println("TODAY  "+newToday);

							  System.out.println("Expiry  "+expiryDate);
							  
							  
							  
							  
							  System.out.println("Compair "+expiryDate.compareTo(newToday));
							  if(expiryDate.compareTo(newToday)!=1){
								  listRes.add(licence);
							  }
							  
							
							
						}
					}
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRes;
	}
	@RequestMapping(value = "/getAssociateWithCountByType", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getAssociateWithCountByType(@RequestParam("type") String type) {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Licence> allLicencce= lincencceManagementService.getAllLicenceByType(type);
			
			for(Licence licence:allLicencce){
				types.add(licence.getAssociate().getAssociateName());
			} 
			for(String str:types){
				LicenceCountDto licenceCountDto= new LicenceCountDto();
				List<Licence> licences=lincencceManagementService.getAllLicenceByAssociateANdType(str,type);
				licenceCountDto.setAssociateName(str);
				licenceCountDto.setCount(licences.size());
				list.add(licenceCountDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getProductNameWithCountByTypeAssociate", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getProductNameWithCountByTypeAssociate(@RequestParam("type") String type,@RequestParam("associateName")String associateName) {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Licence> allLicence= lincencceManagementService.getProductNameByTypeAndAssociate(type,associateName);
			
			for(Licence licence:allLicence){
				types.add(licence.getProduct().getProductName());
			} 
			for(String productName:types){
				LicenceCountDto licenceCountDto= new LicenceCountDto();
				List<Licence> licences=lincencceManagementService.getAllLicencceByTypeAndAssociateAndProduct(type,associateName,productName);
				licenceCountDto.setProductName(productName);
				licenceCountDto.setCount(licences.size());
				list.add(licenceCountDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	@RequestMapping(value = "/addLicenceLifeNotification", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addLicenceLifeNotification(@RequestBody LicenceLifeNotification licenceLifeNotification ) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			List<LicenceLifeNotification> licenceLifeNotifications=lincencceManagementService.getAllLicenceLifeNotificationByType(licenceLifeNotification.getType());
				for(LicenceLifeNotification lifeNotification:licenceLifeNotifications){
					 lifeNotification.setActive(0);
					 lincencceManagementService.addLicenceLifeNotification(lifeNotification);
					 
				}
				
				licenceLifeNotification.setActive(1);
				
				
				lincencceManagementService.addLicenceLifeNotification(licenceLifeNotification);
			
			
			responceObject.setCode(200);
			responceObject.setMessage("Licence Life Notification added .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/changeLicenceLifeStatus", method = RequestMethod.POST)
	public @ResponseBody ResponceObject changeLicenceLifeStatus(@RequestBody LicenceLifeNotification licenceLifeNotification ) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			
			if(licenceLifeNotification.getActive()==0){
				List<LicenceLifeNotification> licenceLifeNotifications=lincencceManagementService.getAllLicenceLifeNotificationByType(licenceLifeNotification.getType());
				for(LicenceLifeNotification lifeNotification:licenceLifeNotifications){
					 lifeNotification.setActive(0);
					 lincencceManagementService.addLicenceLifeNotification(lifeNotification);
					 
				}
				
				licenceLifeNotification.setActive(1);
				
				
			}else{
				licenceLifeNotification.setActive(0);
			}
			
				lincencceManagementService.addLicenceLifeNotification(licenceLifeNotification);
			
			
			responceObject.setCode(200);
			responceObject.setMessage("Licence Life Notification added .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	@RequestMapping(value = "/deleteLicenceLifeNotification", method = RequestMethod.POST)
	public @ResponseBody ResponceObject deleteLicenceLifeNotification(@RequestBody LicenceLifeNotification licenceLifeNotification ) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			lincencceManagementService.deleteLicenceLifeNotification(licenceLifeNotification);
			responceObject.setCode(200);
			responceObject.setMessage("Licence Life Notification deleted .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/deleteAssetLicence", method = RequestMethod.POST)
	public @ResponseBody ResponceObject deleteAssetLicence(@RequestBody AssetLicence assetLicence) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			lincencceManagementService.deleteAssetLicence(assetLicence);
			responceObject.setCode(200);
			responceObject.setMessage("Asset Licence deleted .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	@RequestMapping(value = "/getAllLicenceLifeNotification", method = RequestMethod.GET)
	public @ResponseBody List<LicenceLifeNotification> getAllLicenceLifeNotification() {
		List<LicenceLifeNotification> list=  new ArrayList<LicenceLifeNotification>();
		try {	
			list= lincencceManagementService.getAllLicenceLifeNotification();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/addLicenceTypes", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addLicenceTypes(@RequestBody LicenceTypes licenceTypes ) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			lincencceManagementService.addLicenceTypes(licenceTypes);
			responceObject.setCode(200);
			responceObject.setMessage("Type added .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	
	@RequestMapping(value = "/getAssetIdByTypeAndAssociationAndProductNameAndVersion", method = RequestMethod.GET)
	public @ResponseBody Set<String> getAssetIdByTypeAndAssociationAndProductNameAndVersion(@RequestParam("type") String type,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName,@RequestParam("version") String version) {
		Set<String> res=  new HashSet<String>();
		try {	
			List<AssetLicence> allLicence= lincencceManagementService.getAllAssetLicencceByTypeAndAssociateAndProductAndVersion(type,associateName,productName,version);
			for(AssetLicence assetLicence:allLicence){
				res.add(assetLicence.getAsset().getAssetId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	@RequestMapping(value = "/getAllAssetLicencce", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAllAssetLicencce() {
		List<AssetLicence> allLicence=  new ArrayList<AssetLicence>();
		try {	
			 allLicence= lincencceManagementService.getAllAssetLicencce();
			 int srNo=1;
			for(AssetLicence assetLicence:allLicence){
				assetLicence.setSrNo(srNo);
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
				srNo++;
				if(assetLicence.getAssingDate()!=null){
					String strDate = dateFormat.format(assetLicence.getAssingDate()); 
					assetLicence.setAssDate(strDate);
				}
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getAllAssetLicencceByType", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAllAssetLicencceByType(@RequestParam("type") String type) {
		List<AssetLicence> allLicence=  new ArrayList<AssetLicence>();
		try {	
			 allLicence= lincencceManagementService.getAllAssetLicencceByType(type);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getAllAssetLicencceByTypeAndAssociate", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAllAssetLicencceByTypeAndAssociate(@RequestParam("type") String type,@RequestParam("associateName") String associateName) {
		List<AssetLicence> allLicence=  new ArrayList<AssetLicence>();
		try {	
			 allLicence= lincencceManagementService.getAllAssetLicencceByTypeAndAssociate(type,associateName);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getAllAssetLicencceByTypeAndAssociateAndProduct", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProduct(@RequestParam("type") String type,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName) {
		List<AssetLicence> allLicence=  new ArrayList<AssetLicence>();
		try {	
			 allLicence= lincencceManagementService.getAllAssetLicencceByTypeAndAssociateAndProduct(type,associateName,productName);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getAllAssetLicencceByTypeAndAssociateAndProductAndVersion", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersion(@RequestParam("type") String type,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName,@RequestParam("version") String version) {
		List<AssetLicence> allLicence=  new ArrayList<AssetLicence>();
		try {	
			 allLicence= lincencceManagementService.getAllAssetLicencceByTypeAndAssociateAndProductAndVersion(type,associateName,productName,version);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId(@RequestParam("type") String type,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName,@RequestParam("version") String version,@RequestParam("assetId") String assetId) {
		List<AssetLicence> allLicence=  new ArrayList<AssetLicence>();
		try {	
			 allLicence= lincencceManagementService.getAllAssetLicencceByTypeAndAssociateAndProductAndVersionAndAssetId(type,associateName,productName,version,assetId);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addProduct(@RequestBody Product product) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			lincencceManagementService.addProduct(product);
			responceObject.setCode(200);
			responceObject.setMessage("Product added .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	public @ResponseBody List<Product> getProducts() {
		List<Product> list= new  ArrayList<Product>();

		try {	
			list= lincencceManagementService.getProducts();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getProductsByName", method = RequestMethod.GET)
	public @ResponseBody Product getProductsByName(@RequestParam("productName") String productName) {
		Product product= new  Product();

		try {	
			Optional<Product> optional= lincencceManagementService.getProductsByName(productName);
			if (optional.isPresent()) {
				product=optional.get();
			}else{
				product =null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}
	
	
	@RequestMapping(value = "/addAssociate", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addAssociate(@RequestBody Associate associate) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			lincencceManagementService.addAssociate(associate);
			responceObject.setCode(200);
			responceObject.setMessage("Associate added .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/getAssociates", method = RequestMethod.GET)
	public @ResponseBody List<Associate> getAssociates() {
		List<Associate> list= new  ArrayList<Associate>();

		try {	
			list= lincencceManagementService.getAssociates();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAssociateByName", method = RequestMethod.GET)
	public @ResponseBody Associate getAssociateByName(@RequestParam("associateName") String associateName) {
		Associate associate= new  Associate();

		try {	
			Optional<Associate> optional= lincencceManagementService.getAssociateByName(associateName);
			if (optional.isPresent()) {
				associate=optional.get();
			}else{
				associate =null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return associate;
	}
	//****************************************Dashboard ************************************************************//
	
	
	@RequestMapping(value = "/getlicencesCounts", method = RequestMethod.GET)
	public @ResponseBody LicenceDashboardCountDto getlicencesCounts() {
		LicenceDashboardCountDto dashboardCountDto= new  LicenceDashboardCountDto();

		try {	
			List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			List<Licence> allocatedLincence=lincencceManagementService.getAllocatedLicence();
			List<Licence> inStockLincence=lincencceManagementService.getInStockLicence();
			dashboardCountDto.setAllocatedLicence(allocatedLincence.size());
			dashboardCountDto.setTotoalLicence(allLicencce.size());
			dashboardCountDto.setInstockCount(inStockLincence.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dashboardCountDto;
	}
	
	@RequestMapping(value = "/getlicencesByPubslisher", method = RequestMethod.GET)
	public @ResponseBody List<LicenceByPubslisherDto> getlicencesByPubslisher() {
		List<LicenceByPubslisherDto> list= new  ArrayList<LicenceByPubslisherDto>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			for(Licence licence:allLicencce){
				types.add(licence.getAssociate().getAssociateName());
			}
			for(String str:types){
				LicenceByPubslisherDto byPubslisherDto= new LicenceByPubslisherDto();
				int totalCount=lincencceManagementService.getAllLicenceCountByPublisherName(str);
				int assignedCount=lincencceManagementService.getAssignedLicenceCountByPublisherName(str);
				int inStockCount=lincencceManagementService.getInStockLicenceCountByPublisherName(str);
				byPubslisherDto.setAssigned(assignedCount);
				byPubslisherDto.setInstock(inStockCount);
				byPubslisherDto.setTotal(totalCount);
				byPubslisherDto.setPublisher(str);
				list.add(byPubslisherDto);
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getlicencesByPubslisherForGraph", method = RequestMethod.GET)
	public @ResponseBody AssetTypesCountDtoRes getlicencesByPubslisherForGraph() {
		Set<String> strings = new HashSet<String>();
		ArrayList<String> types = new ArrayList<String>();
		ArrayList<Integer> data = new ArrayList<Integer>();
		AssetTypesCountDtoRes dtoRes = new AssetTypesCountDtoRes();
		try {
			//List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			List<Associate> allLicencce= lincencceManagementService.getAssociates();
			for(Associate associate:allLicencce){
				strings.add(associate.getAssociateName());
			}
			for (String str : strings) {

				int totalCount=lincencceManagementService.getAllLicenceCountByPublisherName(str);
				types.add(str);
				data.add(totalCount);
			}
			
			dtoRes.setData(data);
			dtoRes.setTypes(types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtoRes;
	}
	
	@RequestMapping(value = "/getAllLicences", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllLicences() {
		
		List<Licence> allLicencce = new ArrayList<Licence>();
		try {
			 allLicencce= lincencceManagementService.getAllLicence();
			int srNo=1;
			for(Licence licence:allLicencce){
				Format formatter = new SimpleDateFormat("dd-MM-yyyy");
				String s = formatter.format(licence.getPurchaseDate());
				licence.setPurDate(s);
				licence.setSrNo(srNo);
				
				srNo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicencce;
	}

	@RequestMapping(value = "/getAssignedLicences", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAssignedLicences() {
		
		List<Licence> allLicencce = new ArrayList<Licence>();
		try {
			 allLicencce= lincencceManagementService.getAllocatedLicence();

			int srNo=1;
			for(Licence licence:allLicencce){
				Format formatter = new SimpleDateFormat("dd-MM-yyyy");
				String s = formatter.format(licence.getPurchaseDate());
				String install = formatter.format(licence.getPurchaseDate());
				licence.setInstallDate(install);
				licence.setPurDate(s);
				licence.setSrNo(srNo);
				
				srNo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicencce;
	}

	@RequestMapping(value = "/getInstockLicences", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getInstockLicences() {
		
		List<Licence> allLicencce = new ArrayList<Licence>();
		try {
			 allLicencce= lincencceManagementService.getInstockLicences();
			 System.out.println("SIZE ..... "+allLicencce.size());
			int srNo=1;
			for(Licence licence:allLicencce){
				Format formatter = new SimpleDateFormat("dd-MM-yyyy");
				String s = formatter.format(licence.getPurchaseDate());
				licence.setPurDate(s);
				licence.setSrNo(srNo);
				
				srNo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicencce;
	}
	
	@RequestMapping(value = "/addLicence", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addLicence(@RequestBody Licence licence) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			lincencceManagementService.addLicence(licence);
			responceObject.setCode(200);
			responceObject.setMessage("Licence added .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/getLincencceByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getLincencceByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<Licence> list= new  ArrayList<Licence>();
		try {	
			
				list=lincencceManagementService.getLincencceByLimit(page_no,item_per_page);
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getLicenceByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getLicenceByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<Licence> list= new  ArrayList<Licence>();
		try {	
			
			list=lincencceManagementService.getLicenceByLimitAndSearch(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getLicenceCount", method = RequestMethod.GET)
	public @ResponseBody int  getLicenceCount() {
		int  count= 0;
		try {
			count= lincencceManagementService.getLicenceCount();

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getLicenceCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getLicenceCountAndSearch(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= lincencceManagementService.getLicenceCountAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	
	
	@RequestMapping(value = "/getLicenceTypeWiseCount", method = RequestMethod.GET)
	public @ResponseBody List<LicenceCountDto> getLicenceTypeWiseCount() {
		List<LicenceCountDto> list= new  ArrayList<LicenceCountDto>();
		Set<String> types= new  HashSet<String>();

		try {	
			List<Licence> allLicencce= lincencceManagementService.getAllLicence();
			
			for(Licence licence:allLicencce){
				types.add(licence.getLicenceType());
			} 
			for(String str:types){
				LicenceCountDto licenceCountDto= new LicenceCountDto();
				List<Licence> licences=lincencceManagementService.getAllLicenceByType(str);
				licenceCountDto.setLicenceType(str);
				licenceCountDto.setCount(licences.size());
				list.add(licenceCountDto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getLicenceTypes", method = RequestMethod.GET)
	public @ResponseBody List<LicenceTypes> getLicenceTypes() {
		List<LicenceTypes> list= new  ArrayList<LicenceTypes>();
		try {	
			list= lincencceManagementService.getLicenceTypes();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getLicenceNameByType", method = RequestMethod.GET)
	public @ResponseBody Set<String> getLicenceNameByType(@RequestParam("type") String type) {
		Set<String> types= new  HashSet<String>();
		try {	
			List<Licence> allLicence= lincencceManagementService.getAllLicenceByType(type);
			
			for(Licence licence:allLicence){
				types.add(licence.getProduct().getProductName());
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}
	@RequestMapping(value = "/getAssocitaeNameByType", method = RequestMethod.GET)
	public @ResponseBody Set<String> getAssocitaeNameByType(@RequestParam("type") String type) {
		Set<String> types= new  HashSet<String>();
		try {	
			List<Licence> allLicence= lincencceManagementService.getAllLicenceByType(type);
			
			for(Licence licence:allLicence){
				types.add(licence.getAssociate().getAssociateName());
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}
	
	@RequestMapping(value = "/getAllLicencceByType", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllLicencceByType(@RequestParam("type") String type) {
		List<Licence> allLicence=  new ArrayList<Licence>();
		try {	
			 allLicence= lincencceManagementService.getAllLicenceByType(type);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getProductNameByTypeAndAssociate", method = RequestMethod.GET)
	public @ResponseBody Set<String> getProductNameByTypeAndAssociate(@RequestParam("type") String type,@RequestParam("associateName") String associateName) {
		Set<String> types= new  HashSet<String>();
		try {	
			List<Licence> allLicence= lincencceManagementService.getProductNameByTypeAndAssociate(type,associateName);
			
			for(Licence licence:allLicence){
				types.add(licence.getProduct().getProductName());
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}
	@RequestMapping(value = "/getAllLicencceByTypeAndAssociate", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllLicencceByTypeAndAssociate(@RequestParam("type") String type,@RequestParam("associateName") String associateName) {
		List<Licence> allLicence=  new ArrayList<Licence>();
		try {	
			 allLicence= lincencceManagementService.getProductNameByTypeAndAssociate(type,associateName);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getVersionByTypeAndAssociate", method = RequestMethod.GET)
	public @ResponseBody Set<String> getVersionByTypeAndAssociate(@RequestParam("type") String type,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName) {
		Set<String> types= new  HashSet<String>();
		try {	
			List<Licence> allLicence= lincencceManagementService.getAllLicencceByTypeAndAssociateAndProduct(type,associateName,productName);
			System.out.println("Type :: "+type+" associate "+associateName+" Product "+productName+" Size "+allLicence.size());
			for(Licence licence:allLicence){
				types.add(licence.getLicenceVersion());
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}
	
	@RequestMapping(value = "/getAllLicencceByTypeAndAssociateAndProduct", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllLicencceByTypeAndAssociateAndProduct(@RequestParam("type") String type,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName) {
		List<Licence> allLicence=  new ArrayList<Licence>();
		try {	
			 allLicence= lincencceManagementService.getAllLicencceByTypeAndAssociateAndProduct(type,associateName,productName);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/getAllLicencceByTypeAndAssociateAndProductAndVersion", method = RequestMethod.GET)
	public @ResponseBody List<Licence> getAllLicencceByTypeAndAssociateAndProductAndVersion(@RequestParam("type") String type,@RequestParam("associateName") String associateName,@RequestParam("productName") String productName,@RequestParam("version") String version) {
		List<Licence> allLicence=  new ArrayList<Licence>();
		try {	
			 allLicence= lincencceManagementService.getAllLicencceByTypeAndAssociateAndProductAndVersion(type,associateName,productName,version);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allLicence;
	}
	@RequestMapping(value = "/LicenceByTypeAndName", method = RequestMethod.GET)
	public @ResponseBody List<Licence> LicenceByTypeAndName(@RequestParam("type") String type,@RequestParam("name") String name) {
		List<Licence> list= new  ArrayList<Licence>();
		try {	
			List<Licence> allLicence= lincencceManagementService.LicenceByTypeAndName(type,name);
			System.out.println("FOR Type :: "+type+"  Name :: "+name+"  :: "+allLicence.size());
			for(Licence licence:allLicence){
				Optional<AssetLicence> optional= lincencceManagementService.getAssetLicenceByLicence(licence.getId());
				if(!optional.isPresent()){
					list.add(licence);
				}
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	

	@RequestMapping(value = "/addAssetLicence", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addAssetLicence(@RequestBody AssetLicence licence) {
		ResponceObject responceObject =new ResponceObject();
		try {
				
			lincencceManagementService.addAssetLicence(licence);
			responceObject.setCode(200);
			responceObject.setMessage("AssetLicence added .......... Successfully !!!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/getAssetLicenceByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAssetLicencByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<AssetLicence> list= new  ArrayList<AssetLicence>();
		try {	
			
				list=lincencceManagementService.getAssetLicenceLincenceByLimit(page_no,item_per_page);
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getAssetLicenceLicenceByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAssetLicenceLicenceByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<AssetLicence> list= new  ArrayList<AssetLicence>();
		try {	
			
			list=lincencceManagementService.getAssetLicenceLicenceByLimitAndSearch(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAssetLicenceLicenceCount", method = RequestMethod.GET)
	public @ResponseBody int  getAssetLicenceLicenceCount() {
		int  count= 0;
		try {
			count= lincencceManagementService.getAssetLicenceLicenceCount();

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getAssetLicenceLicenceCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getAssetLicenceLicenceCountAndSearch(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= lincencceManagementService.getAssetLicenceLicenceCountAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getAssetLicenceByAssetId", method = RequestMethod.GET)
	public @ResponseBody List<AssetLicence> getAssetLicenceByAssetId(@RequestParam("id") int id) {
		List<AssetLicence> list= new  ArrayList<AssetLicence>();
		try {	
			
			list=lincencceManagementService.getAssetLicenceByAssetId(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	@RequestMapping(value = "/getExpiryUpdateByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<ExpiryUpdate> getExpiryUpdateByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<ExpiryUpdate> list= new  ArrayList<ExpiryUpdate>();
		try {	
			
				list=expiryUpdateRepo.getExpiryUpdateByLimit(page_no,item_per_page);
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getExpiryUpdateByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<ExpiryUpdate> getExpiryUpdateByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<ExpiryUpdate> list= new  ArrayList<ExpiryUpdate>();
		try {	
			
			list=expiryUpdateRepo.getExpiryUpdateByLimitAndSearch(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getExpiryUpdateCount", method = RequestMethod.GET)
	public @ResponseBody int  getExpiryUpdateCount() {
		int  count= 0;
		try {
			count= expiryUpdateRepo.getExpiryUpdateCount();

			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getExpiryUpdateCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getExpiryUpdateCountAndSearch(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= expiryUpdateRepo.getExpiryUpdateCountAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	@RequestMapping(value = "/getAllupdatedExpiry", method = RequestMethod.GET)
	public @ResponseBody List<ExpiryUpdate> getAllupdatedExpiry() {
		List<ExpiryUpdate> list= new  ArrayList<ExpiryUpdate>();
		try {	
			
			list=expiryUpdateRepo.findAll();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/uploadLicence", method = RequestMethod.POST)
	public @ResponseBody Status uploadLicence(ModelMap model, @ModelAttribute(value = "file") MultipartFile file,
			HttpServletRequest request,@RequestParam("uploadBy")String uploadBy) throws ParseException {
		Status  status= new Status();
		int uploadedCount=0;
		int totalCount=0;
		String responceMsg="";
		try {
			if (!(file == null)) {
				if (file.isEmpty()) {
				} else {
					System.out.println(file.getOriginalFilename());
					try {
						File dir = new File(System.getProperty("catalina.base"), "uploads");
						File uplaodedFile = new File(dir + file.getOriginalFilename());
						file.transferTo(uplaodedFile);
						FileInputStream excelFile = new FileInputStream(uplaodedFile);
						Workbook workbook = new XSSFWorkbook(excelFile);
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						DataFormatter formatter = new DataFormatter();
						Sheet datatypeSheet = workbook.getSheetAt(0); 
						int i = 1;
						//Optional<UserInfo> optUser=userServices.findById(uploadBy);

						totalCount= datatypeSheet.getLastRowNum();
						while (i <= datatypeSheet.getLastRowNum()) { 
							if(totalCount==0){
								status.setCode(500);
								status.setMessage("Data Not Found in sheet");
								String responceMsg0="";

								responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
								responceMsg0+=" \r\n Upaloading Date :: "+new Date();
								
									responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
								
								responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
								
								responceMsg0+="\r\n Data Not Found in sheet";
								String newResMsg=responceMsg0+" \r\n "+responceMsg;
								status.setResmessage(newResMsg);
								
								return status; 
							}

							XSSFRow row = null;
							row = (XSSFRow) datatypeSheet.getRow(i++);
						
							String str=formatter.formatCellValue(row.getCell(0), evaluator);
							if(str.length()==0) {
								continue;
							}
						
							
							int checkExcel=0;
							String branch = formatter.formatCellValue(row.getCell(1), evaluator);
							String type = formatter.formatCellValue(row.getCell(2), evaluator);
							String associate = formatter.formatCellValue(row.getCell(3), evaluator);
							String product = formatter.formatCellValue(row.getCell(4), evaluator);
							String  project= formatter.formatCellValue(row.getCell(5), evaluator);
							String version = formatter.formatCellValue(row.getCell(6), evaluator);
							String key = formatter.formatCellValue(row.getCell(7), evaluator);
							String period = formatter.formatCellValue(row.getCell(8), evaluator);
							String periodUnit = formatter.formatCellValue(row.getCell(9), evaluator);
							String coast = formatter.formatCellValue(row.getCell(10), evaluator);
							String purchaseDate = formatter.formatCellValue(row.getCell(11), evaluator);
							String installationDate = formatter.formatCellValue(row.getCell(12), evaluator);
							String expiryDate = formatter.formatCellValue(row.getCell(13), evaluator);
							System.out.println("KEY   "+key);
							String licencestatus = formatter.formatCellValue(row.getCell(14), evaluator);

							if(i==1){
								if (formatter.formatCellValue(row.getCell(1), evaluator)
										.equalsIgnoreCase("Branch")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(2), evaluator)
										.equalsIgnoreCase("Type")) {
									checkExcel++;

								}
								if (formatter.formatCellValue(row.getCell(3), evaluator)
										.equalsIgnoreCase("Associate")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(4), evaluator)
										.equalsIgnoreCase("Product")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(5), evaluator)
										.equalsIgnoreCase("Project Name")) {
									checkExcel++;
								}

								if (formatter.formatCellValue(row.getCell(6), evaluator).equalsIgnoreCase("Vesrion")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(7), evaluator).equalsIgnoreCase("Key")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(8), evaluator).equalsIgnoreCase("Period")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(9), evaluator).equalsIgnoreCase("Period Unit")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(11), evaluator)
										.equalsIgnoreCase("Purchase Date ")) {
									checkExcel++;
								}
								
								
								
								System.out.println("checkExcel "+checkExcel);
								if(	checkExcel==10){
								}else{
									
									status.setCode(500);
									status.setMessage("Wrong Sheet Selected");
									String responceMsg0="";
								//	Optional<UserInfo> optional=userServices.findById(uploadBy);

									responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
									responceMsg0+=" \r\n Upaloading Date :: "+new Date();

										responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
									
									responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
									
									responceMsg0+="\r\n Wrong Sheet Selected";
									String newResMsg=responceMsg0+" \r\n "+responceMsg;
									status.setResmessage(newResMsg);
									return status; 
								}
							}else{
								String str1 = formatter.formatCellValue(row.getCell(0), evaluator);
								System.out.println("NO DATA " + str1.length());
								if (str1.length() == 0) {
									status.setCode(500);
									status.setMessage("Data Not Found in sheet");
									String responceMsg0 = "";
									responceMsg0 = "Uploading...... ";
									responceMsg0 += " \r\n  File Name :: " + file.getOriginalFilename();
									responceMsg0 += " \r\n Upaloading Date :: " + new Date();
									responceMsg0 += " \r\n Upaload  By:: " + uploadBy;
									responceMsg0 += " \r\n Uploding row done " + uploadedCount + " out of "
											+ totalCount;

									responceMsg0 += "\r\n Data Not Found in sheet";
									String newResMsg = responceMsg0 + " \r\n " + responceMsg;
									status.setResmessage(newResMsg);

									return status;
								} else {
									Optional<Licence> optional2 = lincencceManagementService.getLicenceByKeyAndLicenceTy(associate,product,key);
									if (optional2.isPresent()) {
										Licence licence = optional2.get();

										
										Date iDate = null;

										if (!purchaseDate.equalsIgnoreCase("")) {
											try {
												iDate = new SimpleDateFormat("dd/MM/yyyy").parse(purchaseDate);
												licence.setPurchaseDate(iDate);
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												responceMsg += " \r\n Invalid Invoice Date Format  For Row" + i;
												e.printStackTrace();
											}

										}
										if (!installationDate.equalsIgnoreCase("")) {
											try {
												iDate = new SimpleDateFormat("dd/MM/yyyy").parse(installationDate);
												licence.setInstalllationDate(iDate);
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												responceMsg += " \r\n Invalid Invoice Date Format  For Row" + i;
												e.printStackTrace();
											}

										}
										if (!expiryDate.equalsIgnoreCase("")) {
											try {
												iDate = new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate);
												licence.setLicenceExpiryDate(iDate);
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												responceMsg += " \r\n Invalid Invoice Date Format  For Row" + i;
												e.printStackTrace();
											}

										}
										
										
										System.out.println("aLREADY pRESENT");
										 lincencceManagementService.addLicence(licence);
										uploadedCount++;

									} else {
										
										Optional<Branch> optionalB = branchRepo.getBranchByName(branch);

										if (branch == null || branch == "") {
											responceMsg += " \r\n No Branch Name Found for Row No ::" + i;
											// System.out.println("No BranchName
											// Found for Row No ::"+i);
										} else {
											if (!optionalB.isPresent()) {
												responceMsg += " \r\n InValid Branch Name for Row No ::" + i;
												// System.out.println("InValid
												// Branch Name for Row No
												// ::"+i);
											}

										}

										if (optionalB.isPresent()) {
											Licence licence = new Licence ();
											Branch branchObj = optionalB.get();
											Optional<Associate> optionalAsso=lincencceManagementService.getAssociateByName(associate);
											Optional<Product> optionalProd=lincencceManagementService.getProductsByName(product);
											Optional<LicenceTypes> optionalType=lincencceManagementService.getLicenceTypeByName(type);

											
											Associate associate2= new Associate();
											Product product2= new Product();
											if(!optionalType.isPresent()){
											
												LicenceTypes types =new LicenceTypes();
												types.setTypeName(type);;
												lincencceManagementService.addLicenceTypes(types);
											}
											if(optionalAsso.isPresent()){
												associate2=optionalAsso.get();
											}else{
												Associate associateNew =new Associate();
												associateNew.setAssociateName(associate);
												associate2=lincencceManagementService.addAssociate(associateNew);
											}
											if(optionalProd.isPresent()){
												product2=optionalProd.get();	
											}else{
												Product productNew =new Product();
												productNew.setProductName(product);
												product2=lincencceManagementService.addProduct(productNew);
											}
											System.out.println("Period "+Integer.parseInt(period));
											licence.setBranch(branchObj);
											licence.setLicenceType(type);
											licence.setAssociate(associate2);
											licence.setProduct(product2);
											licence.setProjectName(project);
											licence.setLicenceStatus("In-Stock");
											licence.setLicenceVersion(version);
											licence.setLicencePeriod(Integer.parseInt(period));
											licence.setLicencePeriodUnit(periodUnit);
											
											
											Date iDate = null;

											if (!purchaseDate.equalsIgnoreCase("")) {
												// iDate = new
												// SimpleDateFormat("dd/MM/yyyy").parse(invoiceDate);
												try {
													iDate = new SimpleDateFormat("dd/MM/yyyy").parse(purchaseDate);
													licence.setPurchaseDate(iDate);
												} catch (Exception e) {
													// TODO Auto-generated catch
													// block
													responceMsg += " \r\n Invalid Invoice Date Format  For Row" + i;
													e.printStackTrace();
												}
											}

											if (!installationDate.equalsIgnoreCase("")) {
												// iDate = new
												// SimpleDateFormat("dd/MM/yyyy").parse(invoiceDate);
												try {
													iDate = new SimpleDateFormat("dd/MM/yyyy").parse(installationDate);
													licence.setInstalllationDate(iDate);
												} catch (Exception e) {
													// TODO Auto-generated catch
													// block
													responceMsg += " \r\n Invalid Invoice Date Format  For Row" + i;
													e.printStackTrace();
												}
											}
											System.out.println("nEW ");

											 lincencceManagementService.addLicence(licence);
												uploadedCount++;
											}
								System.out.println("Saving New .........");
								
							}
							
							
							
							
						}

					 }
				}
						workbook.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						
						
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		status.setCode(200);
		status.setMessage("Upalod Successfully");
		String responceMsg0="";
		responceMsg0="Uploading...... ";
	//	Optional<UserInfo> optional=userServices.findById(uploadBy);

		responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
		responceMsg0+=" \r\n Upaloading Date :: "+new Date();
		
			responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
	
		responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
		if(uploadedCount==totalCount){
			responceMsg0+=" \r\n No Constrain Found ";
		}else{
			responceMsg0+=" \r\n Found Following Constrain";
		}
		
		String newResMsg=responceMsg0+" \r\n "+responceMsg;
		status.setResmessage(newResMsg);
		return status;
	}
	
	
	@RequestMapping(value = "/uploadLicenceAllocation", method = RequestMethod.POST)
	public @ResponseBody Status uploadLicenceAllocation(ModelMap model, @ModelAttribute(value = "file") MultipartFile file,
			HttpServletRequest request,@RequestParam("uploadBy")String uploadBy) throws ParseException {
		Status  status= new Status();
		int uploadedCount=0;
		int totalCount=0;
		String responceMsg="";
		try {
			if (!(file == null)) {
				if (file.isEmpty()) {
				} else {
					System.out.println(file.getOriginalFilename());
					try {
						File dir = new File(System.getProperty("catalina.base"), "uploads");
						File uplaodedFile = new File(dir + file.getOriginalFilename());
						file.transferTo(uplaodedFile);
						FileInputStream excelFile = new FileInputStream(uplaodedFile);
						Workbook workbook = new XSSFWorkbook(excelFile);
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						DataFormatter formatter = new DataFormatter();
						Sheet datatypeSheet = workbook.getSheetAt(0); 
						int i = 1;
						//Optional<UserInfo> optUser=userServices.findById(uploadBy);

						totalCount= datatypeSheet.getLastRowNum();
						while (i <= datatypeSheet.getLastRowNum()) { 
							if(totalCount==0){
								status.setCode(500);
								status.setMessage("Data Not Found in sheet");
								String responceMsg0="";

								responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
								responceMsg0+=" \r\n Upaloading Date :: "+new Date();
								
									responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
								
								responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
								
								responceMsg0+="\r\n Data Not Found in sheet";
								String newResMsg=responceMsg0+" \r\n "+responceMsg;
								status.setResmessage(newResMsg);
								
								return status; 
							}

							XSSFRow row = null;
							row = (XSSFRow) datatypeSheet.getRow(i++);
						
							String str=formatter.formatCellValue(row.getCell(0), evaluator);
							if(str.length()==0) {
								continue;
							}
						
							
							int checkExcel=0;
							String branch = formatter.formatCellValue(row.getCell(1), evaluator);
							String type = formatter.formatCellValue(row.getCell(2), evaluator);
							String associate = formatter.formatCellValue(row.getCell(3), evaluator);
							String product = formatter.formatCellValue(row.getCell(4), evaluator);
							String  project= formatter.formatCellValue(row.getCell(5), evaluator);
							String version = formatter.formatCellValue(row.getCell(6), evaluator);
							String key = formatter.formatCellValue(row.getCell(7), evaluator);
							String serialNo = formatter.formatCellValue(row.getCell(8), evaluator);
							
							if(i==1){
								if (formatter.formatCellValue(row.getCell(1), evaluator)
										.equalsIgnoreCase("Branch")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(2), evaluator)
										.equalsIgnoreCase("Type")) {
									checkExcel++;

								}
								if (formatter.formatCellValue(row.getCell(3), evaluator)
										.equalsIgnoreCase("Publisher")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(4), evaluator)
										.equalsIgnoreCase("Product")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(5), evaluator)
										.equalsIgnoreCase("Project Name")) {
									checkExcel++;
								}

								if (formatter.formatCellValue(row.getCell(6), evaluator).equalsIgnoreCase("Vesrion")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(7), evaluator).equalsIgnoreCase("Key")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(8), evaluator).equalsIgnoreCase("Serial No")) {
									checkExcel++;
								}
							
								
								
								
								System.out.println("checkExcel "+checkExcel);
								if(	checkExcel==10){
								}else{
									
									status.setCode(500);
									status.setMessage("Wrong Sheet Selected");
									String responceMsg0="";
								//	Optional<UserInfo> optional=userServices.findById(uploadBy);

									responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
									responceMsg0+=" \r\n Upaloading Date :: "+new Date();

										responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
									
									responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
									
									responceMsg0+="\r\n Wrong Sheet Selected";
									String newResMsg=responceMsg0+" \r\n "+responceMsg;
									status.setResmessage(newResMsg);
									return status; 
								}
							}else{
								String str1 = formatter.formatCellValue(row.getCell(0), evaluator);
								System.out.println("NO DATA " + str1.length());
								if (str1.length() == 0) {
									status.setCode(500);
									status.setMessage("Data Not Found in sheet");
									String responceMsg0 = "";
									responceMsg0 = "Uploading...... ";
									responceMsg0 += " \r\n  File Name :: " + file.getOriginalFilename();
									responceMsg0 += " \r\n Upaloading Date :: " + new Date();
									responceMsg0 += " \r\n Upaload  By:: " + uploadBy;
									responceMsg0 += " \r\n Uploding row done " + uploadedCount + " out of "
											+ totalCount;

									responceMsg0 += "\r\n Data Not Found in sheet";
									String newResMsg = responceMsg0 + " \r\n " + responceMsg;
									status.setResmessage(newResMsg);

									return status;
								} else {
									Optional<Associate> optionalAsso=lincencceManagementService.getAssociateByName(associate);
									System.out.println("Associate "+associate+" is "+optionalAsso.isPresent());
									Optional<Product> optionalProd=lincencceManagementService.getProductsByName(product);
									Optional<Asset> optionalAsset=assetService.getAssetBySerialNo(serialNo);
								
									
									
									System.out.println("Asset for "+serialNo+" is "+optionalAsset.isPresent());
									Optional<AssetLicence> optional2 = lincencceManagementService.getAllAssetLicencceByAssociateAndProductAndAssetId(optionalAsso.get().getId(),optionalProd.get().getId(),optionalProd.get().getId());
									if (optional2.isPresent()) {
									
										
																				uploadedCount++;

									} else {
										List<Licence> licenceList = lincencceManagementService.getAvailableLicencceByAssociateAndProduct(optionalAsso.get().getId(),optionalProd.get().getId());

										AssetLicence assetLicence= new AssetLicence();
											Licence licence =licenceList.get(0);
											
											assetLicence.setAsset(optionalAsset.get());
											assetLicence.setAssingBy("Upload");
											assetLicence.setLicence(licence);
											assetLicence.setLicenceKey(licence.getLicenceKey());
											assetLicence.setAssingDate(new Date());
											licence.setLicenceStatus("Assigned");
											
											lincencceManagementService.addAssetLicence(assetLicence);
											 lincencceManagementService.addLicence(licence);
												uploadedCount++;
										
								
							}
							
							
							
							
						}

					 }
				}
						workbook.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						
						
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		status.setCode(200);
		status.setMessage("Upalod Successfully");
		String responceMsg0="";
		responceMsg0="Uploading...... ";
	//	Optional<UserInfo> optional=userServices.findById(uploadBy);

		responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
		responceMsg0+=" \r\n Upaloading Date :: "+new Date();
		
			responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
	
		responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
		if(uploadedCount==totalCount){
			responceMsg0+=" \r\n No Constrain Found ";
		}else{
			responceMsg0+=" \r\n Found Following Constrain";
		}
		
		String newResMsg=responceMsg0+" \r\n "+responceMsg;
		status.setResmessage(newResMsg);
		return status;
	}
	private int parseInt(String period) {
		// TODO Auto-generated method stub
		return 0;
	}

	@RequestMapping(value = "/uploadExpiryUpdate", method = RequestMethod.POST)
	public @ResponseBody Status uploadExpiryUpdate(ModelMap model, @ModelAttribute(value = "file") MultipartFile file,
			HttpServletRequest request,@RequestParam("uploadBy")String uploadBy) throws ParseException {
		Status  status= new Status();
		int uploadedCount=0;
		int totalCount=0;
		String responceMsg="";
		try {
			if (!(file == null)) {
				if (file.isEmpty()) {
				} else {
					System.out.println(file.getOriginalFilename());
					try {
						File dir = new File(System.getProperty("catalina.base"), "uploads");
						File uplaodedFile = new File(dir + file.getOriginalFilename());
						file.transferTo(uplaodedFile);
						FileInputStream excelFile = new FileInputStream(uplaodedFile);
						Workbook workbook = new XSSFWorkbook(excelFile);
						FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
						DataFormatter formatter = new DataFormatter();
						Sheet datatypeSheet = workbook.getSheetAt(0); 
						int i = 1;
						//Optional<UserInfo> optUser=userServices.findById(uploadBy);

						totalCount= datatypeSheet.getLastRowNum();
						while (i <= datatypeSheet.getLastRowNum()) { 
							if(totalCount==0){
								status.setCode(500);
								status.setMessage("Data Not Found in sheet");
								String responceMsg0="";

								responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
								responceMsg0+=" \r\n Upaloading Date :: "+new Date();
								
									responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
								
								responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
								
								responceMsg0+="\r\n Data Not Found in sheet";
								String newResMsg=responceMsg0+" \r\n "+responceMsg;
								status.setResmessage(newResMsg);
								
								return status; 
							}

							XSSFRow row = null;
							row = (XSSFRow) datatypeSheet.getRow(i++);
						
							String str=formatter.formatCellValue(row.getCell(0), evaluator);
							if(str.length()==0) {
								continue;
							}
						
							
							int checkExcel=0;
							String publisher = formatter.formatCellValue(row.getCell(1), evaluator);
							String product = formatter.formatCellValue(row.getCell(2), evaluator);
							String version = formatter.formatCellValue(row.getCell(3), evaluator);
							String key = formatter.formatCellValue(row.getCell(4), evaluator);
							String  new_exp= formatter.formatCellValue(row.getCell(5), evaluator);
							String cost = formatter.formatCellValue(row.getCell(6), evaluator);
							System.out.println("publisher "+publisher);
							System.out.println("product "+product);

							System.out.println(" version "+ version);

							System.out.println("key "+key);

							if(i==1){
								if (formatter.formatCellValue(row.getCell(1), evaluator)
										.equalsIgnoreCase("Publisher")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(2), evaluator)
										.equalsIgnoreCase("Product")) {
									checkExcel++;

								}
								if (formatter.formatCellValue(row.getCell(3), evaluator)
										.equalsIgnoreCase("Version")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(4), evaluator)
										.equalsIgnoreCase("Key")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(5), evaluator)
										.equalsIgnoreCase("New Expiry Date")) {
									checkExcel++;
								}

								
								
								
								
								System.out.println("checkExcel "+checkExcel);
								if(	checkExcel==4){
								}else{
									
									status.setCode(500);
									status.setMessage("Wrong Sheet Selected");
									String responceMsg0="";
								//	Optional<UserInfo> optional=userServices.findById(uploadBy);

									responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
									responceMsg0+=" \r\n Upaloading Date :: "+new Date();

										responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
									
									responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
									
									responceMsg0+="\r\n Wrong Sheet Selected";
									String newResMsg=responceMsg0+" \r\n "+responceMsg;
									status.setResmessage(newResMsg);
									return status; 
								}
							}else{
								String str1 = formatter.formatCellValue(row.getCell(0), evaluator);
								System.out.println("NO DATA " + str1.length());
								if (str1.length() == 0) {
									status.setCode(500);
									status.setMessage("Data Not Found in sheet");
									String responceMsg0 = "";
									responceMsg0 = "Uploading...... ";
									responceMsg0 += " \r\n  File Name :: " + file.getOriginalFilename();
									responceMsg0 += " \r\n Upaloading Date :: " + new Date();
									responceMsg0 += " \r\n Upaload  By:: " + uploadBy;
									responceMsg0 += " \r\n Uploding row done " + uploadedCount + " out of "
											+ totalCount;

									responceMsg0 += "\r\n Data Not Found in sheet";
									String newResMsg = responceMsg0 + " \r\n " + responceMsg;
									status.setResmessage(newResMsg);

									return status;
								} else {
									Optional<Licence> optional2 = lincencceManagementService.getLicenceByPublisherProductKey(publisher,product,key);
									
									System.out.println("LICENCE IS FOUND "+optional2.isPresent());
									if (optional2.isPresent()) {
										Licence licence = optional2.get();

										Date iDate = new SimpleDateFormat("dd/MM/yyyy").parse(new_exp);
										
										
										ExpiryUpdate expiryUpdate= new ExpiryUpdate();
										expiryUpdate.setCost(cost);
										expiryUpdate.setExistingExpiryDate(licence.getLicenceExpiryDate());
										expiryUpdate.setLicence(licence);
										expiryUpdate.setNewExpiryDate(iDate);
										
										
										licence.setLicenceExpiryDate(iDate);
										licence.setCost(cost);
										expiryUpdateRepo.save(expiryUpdate);
										 lincencceManagementService.addLicence(licence);
										uploadedCount++;

									} else {
										
										responceMsg += " \r\n Invalid Licence Details for Row" + i;

								
							}
							
							
							
							
						}

					 }
				}
						workbook.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						
						
					}
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		status.setCode(200);
		status.setMessage("Upalod Successfully");
		String responceMsg0="";
		responceMsg0="Uploading...... ";
	//	Optional<UserInfo> optional=userServices.findById(uploadBy);

		responceMsg0+=" \r\n  File Name :: "+file.getOriginalFilename();
		responceMsg0+=" \r\n Upaloading Date :: "+new Date();
		
			responceMsg0+=" \r\n Upaload  By:: "+uploadBy;
	
		responceMsg0+=" \r\n Uploding row done "+uploadedCount+" out of "+totalCount;
		if(uploadedCount==totalCount){
			responceMsg0+=" \r\n No Constrain Found ";
		}else{
			responceMsg0+=" \r\n Found Following Constrain";
		}
		
		String newResMsg=responceMsg0+" \r\n "+responceMsg;
		status.setResmessage(newResMsg);
		return status;
	}
	
	@RequestMapping(value = "/getMaxKeyIndex", method = RequestMethod.GET)
	public @ResponseBody ResponceObject getMaxKeyIndex() {
		ResponceObject responceObject =new ResponceObject();
		try {
			
		int count=lincencceRepo.getMaxIndexIsPresent();
		String resData="";
		if(count==0){
			resData="00001";
		}else{
			resData=lincencceRepo.getMaxKeyIndex();
		}
			System.out.println("COUNT "+count);
			System.out.println("resData "+resData);
			responceObject.setDataString(resData);
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
}
