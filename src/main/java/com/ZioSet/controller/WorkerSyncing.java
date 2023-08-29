package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.management.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.dto.ResponceObject;
import com.ZioSet.model.Asset;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.Bundle;
import com.ZioSet.model.BundleApplications;
import com.ZioSet.model.CPUDetials;
import com.ZioSet.model.DiskDetials;
import com.ZioSet.model.Employee;
import com.ZioSet.model.InstallLicenceStock;
import com.ZioSet.model.MemoryDetails;
import com.ZioSet.model.OSDetials;
import com.ZioSet.model.RamDetials;
import com.ZioSet.model.RamTotal;
import com.ZioSet.model.Software;
import com.ZioSet.Repo.CPUDetialsRepo;
import com.ZioSet.Repo.DiskDetialsRepo;
import com.ZioSet.Repo.InstallLicenceStockRepo;
import com.ZioSet.Repo.MemoryDetailsRepo;
import com.ZioSet.Repo.OSDetialsRepo;
import com.ZioSet.Repo.RamDetialsRepo;
import com.ZioSet.Repo.RamTotalRepo;
import com.ZioSet.Repo.SoftwareRepo;
import com.ZioSet.Service.AssetEmployeeMappeServices;
import com.ZioSet.Service.AssetService;
import com.ZioSet.Service.NotificationService;
 // import com.jayway.jsonpath.Option;

@RestController
@CrossOrigin("*")
@RequestMapping("/workerSync")
public class WorkerSyncing {
	
	
	@Autowired
	NotificationService notificationService;
	
	
	@Autowired
	AssetService assetServices;
	
	@Autowired
	CPUDetialsRepo cPUDetialsRepo;
	
	@Autowired
	DiskDetialsRepo diskDetialsRepo;
	@Autowired
	MemoryDetailsRepo memoryDetailsRepo;
	
	@Autowired
	OSDetialsRepo oSDetialsRepo;
	
	@Autowired
	RamDetialsRepo ramDetialsRepo;
	
	@Autowired
	RamTotalRepo ramTotalRepo;
	@Autowired
	InstallLicenceStockRepo installLicenceStockRepo;
	@Autowired
	AssetEmployeeMappeServices assetEmployeeMappeServices;
	
	@Autowired
	SoftwareRepo softwareRepo;
	
	@Autowired
	AssetService assetService;
	
	@RequestMapping(value = "/addRam", method = RequestMethod.POST)
	public ResponceObject addRam(@RequestBody RamDetials ramDetials)  {
		ResponceObject responceDTO= new ResponceObject();
		try {		 
			
			
			Optional<Asset>optional=assetServices.checkSerialNo(ramDetials.getSystemSerialNo());
			if(optional.isPresent()){
				Optional<RamDetials> optional2=ramDetialsRepo.getRamDetialsBySerialNoAndOther(ramDetials.getSystemSerialNo(),ramDetials.getSize(),ramDetials.getSerialNo(),ramDetials.getPartNo(),ramDetials.getManufacture());
				if(optional2.isPresent()){
					RamDetials detials=optional2.get();
					detials.setAsset(optional.get());

					detials.setAddedDate(new Date());
					ramDetialsRepo.save(detials);
				}else{
					ramDetials.setAsset(optional.get());
					ramDetials.setAddedDate(new Date());

					ramDetialsRepo.save(ramDetials);
				}
				Optional<RamTotal>  optional3=ramTotalRepo.getByAssetId(optional.get().getId());
				if(optional3.isPresent()){
					RamTotal ramTotal= optional3.get();
					ramTotal.setSize(ramDetials.getTotalSize());

					ramTotal.setAddedDate(new Date());
					ramTotalRepo.save(ramTotal);
				}else{
					RamTotal ramTotal= new RamTotal();
					ramTotal.setAddedDate(new Date());
					ramTotal.setAsset(optional.get());
					ramTotal.setSize(ramDetials.getTotalSize());
					ramTotalRepo.save(ramTotal);
				}
				
				
			}
			System.out.println("Adding RAM ......BY API"+ramDetials.getTotalSize());
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}
	
	
	
	
	@RequestMapping(value = "/addCPU", method = RequestMethod.POST)
	public ResponceObject addBundle(@RequestBody CPUDetials cPU)  {
		ResponceObject responceDTO= new ResponceObject();
		try {		 
			
			
			Optional<Asset>optional=assetServices.checkSerialNo(cPU.getSerialNo());
			if(optional.isPresent()){
				Optional<CPUDetials> optional2=cPUDetialsRepo.getCPUDetialBySerialNo(cPU.getSerialNo());
				if(optional2.isPresent()){
					CPUDetials cpuDetials=optional2.get();
					if(!cPU.getProcessorName().equalsIgnoreCase(cpuDetials.getProcessorName())){
						notificationService.sendNotificationForCPUChange(cpuDetials,cPU);
					}
					cpuDetials.setNumberOfCores(cPU.getNumberOfCores());
					cpuDetials.setNumberOfProcessors(cPU.getNumberOfProcessors());
					cpuDetials.setProcessorName(cPU.getProcessorName());
					cpuDetials.setProcessorSpeed(cPU.getProcessorSpeed());
					cpuDetials.setSyncUpdatedDate(new Date());
					cPUDetialsRepo.save(cpuDetials);
					
					
				}else{
					cPU.setAsset(optional.get());
					cPU.setSyncUpdatedDate(new Date());
					cPUDetialsRepo.save(cPU);
				}
			}
			System.out.println("Adding CPU ......BY API");
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}

	@RequestMapping(value = "/addDiskDetials", method = RequestMethod.POST)
	public ResponceObject addDiskDetials(@RequestBody DiskDetials diskDetials)  {
		ResponceObject responceDTO= new ResponceObject();
		try {		 
			
			
			Optional<Asset>optional=assetServices.checkSerialNo(diskDetials.getSerialNo());
			if(optional.isPresent()){
				Optional<DiskDetials> optional2=diskDetialsRepo.getDiskDetialsBySerialNoAndDiskName(diskDetials.getSerialNo(),diskDetials.getName());
				if(optional2.isPresent()){
					DiskDetials cpuDetials=optional2.get();
					cpuDetials.setDescription(diskDetials.getDescription());
					cpuDetials.setFileSystem(diskDetials.getFileSystem());
					cpuDetials.setFreeSpace(diskDetials.getFreeSpace());
					cpuDetials.setName(diskDetials.getName());
					cpuDetials.setSerial(diskDetials.getSerial());
					cpuDetials.setSize(diskDetials.getSize());
					cpuDetials.setSyncUpdatedDate(new Date());
					cpuDetials.setVolumeName(diskDetials.getVolumeName());

					diskDetialsRepo.save(cpuDetials);
					double freeSpace=Double.parseDouble(diskDetials.getFreeSpace().replace("GB", ""));
					double totalSpace=Double.parseDouble(diskDetials.getSize().replace("GB", ""));
					double per=((freeSpace*100)/totalSpace);
					System.out.println("DISK Utilization :: "+per+"  %");
					
				}else{
					diskDetials.setAsset(optional.get());
					diskDetials.setSyncUpdatedDate(new Date());
					diskDetialsRepo.save(diskDetials);
					double freeSpace=Double.parseDouble(diskDetials.getFreeSpace());
					double totalSpace=Double.parseDouble(diskDetials.getSize());
					double per=((freeSpace*100)/totalSpace);
					System.out.println("DISK Utilization :: "+per+"  %");

					
				}
				
				
			}
			System.out.println("Adding diskDetialsRepo ......BY API");
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}
	@RequestMapping(value = "/addMemoryDetials", method = RequestMethod.POST)
	public ResponceObject addMemoryDetials(@RequestBody MemoryDetails memoryDetails)  {
		ResponceObject responceDTO= new ResponceObject();
		try {		 
			
			
			Optional<Asset>optional=assetServices.checkSerialNo(memoryDetails.getSerialNo());
			if(optional.isPresent()){
				Optional<MemoryDetails> optional2=memoryDetailsRepo.getMemoryDetailsBySerialNo(memoryDetails.getSerialNo());
				if(optional2.isPresent()){
					MemoryDetails memoryDetails2=optional2.get();
					memoryDetails2.setPhysicalFree(memoryDetails.getPhysicalFree());
					memoryDetails2.setPhysicalInUse(memoryDetails.getPhysicalInUse());
					memoryDetails2.setPhysicalTotalInstalled(memoryDetails.getPhysicalTotalInstalled());
					memoryDetails2.setVirtualFree(memoryDetails.getVirtualFree());
					memoryDetails2.setVirtualTotalInstalled(memoryDetails.getVirtualTotalInstalled());
					memoryDetails2.setVirtualInUse(memoryDetails.getVirtualInUse());
					memoryDetails2.setSyncUpdatedDate(new Date());

					memoryDetailsRepo.save(memoryDetails2);
				}else{
					memoryDetails.setAsset(optional.get());
					memoryDetails.setSyncUpdatedDate(new Date());
					memoryDetailsRepo.save(memoryDetails);
				}
			}
			System.out.println("Adding Memory ......BY API");
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}
	
	@RequestMapping(value = "/addOS", method = RequestMethod.POST)
	public ResponceObject addOS(@RequestBody OSDetials osDetials)  {
		ResponceObject responceDTO= new ResponceObject();
		try {		 
			
			
			Optional<Asset>optional=assetServices.checkSerialNo(osDetials.getSerialNo());
			if(optional.isPresent()){
				Optional<OSDetials> optional2=oSDetialsRepo.getOSDetailsBySerialNo(osDetials.getSerialNo());
				if(optional2.isPresent()){
					OSDetials osDetials2=optional2.get();
					if(!osDetials.getName().equalsIgnoreCase(osDetials2.getName())){
						notificationService.sendNotificationForOS("OS",osDetials,osDetials2);
					}
					if(!osDetials.getVersion().equalsIgnoreCase(osDetials2.getVersion())){
						notificationService.sendNotificationForOS("Version",osDetials,osDetials2);
					}
					if(!osDetials.getOsSerialNumber().equalsIgnoreCase(osDetials2.getOsSerialNumber())){
						notificationService.sendNotificationForOS("Serial Number",osDetials,osDetials2);
					}
					osDetials2.setLastBootTime(osDetials.getLastBootTime());
					osDetials2.setName(osDetials.getName());
					osDetials2.setOsSerialNumber(osDetials.getOsSerialNumber());
					osDetials2.setTimeZone(osDetials.getTimeZone());
					osDetials2.setVersion(osDetials.getVersion());
					osDetials.setAsset(optional.get());

					osDetials2.setSyncUpdatedDate(new Date());
					System.out.println("OLD OS  "+osDetials2.getName());
					System.out.println("New OS  "+osDetials.getName());

					oSDetialsRepo.save(osDetials2);
					
				}else{
					osDetials.setAsset(optional.get());
					osDetials.setSyncUpdatedDate(new Date());
					oSDetialsRepo.save(osDetials);
				}
			}
			System.out.println("Adding OS ......BY API");
			return responceDTO	;
		} catch (Exception e) {
			e.printStackTrace();
			responceDTO.setCode(500);
			responceDTO.setMessage(e.getMessage());
			return responceDTO	;
		}
	}
	
	@RequestMapping(value = "/getOSDetialsByAssetId", method = RequestMethod.GET)
	public @ResponseBody OSDetials getOSDetialsByAssetId(@RequestParam("serialNo") String serialNo) {
		OSDetials detials= new  OSDetials();
		try {	
			Optional<OSDetials> optional=oSDetialsRepo.getOSDetailsBySerialNo(serialNo);
			if(optional.isPresent()){
				detials=optional.get();
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detials;
	}
	@RequestMapping(value = "/getCPUDetialsByAssetId", method = RequestMethod.GET)
	public @ResponseBody CPUDetials getCPUDetialsByAssetId(@RequestParam("serialNo") String serialNo) {
		CPUDetials detials= new  CPUDetials();
		try {	
			Optional<CPUDetials> optional=cPUDetialsRepo.getCPUDetialBySerialNo(serialNo);
			if(optional.isPresent()){
				detials=optional.get();
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detials;
	}
	@RequestMapping(value = "/getMemoryDetailsDetialsByAssetId", method = RequestMethod.GET)
	public @ResponseBody MemoryDetails getMemoryDetailsDetialsByAssetId(@RequestParam("serialNo") String serialNo) {
		MemoryDetails detials= new  MemoryDetails();
		try {	
			Optional<MemoryDetails> optional=memoryDetailsRepo.getMemoryDetailsBySerialNo(serialNo);
			if(optional.isPresent()){
				detials=optional.get();
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detials;
	}
	
	@RequestMapping(value = "/getDiskDetailsDetialsByAssetId", method = RequestMethod.GET)
	public @ResponseBody List<DiskDetials> getDiskDetailsDetialsByAssetId(@RequestParam("serialNo") String serialNo) {
		List<DiskDetials> detials= new  ArrayList<DiskDetials>();
		try {	
			detials=diskDetialsRepo.getDiskDetialsBySerialNo(serialNo);
			

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detials;
	}
	
	
	
	@RequestMapping(value = "/getUnavailableWorker", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getUnavailableWorker() {
		List<Asset> detials= new  ArrayList<Asset>();
		try {	
			List<Asset> allAsset=assetServices.getAllAsset();
			int srNo=1;
			for(Asset asset:allAsset){
				
				List<InstallLicenceStock> list= installLicenceStockRepo.getAllInstallLicenceStocksBySerialNo(asset.getSerialNo());
				if(list.size()==0){
					
					Optional<AssetEmployeeAssigned> assetEmpOp=assetEmployeeMappeServices.getAllocationByAsset(asset.getId());
					asset.setSrNo(srNo);
					srNo++;
					System.out.println("EMP ASSIGNE  "+assetEmpOp.isPresent());
					if(assetEmpOp.isPresent()){
						asset.setEmployee(assetEmpOp.get().getEmployee());
						detials.add(asset);
						
					}else{
						detials.add(asset);

					}
				}
			}
					
			

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detials;
	}
	@RequestMapping(value = "/getUnavailableWorkerForLast5Days", method = RequestMethod.GET)
	public @ResponseBody List<Asset> getUnavailableWorkerForLast5Days() {
		List<Asset> detials= new  ArrayList<Asset>();
		try {	
			List<Asset> allAsset=assetServices.getAllAsset();
			int srNo=1;
			List<Integer> categories =installLicenceStockRepo.getListWorkerInstalledList();

			for(int id:categories){
				Optional<Asset> assetOp =assetService.getAssetByIdOp(id);
				Asset asset=assetOp.get(); 
				Date today= new Date();
				Calendar c = Calendar.getInstance(); 
				c.setTime(today); 
				c.add(Calendar.DATE, -5);
				Date nextDate = c.getTime();
			//	System.out.println("EMP nextDate  "+nextDate);

				List<Software> list= softwareRepo.getAllSoftwareBySerialNoAndBeetweenDate(asset.getSerialNo(),today,nextDate);
				if(list.size()==0){
					
					Optional<AssetEmployeeAssigned> assetEmpOp=assetEmployeeMappeServices.getAllocationByAsset(asset.getId());
					asset.setSrNo(srNo);
					srNo++;
					//System.out.println("EMP ASSIGNE  "+assetEmpOp.isPresent());
					if(assetEmpOp.isPresent()){
						asset.setEmployee(assetEmpOp.get().getEmployee());
						detials.add(asset);
						
					}else{
						detials.add(asset);

					}
				}
			}
					
			

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detials;
	}
	
}
