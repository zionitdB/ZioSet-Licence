package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Service.EmployeeServices;
import com.ZioSet.Service.WorkerLicencesServices;
import com.ZioSet.dto.ResponceObject;
import com.ZioSet.dto.ValidateKeyDTO;
import com.ZioSet.model.Employee;
import com.ZioSet.model.WorkerLicences;
@RestController
@CrossOrigin("*")
@RequestMapping("workerLicenece")
public class WorkerLicencesController {
	
	@Autowired
	WorkerLicencesServices workerLicencesServices;
	
	@RequestMapping(value = "/validateKey", method = RequestMethod.POST)
	public @ResponseBody ResponceObject  validateKey(@RequestBody ValidateKeyDTO validateKeyDTO) {
		ResponceObject responceObject= new ResponceObject();
		try {
			Optional<WorkerLicences> optional=workerLicencesServices.getWorkerLicencesByKey(validateKeyDTO.getLicenceKey());
		if(optional.isPresent()){
			WorkerLicences licences=optional.get();
			if(licences.getStatus()!=1){
				if(licences.getSerialNo().equalsIgnoreCase(validateKeyDTO.getSerialNo())){
					responceObject.setCode(200);
					responceObject.setMessage("Licence Key Verified");
				}else{
					responceObject.setCode(500);
					responceObject.setMessage("Licence Key already used");
				}
			}else{
				licences.setStatus(0);
				licences.setHostName(validateKeyDTO.getHostName());
				licences.setSerialNo(validateKeyDTO.getSerialNo());
				licences.setRegisterDate(new Date());
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.YEAR, 1);
				Date newDate = c.getTime();
				licences.setExpiryDate(newDate);
				workerLicencesServices.save(licences);
				
				responceObject.setCode(200);
				responceObject.setMessage("Licence Key Verified");
			}
		}else{

			responceObject.setCode(500);
			responceObject.setMessage("Invalid Licence Key");
		}
			
		
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/createLicences", method = RequestMethod.GET)
	public @ResponseBody ResponceObject  createLicences(@RequestParam("licencceNO") int licencceNO) {
		ResponceObject responceObject= new ResponceObject();
		try {
			int count= workerLicencesServices.getWorkerLicencesCount();
			if(count==0){
				for(int i=1;i<=licencceNO;i++){
					WorkerLicences workerLicences= new WorkerLicences();
					int c=100000;
					int c1=c+i;
					String value=String.valueOf(c1);
					String serialKey=value.substring(1,value.length());
					String licenceKey="ZNR-IND-PN-"+serialKey;

					System.out.println("Key "+licenceKey);
					workerLicences.setLicenceKey(licenceKey);
					workerLicences.setGeneratedDate(new Date());
					workerLicences.setSerialKey(serialKey);
					workerLicences.setStatus(1);
					workerLicencesServices.save(workerLicences);
				}
			}else{
				String maxSerialKey=workerLicencesServices.getLastInserted();
				for(int i=1;i<=licencceNO;i++){
					WorkerLicences workerLicences= new WorkerLicences();
					String maxKey="1"+maxSerialKey;
					int c=Integer.valueOf(maxKey);
					int c1=c+i;
					String value=String.valueOf(c1);
					String serialKey=value.substring(1,value.length());
					String licenceKey="ZNR-IND-PN-"+serialKey;

					System.out.println("Key "+licenceKey);
					workerLicences.setLicenceKey(licenceKey);
					workerLicences.setGeneratedDate(new Date());
					workerLicences.setSerialKey(serialKey);
					workerLicences.setStatus(1);
					workerLicencesServices.save(workerLicences);
				}
			}
			responceObject.setCode(200);
			responceObject.setMessage("Licence Created successfully");
		
		} catch (Exception e) {
			e.printStackTrace();
			responceObject.setCode(500);
			responceObject.setMessage("Something Wrong");
		}
		return responceObject;
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/getWorkerLicencesByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<WorkerLicences> getWorkerLicencesByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<WorkerLicences> list= new  ArrayList<WorkerLicences>();
		try {	
			list=workerLicencesServices.getWorkerLicencesByLimit(page_no,item_per_page);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getWorkerLicencesByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<WorkerLicences> getWorkerLicencesByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<WorkerLicences> list= new  ArrayList<WorkerLicences>();
		try {	
			
			list=workerLicencesServices.getWorkerLicencesByLimitAndSearch(searchText,pageNo,perPage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getWorkerLicencesCount", method = RequestMethod.GET)
	public @ResponseBody int  getWorkerLicencesCount() {
		int  count= 0;
		try {
			count= workerLicencesServices.getWorkerLicencesCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getWorkerLicencesCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getWorkerLicencesCountAndSearch(@RequestParam("searchText") String searchText,@RequestParam("type") String type) {
		int  count= 0;
		try {
			count= workerLicencesServices.getWorkerLicencesCountAndSearch(searchText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

}
