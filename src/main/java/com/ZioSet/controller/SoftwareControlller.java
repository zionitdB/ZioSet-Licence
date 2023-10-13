package com.ZioSet.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Service.AssetEmployeeMappeServices;
import com.ZioSet.Service.SoftwareService;
import com.ZioSet.configuration.SoftwareDetailsInsert;
import com.ZioSet.dto.LastFiveDateCountDTO;
import com.ZioSet.dto.RequestObj;
import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.Software;

@RestController
@CrossOrigin("*")
@RequestMapping("software")
public class SoftwareControlller {

	@Autowired
	SoftwareService softwareService;
	@Autowired
	AssetEmployeeMappeServices assetEmployeeMappeServices;
	
	
	@RequestMapping(value = "/sycingByWorker1", method = RequestMethod.POST)
	public @ResponseBody void sycingByWorker1(@RequestBody Software softwares) {
		try {
			System.out.println("Syncing........................................................."+softwares.getSerialNo());
			/*SoftwareDetailsInsert detailsInser =new SoftwareDetailsInsert(softwares);
			detailsInser.start();
			*/softwareService.addSoftwareOnj(softwares);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	@RequestMapping(value = "/sycingByWorker2", method = RequestMethod.POST)
	public @ResponseBody void sycingByWorker2(@RequestBody List<Software> softwares) {
		try {
			System.out.println("Syncing Starts .........................................................");

			for(Software  software:softwares){
				softwareService.addSoftwareOnj(software);
				System.out.println("Application........................................................."+software.getApplicationName()+" "+software.getSystemIp());

			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}


	@RequestMapping(value = "/getInstallLicenceCountForLastDays", method = RequestMethod.GET)
	public @ResponseBody LastFiveDateCountDTO getInstallLicenceCountForLastDays() {
		LastFiveDateCountDTO countDTO = new LastFiveDateCountDTO();
		List<String> dates = new ArrayList<String>();
		List<Integer> counts = new ArrayList<Integer>();
		try {
			Date latestDate = softwareService.getLatestDate();
			Date SecondDate = softwareService.getSecondDate(latestDate);
			Date ThirdDate = softwareService.getSecondDate(SecondDate);
			Date FourthDate = softwareService.getSecondDate(ThirdDate);
			Date FifthDate = softwareService.getSecondDate(FourthDate);

			Format formatter = new SimpleDateFormat("yyyy-MM-dd");
			int countByFistDate = 0;
			int countBySecondDate = 0;
			int countByThirdDate = 0;
			int countByFourthDate = 0;
			int countByFiftDate = 0;

			String s1 = "";
			String s2 = "";
			String s3 = "";
			String s4 = "";
			String s5 = "";
			System.out.println("Latest Date "+latestDate);
			System.out.println("SecondDate "+SecondDate);
			System.out.println("ThirdDate"+ThirdDate);
			System.out.println("FourthDate"+FourthDate);
			System.out.println("FifthDate "+FifthDate);

			if (latestDate != null) {
				countByFistDate = softwareService.getFetchCountByDate(latestDate);
				s1 = formatter.format(latestDate);
			} else {
				Date dateInstance = new Date();

				s1 = formatter.format(dateInstance);
			}

			if (SecondDate != null) {
				countBySecondDate = softwareService.getFetchCountByDate(SecondDate);
				s2 = formatter.format(SecondDate);
			} else {
				Date dateInstance = new Date();

				Calendar cal = Calendar.getInstance();
				cal.setTime(dateInstance);
				cal.add(Calendar.DATE, -1);
				Date newDate = cal.getTime();
				s2 = formatter.format(newDate);
			}
			if (ThirdDate != null) {
				countByThirdDate = softwareService.getFetchCountByDate(ThirdDate);
				s3 = formatter.format(ThirdDate);
			} else {
				Date dateInstance = new Date();

				Calendar cal = Calendar.getInstance();
				cal.setTime(dateInstance);
				cal.add(Calendar.DATE, -2);
				Date newDate = cal.getTime();
				s3 = formatter.format(newDate);
			}
			if (FourthDate != null) {
				countByFourthDate = softwareService.getFetchCountByDate(FourthDate);
				s4 = formatter.format(FourthDate);
			} else {
				Date dateInstance = new Date();

				Calendar cal = Calendar.getInstance();
				cal.setTime(dateInstance);
				cal.add(Calendar.DATE, -3);
				Date dateBefore30Days = cal.getTime();
				Date newDate = cal.getTime();
				s4 = formatter.format(newDate);
			}
			if (FifthDate != null) {
				countByFiftDate = softwareService.getFetchCountByDate(FifthDate);
				s5 = formatter.format(FifthDate);
			} else {
				Date dateInstance = new Date();

				Calendar cal = Calendar.getInstance();
				cal.setTime(dateInstance);
				cal.add(Calendar.DATE, -4);
				Date dateBefore30Days = cal.getTime();
				Date newDate = cal.getTime();
				s5 = formatter.format(newDate);
			}

			counts.add(countByFistDate);
			counts.add(countBySecondDate);
			counts.add(countByThirdDate);
			counts.add(countByFourthDate);
			counts.add(countByFiftDate);

			dates.add(s1);
			dates.add(s2);
			dates.add(s3);
			dates.add(s4);
			dates.add(s5);

			countDTO.setDates(dates);
			countDTO.setCounts(counts);

			System.out.println("First Date " + latestDate + "  " + countByFistDate);
			System.out.println("SecondDate Date " + SecondDate + "  " + countBySecondDate);
			System.out.println("ThirdDate Date " + ThirdDate + "  " + countByThirdDate);
			System.out.println("FourthDate Date " + FourthDate + "  " + countByFourthDate);
			System.out.println("FifthDate Date " + FifthDate + "  " + countByFiftDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return countDTO;
	}
	@RequestMapping(value = "/getInstallLiceneceByDate", method = RequestMethod.POST)
	public @ResponseBody List<Software> getInstallLiceneceByDate(@RequestBody RequestObj requestObj) {
		List<Software> list= new ArrayList<Software>();
		try {
			list = softwareService.getInstallLiceneceByDate(requestObj.getDate());
			int srNo=1;
			
			System.out.println("LIST SIZE "+list.size()+" "+requestObj.getDate());
			for(Software software:list){
				/*if(software.getInstallDate()!=null){
					Format formatter = new SimpleDateFormat("dd-MM-yyyy");
					String s = formatter.format(software.getInstallDate());
					software.setInstallDate(s);
				}*/
				/*Optional<AssetEmployeeAssigned>  optional= assetEmployeeMappeServices.getAllocationByAsset(software.getAsset().getId());
				if(optional.isPresent()){
					software.setEmployee(optional.get().getEmployee());
				}*/
				
				software.setSrNo(srNo);
				srNo++;
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
