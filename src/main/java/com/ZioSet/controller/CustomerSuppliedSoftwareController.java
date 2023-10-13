package com.ZioSet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ZioSet.dto.CustomerSuppliedSoftwareCountDTO;
import com.ZioSet.dto.ResponceObject;
import com.ZioSet.dto.Status;
import com.ZioSet.model.Asset;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.AssetTransaction;
import com.ZioSet.model.Branch;
import com.ZioSet.model.CustomerSuppliedSoftware;
import com.ZioSet.model.Employee;
import com.ZioSet.Service.CustomerSuppliedSoftwareService;

@RestController
@CrossOrigin("*")
@RequestMapping("customerSuppliedSoftware")
public class CustomerSuppliedSoftwareController {
	@Autowired 
	CustomerSuppliedSoftwareService customerSuppliedSoftwareService;
	
	@RequestMapping(value = "/getAllCustomerSuppliedSoftwares", method = RequestMethod.GET)
	public @ResponseBody List<CustomerSuppliedSoftware> getAllCustomerSuppliedSoftwares() {
		List<CustomerSuppliedSoftware> list= null;
		try {
			list = customerSuppliedSoftwareService.getAllCustomerSuppliedSoftwares();
			int srNo=1;
			for(CustomerSuppliedSoftware customerSuppliedSoftware:list){
				customerSuppliedSoftware.setSrNo(srNo);
				srNo++;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping(value = "/addNewCustomerSuppliedSoftware", method = RequestMethod.POST)
	public @ResponseBody ResponceObject addNewCustomerSuppliedSoftware(@RequestBody CustomerSuppliedSoftware  customerSuppliedSoftware) {
		ResponceObject responceObject =new ResponceObject();
		try {
			System.out.println("S  "+customerSuppliedSoftware.toString());
			Optional<CustomerSuppliedSoftware> optional= customerSuppliedSoftwareService.getCustomerSuppliedSoftware(customerSuppliedSoftware.getFormSrNo());
			if(optional.isPresent()){
				CustomerSuppliedSoftware customerSuppliedSoftware2= optional.get();
				customerSuppliedSoftware2.setAssetTagNo(customerSuppliedSoftware.getAssetTagNo());
				customerSuppliedSoftware2.setLanguage(customerSuppliedSoftware.getLanguage());
				customerSuppliedSoftware2.setLicenceCount(customerSuppliedSoftware.getLicenceCount());
				customerSuppliedSoftware2.setRemark(customerSuppliedSoftware.getRemark());
				customerSuppliedSoftware2.setTitle(customerSuppliedSoftware.getTitle());
				customerSuppliedSoftware2.setVersion(customerSuppliedSoftware.getVersion());
				customerSuppliedSoftwareService.addNewCustomerSuppliedSoftware(optional.get());
				responceObject.setCode(200);
				responceObject.setMessage("Added Sucessfully!!!");
			}else{
				customerSuppliedSoftwareService.addNewCustomerSuppliedSoftware(customerSuppliedSoftware);

			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	@RequestMapping(value = "/deleteCustomerSuppliedSoftware", method = RequestMethod.POST)
	public @ResponseBody ResponceObject deleteCustomerSuppliedSoftware(@RequestBody CustomerSuppliedSoftware  customerSuppliedSoftware) {
		ResponceObject responceObject =new ResponceObject();
		try {
			System.out.println("S  "+customerSuppliedSoftware.toString());
			
				customerSuppliedSoftwareService.deleteCustomerSuppliedSoftware(customerSuppliedSoftware);
				responceObject.setCode(200);
				responceObject.setMessage("Deleted successfully..... ");
		
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	@RequestMapping(value = "/getCustomerSuppliedSoftwareByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<CustomerSuppliedSoftware> list= new  ArrayList<CustomerSuppliedSoftware>();
		try {	
			list=customerSuppliedSoftwareService.getCustomerSuppliedSoftwareByLimit(page_no,item_per_page);

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getCustomerSuppliedSoftwareByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<CustomerSuppliedSoftware> getCustomerSuppliedSoftwareByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<CustomerSuppliedSoftware> list= new  ArrayList<CustomerSuppliedSoftware>();
		try {	
			
			list=customerSuppliedSoftwareService.getCustomerSuppliedSoftwareByLimitAndSearch(searchText,pageNo,perPage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getCustomerSuppliedSoftwareCount1", method = RequestMethod.GET)
	public @ResponseBody CustomerSuppliedSoftwareCountDTO  getCustomerSuppliedSoftwareCount1() {
		CustomerSuppliedSoftwareCountDTO  supplierCount= new CustomerSuppliedSoftwareCountDTO();
		try {
			int listcount= customerSuppliedSoftwareService.getCustomerSuppliedSoftwareCount();
			int supplierToatlCount= customerSuppliedSoftwareService.getCustomerSuppliedSoftwareTotalCount();
			System.out.println("Total Count.............................................. "+supplierToatlCount);
			supplierCount.setListcount(listcount);
			supplierCount.setTotalCount(supplierToatlCount);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}
	@RequestMapping(value = "/getSuppliedSoftwareCount", method = RequestMethod.GET)
	public @ResponseBody int  getSuppliedSoftwareCount() {
		int  supplierCount= 0;
		try {
			supplierCount= customerSuppliedSoftwareService.getSuppliedSoftwareCount();

			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}
	@RequestMapping(value = "/getCustomerSuppliedSoftwareCount", method = RequestMethod.GET)
	public @ResponseBody int  getCustomerSuppliedSoftwareCount() {
		int  supplierCount= 0;
		try {
			supplierCount= customerSuppliedSoftwareService.getCustomerSuppliedSoftwareCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}
	@RequestMapping(value = "/getCustomerSuppliedSoftwareCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getCustomerSuppliedSoftwareCountAndSearch(@RequestParam("searchText") String searchText) {
		int  supplierCount= 0;
		try {
			supplierCount= customerSuppliedSoftwareService.getCustomerSuppliedSoftwareCountAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}
	
	@RequestMapping(value = "/uploadCustomerSuppliedSoftware", method = RequestMethod.POST)
	public @ResponseBody Status uploadCustomerSuppliedSoftware(@ModelAttribute(value = "file") MultipartFile file, HttpServletRequest request,
			@RequestParam("uploadBy") String uploadBy) throws ParseException {
		Status status = new Status();
		int uploadedCount = 0;
		int totalCount = 0;
		String responceMsg = "";
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
						totalCount = datatypeSheet.getLastRowNum();
						System.out.println("totalCount " + totalCount);
						int i = 0;
						while (i <= datatypeSheet.getLastRowNum()) {
							if (totalCount == 0) {
								status.setCode(500);
								status.setMessage("Data Not Found in sheet");
								String responceMsg0 = "";
								responceMsg0 = "Uploading...... ";
								responceMsg0 += " \r\n  File Name :: " + file.getOriginalFilename();
								responceMsg0 += " \r\n Upaloading Date :: " + new Date();
								responceMsg0 += " \r\n Upaload  By:: " + uploadBy;
								responceMsg0 += " \r\n Uploding row done " + uploadedCount + " out of " + totalCount;

								responceMsg0 += "\r\n Data Not Found in sheet";
								String newResMsg = responceMsg0 + " \r\n " + responceMsg;
								status.setResmessage(newResMsg);

								// return status;
							}
							XSSFRow row = null;
							row = (XSSFRow) datatypeSheet.getRow(i++);
							// String str = row.getCell(0).toString();
							String str = formatter.formatCellValue(row.getCell(0), evaluator);
							int checkExcel = 0;

							String formSrNo = formatter.formatCellValue(row.getCell(1), evaluator);
							String assetTagNo = formatter.formatCellValue(row.getCell(2), evaluator);
							String title = formatter.formatCellValue(row.getCell(3), evaluator);
							String version = formatter.formatCellValue(row.getCell(4), evaluator);
							String language = formatter.formatCellValue(row.getCell(5), evaluator);
							String remark = formatter.formatCellValue(row.getCell(6), evaluator);
							String licenceCount = formatter.formatCellValue(row.getCell(7), evaluator);
							
						

							if (i == 1) {

								if (formatter.formatCellValue(row.getCell(1), evaluator)
										.equalsIgnoreCase("Forms Sr. No")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(2), evaluator)
										.equalsIgnoreCase("Asset Tag No")) {
									checkExcel++;

								}
								if (formatter.formatCellValue(row.getCell(3), evaluator)
										.equalsIgnoreCase("Title")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(4), evaluator)
										.equalsIgnoreCase("Version")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(5), evaluator)
										.equalsIgnoreCase("Language")) {
									checkExcel++;
								}

								if (formatter.formatCellValue(row.getCell(6), evaluator).equalsIgnoreCase("Remarks")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(7), evaluator).equalsIgnoreCase("License Count")) {
									checkExcel++;
								}
								

								System.out.println("checkExcel " + checkExcel);
								if (checkExcel == 7) {
								} else {
									status.setCode(500);
									status.setMessage("Wrong Sheet Selected");
									String responceMsg0 = "";
									responceMsg0 = "Uploading...... ";
									responceMsg0 += " \r\n  File Name :: " + file.getOriginalFilename();
									responceMsg0 += " \r\n Upaloading Date :: " + new Date();
									responceMsg0 += " \r\n Upaload  By:: " + uploadBy;
									responceMsg0 += " \r\n Uploding row done " + uploadedCount + " out of "
											+ totalCount;

									responceMsg0 += "\r\n Wrong Sheet Selected";
									String newResMsg = responceMsg0 + " \r\n " + responceMsg;
									status.setResmessage(newResMsg);
									return status;
								}
							} else {

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
									Optional<CustomerSuppliedSoftware> optional = customerSuppliedSoftwareService.getCustomerSuppliedSoftware(formSrNo);
									if(optional.isPresent()){
										responceMsg += " \r\n From Serial No Is Already Exits For Row No ::" + i;

									}else{
										CustomerSuppliedSoftware customerSuppliedSoftware= new CustomerSuppliedSoftware();
										customerSuppliedSoftware.setAdded_date(new Date());
										customerSuppliedSoftware.setAssetTagNo(assetTagNo);
										customerSuppliedSoftware.setFormSrNo(formSrNo);
										customerSuppliedSoftware.setLanguage(language);
										customerSuppliedSoftware.setLicenceCount(Integer.parseInt(licenceCount));
										customerSuppliedSoftware.setRemark(remark);
										customerSuppliedSoftware.setTitle(title);
										customerSuppliedSoftware.setVersion(version);
										customerSuppliedSoftwareService.addNewCustomerSuppliedSoftware(customerSuppliedSoftware);
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
		String responceMsg0 = "";
		responceMsg0 = "Uploading...... ";
		responceMsg0 += " \r\n  File Name :: " + file.getOriginalFilename();
		responceMsg0 += " \r\n Upaloading Date :: " + new Date();
		responceMsg0 += " \r\n Upaload  By:: " + uploadBy;
		responceMsg0 += " \r\n Uploding row done " + uploadedCount + " out of " + totalCount;
		if (uploadedCount == totalCount) {
			responceMsg0 += " \r\n No Constrain Found ";
		} else {
			responceMsg0 += " \r\n Found Following Constrain";
		}

		String newResMsg = responceMsg0 + " \r\n " + responceMsg;
		status.setResmessage(newResMsg);
		return status;
	}
}
