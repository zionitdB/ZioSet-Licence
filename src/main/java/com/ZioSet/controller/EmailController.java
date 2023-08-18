package com.ZioSet.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.Service.EmailService;
import com.ZioSet.dto.EmailDetials;
import com.ZioSet.dto.EmailReceiverCountDto;
import com.ZioSet.dto.ResponceObject;
import com.ZioSet.dto.Status;
import com.ZioSet.model.EmailReceiver;
import com.ZioSet.model.EmailSenderDetials;
import com.ZioSet.model.EmailTemplate;





@RestController
@CrossOrigin("*")
@RequestMapping("email")
public class EmailController {
	
	@Autowired
	EmailService  emailSmsService;
	
	
	
	@RequestMapping(value = "/testMail", method = RequestMethod.GET)

	public @ResponseBody ResponceObject sendMail() {
		ResponceObject status =new ResponceObject();
		try {
			
			
			status=	emailSmsService.sendMail("dattatray.bodhale@zionit.in","TEST ", "HELlo");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)

	public @ResponseBody ResponceObject sendMail(@RequestBody EmailDetials emailDetials) {
		ResponceObject status =new ResponceObject();
		try {
			
			
			status=	emailSmsService.sendMail(emailDetials.getRecipient(),emailDetials.getSubject(), emailDetials.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
		
	@RequestMapping(value = "/addEmailDetial", method = RequestMethod.POST)
	public @ResponseBody Status addEmailDetial(@RequestBody EmailSenderDetials emailSenderDetials) {
		Status status =new Status();
		try {
			List<EmailSenderDetials> list=emailSmsService.getAllEmailSenderDetials();
			emailSenderDetials.setActive(1);
			emailSenderDetials.setAddedDate(new Date());
			emailSmsService.addEmailDetial(emailSenderDetials);
			for(EmailSenderDetials detials :list){
				if(emailSenderDetials.getId()!=detials.getId()){
					detials.setActive(0);
					emailSmsService.addEmailDetial(detials);
				}
				
				
			}
			
			status.setCode(200);
			status.setMessage("Email Detials Saved..........Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	@RequestMapping(value = "/getAllEmailSenderDetials", method = RequestMethod.GET)
	public @ResponseBody List<EmailSenderDetials> getAllEmailSenderDetials() {
		Status status =new Status();
		List<EmailSenderDetials> list =null;
		try {
			list=emailSmsService.getAllEmailSenderDetials();
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return list;
	}
	
	@RequestMapping(value = "/changeStatusEmail", method = RequestMethod.POST)
	public @ResponseBody Status changeStatusEmail(@RequestBody EmailSenderDetials emailSenderDetials) {
		Status status =new Status();
		try {
			if(emailSenderDetials.getActive()==1){
				emailSenderDetials.setActive(0);
				emailSmsService.addEmailDetial(emailSenderDetials);

			}else{
				List<EmailSenderDetials> list=emailSmsService.getAllEmailSenderDetials();

				for(EmailSenderDetials detials :list){
					if(detials.getId()==emailSenderDetials.getId()){
						detials.setActive(1);
						emailSmsService.addEmailDetial(detials);
					}else{
						detials.setActive(0);
						emailSmsService.addEmailDetial(detials);
					}
					
					
				}
			}
			
			
			status.setCode(200);
			status.setMessage("Status Changed............ Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	@RequestMapping(value = "/deletEmailDetial", method = RequestMethod.POST)
	public @ResponseBody Status deletEmailDetial(@RequestBody EmailSenderDetials emailSenderDetials) {
		Status status =new Status();
		try {
			
			emailSmsService.deletEmailDetial(emailSenderDetials);
			
			
			status.setCode(200);
			status.setMessage("Data Deleted............ Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	
	
	
	
	@RequestMapping(value = "/addEmailReceiver", method = RequestMethod.POST)
	public @ResponseBody Status addEmailReceiver(@RequestBody EmailReceiver emailReceiver) {
		Status status =new Status();
		try {emailReceiver.setActive(1);
		emailReceiver.setAddedDate(new Date());
			emailSmsService.addEmailReceiver(emailReceiver);
			status.setCode(200);
			status.setMessage("Email Detials Saved..........Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	
	
	@RequestMapping(value = "/getAllEmailReceiver/{type}", method = RequestMethod.GET)
	public @ResponseBody List<EmailReceiver> getAllEmailReceiver(@PathVariable("type") String type) {
		Status status =new Status();
		List<EmailReceiver> list =null;
		try {
			if(type.equalsIgnoreCase("all")){
				list=emailSmsService.getAllEmailReceiver();
			}
			if(type.equalsIgnoreCase("active")){
				list=emailSmsService.getActiveAllEmailReceiver();
			}
			if(type.equalsIgnoreCase("pune")){
				list=emailSmsService.getPuneAllEmailReceiver();
			}
			if(type.equalsIgnoreCase("bengaluru")){
				list=emailSmsService.getBengaluruAllEmailReceiver();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return list;
	}
	
	@RequestMapping(value = "/changeStatusEmailReceiver", method = RequestMethod.POST)
	public @ResponseBody Status changeStatusEmailReceiver(@RequestBody EmailReceiver emailReceiver) {
		Status status =new Status();
		try {
			if(emailReceiver.getActive()==1){
				emailReceiver.setActive(0);
				emailSmsService.addEmailReceiver(emailReceiver);

			}else{
				emailReceiver.setActive(1);
				emailSmsService.addEmailReceiver(emailReceiver);
			}
			
			
			status.setCode(200);
			status.setMessage("Status Changed............ Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	@RequestMapping(value = "/deletEmailReceiver", method = RequestMethod.POST)
	public @ResponseBody Status deletEmailReceiver(@RequestBody EmailReceiver emailReceiver) {
		Status status =new Status();
		try {
			
			emailSmsService.deletEmailReceiver(emailReceiver);
			
			
			status.setCode(200);
			status.setMessage("Data Deleted............ Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	

	@RequestMapping(value = "/getMailCounts", method = RequestMethod.GET)
	public @ResponseBody EmailReceiverCountDto getMailCounts() {
		EmailReceiverCountDto emailReceiverCountDto =new EmailReceiverCountDto();
		List<EmailReceiver> list =null;
		try {
			int totalRecieverCount=emailSmsService.getAllEmailReceiverCount();
			int activeRecieverCount=emailSmsService.getActiveRciverCount();
			int puneRecieverCount=emailSmsService.getPuneAllEmailReceiverCount();
			int bengaluruRecieverCount=emailSmsService.getBengaluruAllEmailReceiverCount();
			emailReceiverCountDto.setActiveRecieverCount(activeRecieverCount);
			emailReceiverCountDto.setTotalRecieverCount(totalRecieverCount);
			emailReceiverCountDto.setBengaluruRecieverCount(bengaluruRecieverCount);
			emailReceiverCountDto.setPuneRecieverCount(puneRecieverCount);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return emailReceiverCountDto;
	}
	
	
//************************************************************ EMAIL TEMPLATE *********************************************************//

	
	@RequestMapping(value = "/addEmailTemplate", method = RequestMethod.POST)
	public @ResponseBody Status addEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
		Status status =new Status();
		try {
			emailTemplate.setActive(1);
			emailTemplate.setAddedDate(new Date());
			emailSmsService.addEmailTemplate(emailTemplate);
			status.setCode(200);
			status.setMessage("Email Template Saved..........Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	@RequestMapping(value = "/deletEmailTemplate", method = RequestMethod.POST)
	public @ResponseBody Status deletEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
		Status status =new Status();
		try {
			
			emailSmsService.deletEmailTemplate(emailTemplate);
			
			
			status.setCode(200);
			status.setMessage("EmailTemplate Deleted............ Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	@RequestMapping(value = "/changeStatusEmailTemplate", method = RequestMethod.POST)
	public @ResponseBody Status changeStatusEmailTemplate(@RequestBody EmailTemplate emailTemplate) {
		Status status =new Status();
		try {
			if(emailTemplate.getActive()==1){
				emailTemplate.setActive(0);
				emailSmsService.addEmailTemplate(emailTemplate);

			}else{
				emailTemplate.setActive(1);
				emailSmsService.addEmailTemplate(emailTemplate);
			}
			
			
			status.setCode(200);
			status.setMessage("Status Changed............ Successfully!!!");
		} catch (Exception e) {
			e.printStackTrace();
			status.setCode(500);
			status.setMessage("Something Wrong");
		}
		return status;
	}
	
	@RequestMapping(value = "/getAllEmailTemplate", method = RequestMethod.GET)
	public @ResponseBody List<EmailTemplate> getAllEmailTemplate() {
		
		List<EmailTemplate> list =new ArrayList<EmailTemplate>();
		try {
			
				list=emailSmsService.getAllEmailTemplate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return list;
	}
}
