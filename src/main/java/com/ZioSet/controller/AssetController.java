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

import com.ZioSet.Repo.BranchRepo;
import com.ZioSet.Repo.CPUDetialsRepo;
import com.ZioSet.Repo.DiskDetialsRepo;
import com.ZioSet.Repo.MemoryDetailsRepo;
import com.ZioSet.Repo.OSDetialsRepo;
import com.ZioSet.Service.AssetService;
import com.ZioSet.dto.AssetCountDetialDto;
import com.ZioSet.dto.Status;
import com.ZioSet.dto.SysytemDetailsDTO;
import com.ZioSet.model.Asset;
import com.ZioSet.model.Branch;
import com.ZioSet.model.CPUDetials;
import com.ZioSet.model.DiskDetials;
import com.ZioSet.model.MemoryDetails;
import com.ZioSet.model.OSDetials;


@RestController
@CrossOrigin("*")
@RequestMapping("asset")
public class AssetController {
	
	@Autowired
	AssetService assetServices;
	@Autowired
	BranchRepo branchRepo;

	@Autowired
	CPUDetialsRepo cPUDetialsRepo;
	
	@Autowired
	DiskDetialsRepo diskDetialsRepo;
	@Autowired
	MemoryDetailsRepo memoryDetailsRepo;
	
	@Autowired
	OSDetialsRepo oSDetialsRepo;

	@RequestMapping(value = "/getAllAvailableTags1", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAllAvailableTags1() {
		List<Asset> list = new ArrayList<Asset>();
		try {

			list = assetServices.getAvailableAssets();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/addNewAsset", method = RequestMethod.POST)
	public @ResponseBody Status addNewAsset(@RequestBody Asset asset) {
		Status status = new Status();
		try {

			

			Optional<Asset> optional = assetServices.getAssetByIdOp(asset.getId());

			//System.out.println("Hii " + optional.isPresent());
			if (optional.isPresent()) {
				assetServices.addNewAsset(asset);
				status.setCode(200);
				status.setMessage("Asset Detials Updated .... Successfully");

			} else {
				asset.setActive(1);
				asset.setAvailableStatus(1);
				asset.setAddedDate(new Date());
				assetServices.addNewAsset(asset);
				status.setCode(200);
				status.setMessage("New Asset Added.... Successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	@RequestMapping(value = "/getAssetsByLimit/{page_no}/{item_per_page}/{type}", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAssetsByLimit(@PathVariable("page_no") int page_no,
			@PathVariable("item_per_page") int item_per_page,@PathVariable("type") String type) {
		List<Asset> list = new ArrayList<Asset>();
		try {
						if(type.equalsIgnoreCase("all")){
							list = assetServices.getAssetsByLimit(page_no, item_per_page);

						}else{
						list = assetServices.getAssignedAssetsByLimit(page_no, item_per_page);

						}
			
			
			
			for (Asset asset : list) {
				int currentAge0;
				Date invoiceDate = asset.getInvoiceDate();
				Date today = new Date();
				if (invoiceDate != null) {
					long diff = today.getTime() - invoiceDate.getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);
					int year = (days / 356);
					asset.setAge(String.valueOf(year));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@RequestMapping(value = "/getAssetsWithSystemDetialsByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<SysytemDetailsDTO> getAssetsWithSystemDetialsByLimit(@PathVariable("page_no") int page_no,
			@PathVariable("item_per_page") int item_per_page) {
		List<Asset> list = new ArrayList<Asset>();
		List<SysytemDetailsDTO> listres = new ArrayList<SysytemDetailsDTO>();
		try {
							list = assetServices.getAssetsByLimit(page_no, item_per_page);

						
			
			
			int srNo=0;
			for (Asset asset : list) {
				SysytemDetailsDTO sysytemDetailsDTO= new SysytemDetailsDTO();
				int currentAge0;
				Date invoiceDate = asset.getInvoiceDate();
				Date today = new Date();
				if (invoiceDate != null) {
					long diff = today.getTime() - invoiceDate.getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);
					int year = (days / 356);
					asset.setAge(String.valueOf(year));
				}
				sysytemDetailsDTO.setAssetType(asset.getAssetType());
				sysytemDetailsDTO.setMake(asset.getMake());
				sysytemDetailsDTO.setModel(asset.getModel());
				sysytemDetailsDTO.setSerialNo(asset.getSerialNo());
				sysytemDetailsDTO.setSrNo(srNo);
				
				Optional<CPUDetials> cpuDetials=cPUDetialsRepo.getCPUDetialBySerialNo(asset.getSerialNo());
				Optional<OSDetials> optionalos=oSDetialsRepo.getOSDetailsBySerialNo(asset.getSerialNo());
				Optional<MemoryDetails> optionalMemory=memoryDetailsRepo.getMemoryDetailsBySerialNo(asset.getSerialNo());
				List<DiskDetials> diskDetials=diskDetialsRepo.getDiskDetialsBySerialNo(asset.getSerialNo());
				//String distD=diskDetials.get(0).get
				int s=1;
				if(diskDetials.size()!=0){
					String dtr="";
					for(DiskDetials detials:diskDetials){
						/*if(s==1){
							
						}else{
							dtr+=s+" . "+detials.getName()+" - "+detials.getFreeSpace()+" / "+detials.getVolumeName();
						}*/
						dtr+=s+" . "+detials.getName()+" - "+detials.getFreeSpace()+" / "+detials.getVolumeName()+",";
						
					}
					sysytemDetailsDTO.setNoOfDisk(diskDetials.size());

					sysytemDetailsDTO.setDiskDetials(dtr);
				}
				if(cpuDetials.isPresent()){
					sysytemDetailsDTO.setNumberOfCores(cpuDetials.get().getNumberOfCores());
					sysytemDetailsDTO.setNumberOfProcessors(cpuDetials.get().getNumberOfProcessors());
					sysytemDetailsDTO.setProcessorName(cpuDetials.get().getProcessorName());

				}
				if(optionalMemory.isPresent()){
					sysytemDetailsDTO.setMemoryDetials(optionalMemory.get().getPhysicalTotalInstalled());

				}
				if(optionalos.isPresent()){
					sysytemDetailsDTO.setOsname(optionalos.get().getName());
					sysytemDetailsDTO.setVersion(optionalos.get().getVersion());
				}
			
				
				
				srNo++;
				listres.add(sysytemDetailsDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listres;
	}

	@RequestMapping(value = "/getAssetsByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAssetsByLimitAndSearch(@RequestParam("searchText") String searchText,
			@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage, @RequestParam("type") String type) {
		List<Asset> list = new ArrayList<Asset>();
		try {
			if(type.equalsIgnoreCase("all")){
				list = assetServices.getAssetsByLimitAndSearch(searchText, pageNo, perPage);

			}else{
				list = assetServices.getAssinedAssetsByLimitAndSearch(searchText, pageNo, perPage);

			}


			for (Asset asset : list) {
				int currentAge0;
				Date invoiceDate = asset.getInvoiceDate();
				Date today = new Date();
				// System.out.println("Invoice Date "+invoiceDate+" for
				// "+asset.getId());
				// System.out.println("today "+today);
				if (invoiceDate != null) {
					long diff = today.getTime() - invoiceDate.getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);
					int year = (days / 356);
					// System.out.println ("Days: " + year);
					asset.setAge(String.valueOf(year));
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAssetsWithSystemDetialsByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<SysytemDetailsDTO> getAssetsWithSystemDetialsByLimitAndSearch(@RequestParam("searchText") String searchText,
			@RequestParam("pageNo") int pageNo, @RequestParam("perPage") int perPage) {
		List<Asset> list = new ArrayList<Asset>();
		List<SysytemDetailsDTO> listREs = new ArrayList<SysytemDetailsDTO>();
		try {
			
			list = assetServices.getAssetsByLimitAndSearch(searchText, pageNo, perPage);


			int srNo=0;
			for (Asset asset : list) {
				SysytemDetailsDTO sysytemDetailsDTO= new SysytemDetailsDTO();
				int currentAge0;
				Date invoiceDate = asset.getInvoiceDate();
				Date today = new Date();
				if (invoiceDate != null) {
					long diff = today.getTime() - invoiceDate.getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);
					int year = (days / 356);
					asset.setAge(String.valueOf(year));
				}
				sysytemDetailsDTO.setAssetType(asset.getAssetType());
				sysytemDetailsDTO.setMake(asset.getMake());
				sysytemDetailsDTO.setModel(asset.getModel());
				sysytemDetailsDTO.setSerialNo(asset.getSerialNo());
				sysytemDetailsDTO.setSrNo(srNo);
				
				Optional<CPUDetials> cpuDetials=cPUDetialsRepo.getCPUDetialBySerialNo(asset.getSerialNo());
				Optional<OSDetials> optionalos=oSDetialsRepo.getOSDetailsBySerialNo(asset.getSerialNo());
				Optional<MemoryDetails> optionalMemory=memoryDetailsRepo.getMemoryDetailsBySerialNo(asset.getSerialNo());
				List<DiskDetials> diskDetials=diskDetialsRepo.getDiskDetialsBySerialNo(asset.getSerialNo());
				//String distD=diskDetials.get(0).get
				int s=1;
				String dtr="";
				for(DiskDetials detials:diskDetials){
					/*if(s==1){
						
					}else{
						dtr+=s+" . "+detials.getName()+" - "+detials.getFreeSpace()+" / "+detials.getVolumeName();
					}*/
					dtr+=s+" . "+detials.getName()+" - "+detials.getFreeSpace()+" / "+detials.getVolumeName()+",";
					
				}
				sysytemDetailsDTO.setNoOfDisk(diskDetials.size());

				sysytemDetailsDTO.setDiskDetials(dtr);
				
				sysytemDetailsDTO.setMemoryDetials(optionalMemory.get().getPhysicalTotalInstalled());
				sysytemDetailsDTO.setNumberOfCores(cpuDetials.get().getNumberOfCores());
				sysytemDetailsDTO.setNumberOfProcessors(cpuDetials.get().getNumberOfProcessors());
				sysytemDetailsDTO.setProcessorName(cpuDetials.get().getProcessorName());

				sysytemDetailsDTO.setOsname(optionalos.get().getName());
				sysytemDetailsDTO.setVersion(optionalos.get().getVersion());
				srNo++;
				listREs.add(sysytemDetailsDTO);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listREs;
	}
	@RequestMapping(value = "/getAssetCount/{type}", method = RequestMethod.GET)
	public @ResponseBody int getAssetCount(@PathVariable("type") String type) {
		int count = 0;
		try {
			if(type.equalsIgnoreCase("all")){
				count = assetServices.getAssetsCount();

			}else{
				count = assetServices.getAssingedAssetsCount();

			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@RequestMapping(value = "/getAssetCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int getAssetCountAndSearch(@RequestParam("searchText") String searchText,
			@RequestParam("type") String type) {
		int count = 0;
		try {
			if(type.equalsIgnoreCase("all")){
				count = assetServices.getAssetCountAndSearch(searchText);

			}else{
				count = assetServices.getAssingedAssetCountAndSearch(searchText);

			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@RequestMapping(value = "/getAssetDetialsCounts", method = RequestMethod.GET)
	public @ResponseBody AssetCountDetialDto getAssetDetialsCounts() {
		AssetCountDetialDto detialDto = new AssetCountDetialDto();
		try {
			int totalAssetCount = assetServices.getTotalAssetCount();
			int assingedAssetCount = assetServices.getAssingedAssetCount();
			//System.out.println("Total Count "+totalAssetCount);
			//System.out.println("Ass Count "+assingedAssetCount);

			detialDto.setAssingedAssetCount(assingedAssetCount);
			detialDto.setTotalAssetCount(totalAssetCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detialDto;
	}
	@RequestMapping(value = "/getAssetById", method = RequestMethod.GET)
	public @ResponseBody Asset getAllAsset(@RequestParam("id") int id) {
		Asset asset= new Asset();
		try {
			Optional<Asset> optional = assetServices.getAssetByIdOp(id);
			if(optional.isPresent()){
				asset=optional.get();
			}

		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return asset;
	}
	@RequestMapping(value = "/getAllAsset/{type}", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAllAsset(@PathVariable("type") String type) {
		List<Asset> list= new ArrayList<Asset>();
		try {
			
			if(type.equalsIgnoreCase("all")){
				list=assetServices.getAllAsset();

			}else{
				list=assetServices.getAllAssingedAsset();

			}
			int srNo=1;
			for (Asset asset : list) {
				asset.setSrNo(srNo);
				srNo++;
				int currentAge0;
				Date invoiceDate = asset.getInvoiceDate();
				Date today = new Date();
				// System.out.println("Invoice Date "+invoiceDate+" for
				// "+asset.getId());
				// System.out.println("today "+today);
				if (invoiceDate != null) {
					long diff = today.getTime() - invoiceDate.getTime();
					int days = (int) (diff / 1000 / 60 / 60 / 24);
					int year = (days / 356);
					// System.out.println ("Days: " + year);
					asset.setAge(String.valueOf(year));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllAsset1", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getAllAsset1() {
		List<Asset> list= new ArrayList<Asset>();
		try {
			list=assetServices.getAllAsset();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/checkBySerialNo", method = RequestMethod.GET)
	public @ResponseBody Status checkBySerialNo(@RequestParam("serialNo") String serialNo) {
		Status status = new Status();
		try {
			Optional<Asset> optional = assetServices.checkSerialNo(serialNo);
			if (optional.isPresent()) {
				status.setCode(500);
				status.setMessage("Serial No already exits");
			} else {
				status.setCode(200);
				status.setMessage("Valid Serial No");
			}

		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	@RequestMapping(value = "/uploadAsset", method = RequestMethod.POST)
	public @ResponseBody Status postFile(@ModelAttribute(value = "file") MultipartFile file, HttpServletRequest request,
			@RequestParam("uploadBy") String uploadBy) throws ParseException {
		Status status = new Status();
		int uploadedCount = 0;
		System.out.println("Uploading...............Avoid Duplication");
		int totalCount = 0;
		String responceMsg = "";
		try {
			if (!(file == null)) {
				if (file.isEmpty()) {
				} else {
					//System.out.println(file.getOriginalFilename());
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
						//System.out.println("totalCount " + totalCount);
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

							String assetType = formatter.formatCellValue(row.getCell(1), evaluator);
							String serialNo = formatter.formatCellValue(row.getCell(2), evaluator);
							String assetId = formatter.formatCellValue(row.getCell(3), evaluator);
							String purchaseOrderNo = formatter.formatCellValue(row.getCell(4), evaluator);
							String invoiceNo = formatter.formatCellValue(row.getCell(5), evaluator);
							String invoiceDate = formatter.formatCellValue(row.getCell(6), evaluator);
							String age = formatter.formatCellValue(row.getCell(7), evaluator);
							String statusa = formatter.formatCellValue(row.getCell(8), evaluator);
							String make = formatter.formatCellValue(row.getCell(9), evaluator);
							String model1 = formatter.formatCellValue(row.getCell(10), evaluator);
							String deviceGrouping = formatter.formatCellValue(row.getCell(11), evaluator);
							String deskLocation = formatter.formatCellValue(row.getCell(12), evaluator);

							String branchName = formatter.formatCellValue(row.getCell(13), evaluator);
							String EmpCode = formatter.formatCellValue(row.getCell(14), evaluator);
							String storeLocation = formatter.formatCellValue(row.getCell(15), evaluator);

							if (i == 1) {

								if (formatter.formatCellValue(row.getCell(1), evaluator)
										.equalsIgnoreCase("Asset Type")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(2), evaluator)
										.equalsIgnoreCase("Serial No")) {
									checkExcel++;

								}
								if (formatter.formatCellValue(row.getCell(4), evaluator)
										.equalsIgnoreCase("Purchase Order No")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(5), evaluator)
										.equalsIgnoreCase("Invoice No")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(6), evaluator)
										.equalsIgnoreCase("Invoice Date")) {
									checkExcel++;
								}

								if (formatter.formatCellValue(row.getCell(7), evaluator).equalsIgnoreCase("Age")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(8), evaluator).equalsIgnoreCase("Status")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(9), evaluator).equalsIgnoreCase("Make")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(10), evaluator).equalsIgnoreCase("Model")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(13), evaluator)
										.equalsIgnoreCase("Branch Name")) {
									checkExcel++;
								}
								if (formatter.formatCellValue(row.getCell(14), evaluator)
										.equalsIgnoreCase("Employee Code")) {
									checkExcel++;
								}

								//System.out.println("checkExcel " + checkExcel);
								if (checkExcel == 11) {
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
								//System.out.println("NO DATA " + str1.length());
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
									Optional<Asset> optional2 = assetServices.getAssetBySerialNo(serialNo);
									
									// FOR UPDATE  EXITING 
									if (optional2.isPresent()) {
										
										//System.out.println("Updating :: "+serialNo);
										Asset asset = optional2.get();

										if (assetType != "") {
											asset.setAssetType(assetType);
										}
										if (purchaseOrderNo != "") {
											asset.setPurchaseOrderNo(purchaseOrderNo);
										}
										if (invoiceNo != "") {
											asset.setInvoiceNo(invoiceNo);
										}
										Date iDate = null;

										if (!invoiceDate.equalsIgnoreCase("")) {
											try {
												iDate = new SimpleDateFormat("MM/dd/yy").parse(invoiceDate);
												//System.out.println("Invoice Date "+iDate+"  for   "+invoiceDate);
												asset.setInvoiceDate(iDate);
											} catch (Exception e) {
												// TODO Auto-generated catch
												// block
												responceMsg += " \r\n Invalid Invoice Date Format  For Row" + i;
												e.printStackTrace();
											}

										}
										if(!asset.getStatus().equalsIgnoreCase("Assigned")){
											if (statusa != "") {
												asset.setStatus(statusa);
											}
										}
										
										if (make != "") {
											asset.setMake(make);
										}
										if (model1 != "") {
											asset.setModel(model1);
										}
										if (deskLocation != "") {
											asset.setDeskLocation(deskLocation);
										}
										if (deviceGrouping != "") {
											asset.setDeviceGrouping(deviceGrouping);
										}
										
										
										/*

										if (EmpCode != ""&& asset.getTagCode()!="" ) {
											
										//	if (asset.getTagAlllocationStatus() == 1) {
												Optional<AssetEmployeeAssigned> assingedOptional = assetEmployeeMappeServices
														.getAllocationByAssetId(asset.getAssetId());
												Optional<Employee> optional3 = employeeServices
														.getEmployeeByNoOrSerId(EmpCode);

												if (!assingedOptional.isPresent()) {
													AssetEmployeeAssigned assetEmployeeAssigned = new AssetEmployeeAssigned();
													assetEmployeeAssigned.setAsset(asset);
													if (optional3.isPresent()) {
														asset.setAvailableStatus(0);
														asset.setStatus("Assigned");
														//System.out.println("Employee Present ");
														assetEmployeeAssigned.setEmployee(optional3.get());
														assetEmployeeAssigned.setMappedDate(new Date());
														assetEmployeeAssigned.setMappedStatus(1);
														assetEmployeeAssigned.setMappedBy("Upload Asset");
														assetEmployeeMappeServices.saveAssetEmployee(assetEmployeeAssigned);
														System.out.println("NEW Asset Assigned "+asset.getId());
														AssetTransaction assetTransaction = new AssetTransaction();
														assetTransaction.setAsset(asset);
														assetTransaction.setEmployee(optional3.get());
														assetTransaction.setTransactionDate(new Date());
														assetTransaction.setTranactionType("New Assigned");
														assetEmployeeMappeServices.saveAssetTransaction(assetTransaction);
													} else {
														asset.setAvailableStatus(1);

														//System.out.println("Employee is not Present ");
														responceMsg += " \r\n Employee is not Present for Row No ::"
																+ i;
													}
												
												
												
												} else {
													System.out.println("Asset Is already Assigned "+asset.getId());
											
												
												}
												
												
											//}
										}else{
											System.out.println("EMPLOYEE NOT ASSIGNE");
										}*/
										
										
										assetServices.addNewAsset(asset);
										
										uploadedCount++;
									}
									
									
									// For NEW ENTRy................................................
									else {
										if (assetType == null || assetType == "") {
											responceMsg += " \r\n No Asset Type Found  for Row No ::" + i;
											// //System.out.println("No Tag No
											// Found for Row No ::"+i);
										}
										if (serialNo == null || serialNo == "") {
											responceMsg += " \r\n No SerialNo Found for Row No ::" + i;
											// //System.out.println("No Hostname
											// Found for Row No ::"+i);
										}
										if (statusa == null || statusa == "") {
											responceMsg += " \r\n No Status Found for Row No ::" + i;
											// //System.out.println("No Hostname
											// Found for Row No ::"+i);
										}
										if (make == null || make == "") {
											responceMsg += " \r\n No Make Found for Row No ::" + i;
											// //System.out.println("No Hostname
											// Found for Row No ::"+i);
										}
										if (invoiceDate == "" || invoiceDate == null
												|| invoiceDate.equalsIgnoreCase("")) {
											responceMsg += " \r\n No Invoice Date Found for Row No ::" + i;
										}

										if (model1 == null || model1 == "") {
											responceMsg += " \r\n No Model Found for Row No ::" + i;
											// //System.out.println("No Hostname
											// Found for Row No ::"+i);
										}
										Optional<Branch> optional = branchRepo.getBranchByName(branchName);

										if (branchName == null || branchName == "") {
											responceMsg += " \r\n No Branch Name Found for Row No ::" + i;
											// //System.out.println("No BranchName
											// Found for Row No ::"+i);
										} else {
											if (!optional.isPresent()) {
												responceMsg += " \r\n InValid Branch Name for Row No ::" + i;
												// //System.out.println("InValid
												// Branch Name for Row No
												// ::"+i);
											}

										}
										System.out.println("LOCATION "+storeLocation);
										if (optional.isPresent()) {
											Branch branch = optional.get();
											Asset asset = new Asset();
											/*if (optional2.isPresent()) {
												asset = optional2.get();
											}

											//asset.setAvailableStatus(1);
*/
											asset.setActive(1);
											asset.setAdded_by(uploadBy);
											asset.setAddedDate(new Date());
											asset.setAssetType(assetType);
											asset.setBranch(branch);
											asset.setAge(age);
											asset.setInvoiceNo(invoiceNo);
											Date iDate = null;
											asset.setStoreLocation(storeLocation);
											System.out.println("LOCATION "+storeLocation);

											if (!invoiceDate.equalsIgnoreCase("")) {
												// iDate = new
												// SimpleDateFormat("dd/MM/yyyy").parse(invoiceDate);
												try {
													iDate = new SimpleDateFormat("dd/MM/yyyy").parse(invoiceDate);
													asset.setInvoiceDate(iDate);
												} catch (Exception e) {
													// TODO Auto-generated catch
													// block
													responceMsg += " \r\n Invalid Invoice Date Format  For Row" + i;
													e.printStackTrace();
												}
											}
											asset.setInvoiceDate(iDate);
											asset.setMake(make);
											asset.setModel(model1);
											asset.setPurchaseOrderNo(purchaseOrderNo);
											asset.setSerialNo(serialNo);
											asset.setStoreLocation(storeLocation);
											asset.setStatus(statusa);
											asset.setDeskLocation(deskLocation);
											asset.setDeviceGrouping(deviceGrouping);
											if (!invoiceDate.equalsIgnoreCase("") && assetType != "" && serialNo != ""
													&& statusa != "" && make != "" && model1 != "") {

												

												
												asset.setAvailableStatus(1);
											   assetServices.addNewAsset(asset);
												uploadedCount++;
											}

										}
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
