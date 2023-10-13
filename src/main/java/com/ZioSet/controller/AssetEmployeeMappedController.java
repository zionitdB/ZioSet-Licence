package com.ZioSet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.http.ResponseEntity;
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


import com.ZioSet.dto.Status;
import com.ZioSet.dto.TransferAssetDto;
import com.ZioSet.model.Asset;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.AssetTransaction;
import com.ZioSet.Service.AssetEmployeeMappeServices;
import com.ZioSet.Service.AssetService;
import com.ZioSet.Service.EmployeeServices;



@RestController
@CrossOrigin("*")
@RequestMapping("assetEmpMapped")
public class AssetEmployeeMappedController {
	
	@Autowired
	AssetEmployeeMappeServices assetEmployeeMappeServices;
	@Autowired
	EmployeeServices employeeServices;
	@Autowired
	AssetService 	assetServices;
	
	
	@RequestMapping(value = "/transferAsset", method = RequestMethod.POST)
	public @ResponseBody Status transferAsset(@RequestBody TransferAssetDto transferAssetDto) {
		Status status= new Status();
		try {
			//System.out.println("New Empl "+transferAssetDto.getNewemployee().toString());
			//System.out.println("MAPPED  "+transferAssetDto.getAssetEmployeeAssigned().toString());
			AssetEmployeeAssigned assetEmployeeAssigned= new AssetEmployeeAssigned();
			assetEmployeeAssigned.setAsset(transferAssetDto.getAssetEmployeeAssigned().getAsset());
			assetEmployeeAssigned.setEmployee(transferAssetDto.getNewemployee());
			assetEmployeeAssigned.setMappedDate(new Date());
			assetEmployeeAssigned.setMappedStatus(1);
			assetEmployeeMappeServices.mappedAsset(assetEmployeeAssigned);
			assetEmployeeMappeServices.releaseMappedAsset(transferAssetDto.getAssetEmployeeAssigned());
			
			AssetTransaction assetTransaction = new AssetTransaction();
			assetTransaction.setAsset(transferAssetDto.getAssetEmployeeAssigned().getAsset());
			assetTransaction.setEmployee(transferAssetDto.getNewemployee());
			assetTransaction.setTransactionDate(new Date());
			assetTransaction.setTranactionType("Transfer");
			assetEmployeeMappeServices.saveAssetTransaction(assetTransaction);
			 status.setCode(200);
			 status.setMessage("Asset Release.... Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			 status.setCode(500);
			 status.setMessage("Something Wrong");
		}
		return status;
	}
 
	@RequestMapping(value = "/mappedAsset", method = RequestMethod.POST)
	public @ResponseBody Status updateAsset(@RequestBody AssetEmployeeAssigned assetEmployeeAssigned) {
		Status status= new Status();
		try {

			if(assetEmployeeAssigned.getAsset().getBranch().getBranchId()==assetEmployeeAssigned.getEmployee().getBranch().getBranchId()){
				assetEmployeeAssigned.setMappedDate(new Date());
				assetEmployeeMappeServices.mappedAsset(assetEmployeeAssigned);
				Asset asset= assetEmployeeAssigned.getAsset();
				asset.setAvailableStatus(0);
				asset.setStatus("Assigned");
				assetServices.addNewAsset(asset);
				AssetTransaction assetTransaction = new AssetTransaction();
				assetTransaction.setAsset(asset);
				assetTransaction.setEmployee(assetEmployeeAssigned.getEmployee());
				assetTransaction.setTransactionDate(new Date());
				assetTransaction.setTranactionType("New Assigned");
				assetEmployeeMappeServices.saveAssetTransaction(assetTransaction);
				 status.setCode(200);
				 status.setMessage("Asset Employee Mapping.... Successfully");
			}else{
				 status.setCode(500);
				 status.setMessage("Branch Not Match");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			 status.setCode(500);
			 status.setMessage("Something Wrong");
		}
		return status;
	}
	@RequestMapping(value = "/releaseMappedAsset", method = RequestMethod.POST)
	public @ResponseBody Status releaseMappedAsset(@RequestBody AssetEmployeeAssigned assetEmployeeAssigned) {
		Status status= new Status();
		try {
		
			assetEmployeeMappeServices.releaseMappedAsset(assetEmployeeAssigned);
			Asset asset= assetEmployeeAssigned.getAsset();
			asset.setAvailableStatus(1);
			asset.setStatus("InStock");
			assetServices.addNewAsset(asset);
			AssetTransaction assetTransaction= new AssetTransaction();
			assetTransaction.setTranactionType("Deallocation");
			assetTransaction.setEmployee(assetEmployeeAssigned.getEmployee());
			assetTransaction.setAsset(asset);
			assetTransaction.setTransactionDate(new Date());
			assetEmployeeMappeServices.saveAssetTransaction(assetTransaction);
			assetEmployeeMappeServices.deleteAssetEmployeeAssigned(assetEmployeeAssigned);
			 status.setCode(200);
			 status.setMessage("Asset Release.... Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			 status.setCode(500);
			 status.setMessage("Something Wrong");
		}
		return status;
	}
	
	
	@RequestMapping(value = "/getAllocationByAssetId", method = RequestMethod.GET)
	public @ResponseBody AssetEmployeeAssigned getAllocationByAssetId(@RequestParam("id")int id) {
		AssetEmployeeAssigned employeeAssigned= new  AssetEmployeeAssigned();
		try {	
			employeeAssigned=assetEmployeeMappeServices.getAllocationByAssetId(id);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeAssigned;
	}
	
	@RequestMapping(value = "/getAllocationHistoryByAssetId", method = RequestMethod.GET)
	public @ResponseBody List<AssetTransaction> getAllocationHistoryByAssetId(@RequestParam("id")int id) {
		List<AssetTransaction> list= new  ArrayList<AssetTransaction>();
		try {	
			list=assetEmployeeMappeServices.getAllocationHistoryByAssetId(id);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@RequestMapping(value = "/getAllocatedAssetByEmployee", method = RequestMethod.GET)
	public @ResponseBody List<AssetEmployeeAssigned> getAllocatedAssetByEmployee(@RequestParam("employeeId") int employeeId) {
		List<AssetEmployeeAssigned> list= new  ArrayList<AssetEmployeeAssigned>();
		try {	
			list=assetEmployeeMappeServices.getEmployeeWiseAllocationReport(employeeId);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@RequestMapping(value = "/getAssetEmployeeAssignedByLimit/{page_no}/{item_per_page}/{dataReq}", method = RequestMethod.GET)
	public @ResponseBody List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page,@PathVariable("dataReq") String dataReq) {
		List<AssetEmployeeAssigned> list= new  ArrayList<AssetEmployeeAssigned>();
		try {	
			list=assetEmployeeMappeServices.getAssetEmployeeAssignedByLimit(page_no,item_per_page,dataReq);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@RequestMapping(value = "/getAssetEmployeeAssignedByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<AssetEmployeeAssigned> getAssetEmployeeAssignedByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage,@RequestParam("dataReq") String dataReq) {
		List<AssetEmployeeAssigned> list= new  ArrayList<AssetEmployeeAssigned>();
		try {	
			
			list=assetEmployeeMappeServices.getAssetEmployeeAssignedByLimitAndSearch(searchText,pageNo,perPage,dataReq);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAssetEmployeeAssignedCount/{dataReq}", method = RequestMethod.GET)
	public @ResponseBody int  getAssetEmployeeAssignedCount(@PathVariable("dataReq") String dataReq) {
		int  supplierCount= 0;
		try {
			supplierCount= assetEmployeeMappeServices.getAssetEmployeeAssignedCount(dataReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}
	@RequestMapping(value = "/getAssetEmployeeAssignedCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getAssetEmployeeAssignedCountAndSearch(@RequestParam("searchText") String searchText,@RequestParam("dataReq") String dataReq) {
		int  supplierCount= 0;
		try {
			supplierCount= assetEmployeeMappeServices.getAssetEmployeeAssignedCountAndSearch(searchText,dataReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}
	

	@RequestMapping(value = "/getAllAssetEmployees", method = RequestMethod.GET)
	public @ResponseBody List<AssetEmployeeAssigned> getAllAssetEmployees() {
		List<AssetEmployeeAssigned> list= new  ArrayList<AssetEmployeeAssigned>();
		try {	
			List<AssetEmployeeAssigned> list1=assetEmployeeMappeServices.getAllAssetEmployees();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		System.out.println("SIZE ..............."+list1.size());
			int srNo=1;
			for(AssetEmployeeAssigned assetEmployeeAssigned:list1){
				String mapDateStr = dateFormat.format(assetEmployeeAssigned.getMappedDate());  
				assetEmployeeAssigned.setMapDateStr(mapDateStr);
				assetEmployeeAssigned.setSrNo(srNo);
				assetEmployeeAssigned.getAsset().setIncDate(dateFormat.format(assetEmployeeAssigned.getAsset().getInvoiceDate()));
				
				Date invoiceDate=assetEmployeeAssigned.getAsset().getInvoiceDate();
				Date today=new Date();
				//System.out.println("Invoice Date "+invoiceDate+" for "+asset.getId());
				//System.out.println("today "+today);
				if(invoiceDate!=null){
					  long diff = today.getTime() - invoiceDate.getTime();
					  int days=(int) (diff / 1000 / 60 / 60 / 24);
					  int year=(days/356);
					 // System.out.println ("Days: " + year);
						assetEmployeeAssigned.getAsset().setAge(String.valueOf(year));

				}
				String reportingManager="";
				if(assetEmployeeAssigned.getEmployee().getManager()!=null){
					 reportingManager=(assetEmployeeAssigned.getEmployee().getManager()).replace(',', ' ');

				}
				assetEmployeeAssigned.getEmployee().setManager(reportingManager);
				assetEmployeeAssigned.setEmpName(assetEmployeeAssigned.getEmployee().getFirstName()+" "+assetEmployeeAssigned.getEmployee().getLastName());
				list.add(assetEmployeeAssigned);
				srNo++;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/*@RequestMapping(value = "/getAllAssetEmployeesCounts", method = RequestMethod.GET)
	public @ResponseBody AssetEmployeeCountDto getAllAssetEmployeesCounts() {
		AssetEmployeeCountDto employeeAssigned= new  AssetEmployeeCountDto();
		try {	
			int totalAssetCount=assetServices.getTotalAssetCount();
			int totalAssingedAssetCount=assetServices.getAssingedAssetCount();
			int totalRmainingAssetCount=totalAssetCount-totalAssingedAssetCount;
			int totalAssingedEmployeeCount=employeeServices.getTotalAssingedEmployeeCount();
			int totalEmployeeCount=employeeServices.getTotalEmployeeCount();
			int totalRmainingEmployeeCount=totalEmployeeCount-totalAssingedEmployeeCount;
			
			System.out.println("totalAssingedEmployeeCount "+totalAssingedEmployeeCount);
			System.out.println("totalEmployeeCount "+totalEmployeeCount);
			
			employeeAssigned.setTotalAssingedAssetCount(totalAssingedAssetCount);
			employeeAssigned.setTotalAssingedEmployeeCount(totalAssingedEmployeeCount);
			employeeAssigned.setTotalRmainingAssetCount(totalRmainingAssetCount);
			employeeAssigned.setTotalRmainingEmployeeCount(totalRmainingEmployeeCount);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeAssigned;
	}*/
	

}
