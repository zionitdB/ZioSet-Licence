/**
 * @ Dattatray Bodhale
 * Jan 27, 2020
 */
package com.ZioSet.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.math3.ml.distance.EarthMoversDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ZioSet.dto.OPTVerfication;
import com.ZioSet.dto.ResponceObject;
import com.ZioSet.dto.Status;
import com.ZioSet.dto.UserDto;
import com.ZioSet.model.Asset;
import com.ZioSet.model.EmailTemplate;
import com.ZioSet.model.LoginOTP;
import com.ZioSet.model.Role;
import com.ZioSet.model.UserInfo;
import com.ZioSet.Repo.LoginOTPRepo;
import com.ZioSet.Repo.UserRepo;
import com.ZioSet.Service.EmailService;
import com.ZioSet.Service.UserServices;


@RestController
@CrossOrigin("*")
@RequestMapping("user")
public class UserController {
	
	@Autowired
	UserServices userServices;
	@Autowired
	UserRepo userRepo;
	@Autowired
	LoginOTPRepo loginOTPRepo;
	@Autowired
	EmailService emailService; 
	
	// Login 
		@RequestMapping(value = "/checkEMail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public @ResponseBody UserDto checkEMail(@RequestBody UserInfo user,HttpServletRequest request ) {
			UserDto userDto = null;
			try {
				String recipient="dattatray.bodhale@a2mee.com";
		       	 String subject="TRIAL  MAIL";
		       	 String message=" THIS TRAIL MAIL FOR LOGIN  a";
		       	/// emailService.sendMail(recipient, subject, message);
		       	 System.out.println("Detials "+request.getRemoteHost());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return userDto;
		}
	// Login 
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody UserDto loginUser(@RequestBody UserInfo user) {
		UserDto userDto = null;
		try {
			System.out.println("USER "+user.getUsername());
			userDto = userServices.loginUser(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDto;
	}
	
	@RequestMapping(value = "/verifyOPT", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponceObject verifyOPT(@RequestBody OPTVerfication optVerfication) {
		ResponceObject responceObject = new ResponceObject();
		try {
			System.out.println("USER "+optVerfication.getUsername());
			Optional<LoginOTP> loginOTPs= loginOTPRepo.getByUserName(optVerfication.getUsername());
			if(loginOTPs.isPresent()){
				if(optVerfication.getOtp().equalsIgnoreCase(loginOTPs.get().getOtp_code())){
					Date nowtime=new Date();
					 long difference_In_Time
		                =nowtime.getTime()  - loginOTPs.get().getGeneratedDate().getTime();
					  long difference_In_Minutes
		                = (difference_In_Time
		                   / (1000 * 60))
		                  % 60;
					  System.out.println("TIME OTP "+loginOTPs.get().getGeneratedDate().getTime());
					  System.out.println("TIME OTP "+nowtime.getTime());
					  if(difference_In_Minutes<=1){
						  responceObject.setCode(200);
							responceObject.setMessage("OTP Verified");
					  }else{
						  responceObject.setCode(600);
							responceObject.setMessage("OTP Expire");
					  }
					  System.out.println(" TIME DIFFF "+difference_In_Minutes);
					   
				}else{
					responceObject.setCode(500);
					responceObject.setMessage("OTP Not Match");
				}
			}else{
				responceObject.setCode(400);
				responceObject.setMessage("OTP NOT Found");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responceObject;
	}
	@RequestMapping(value = "/resendOTP", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponceObject resendOTP(@RequestBody OPTVerfication optVerfication) {
		ResponceObject responceObject = new ResponceObject();
		try {
			Optional<UserInfo> list =userRepo.getUsersByUsername(optVerfication.getUsername());

			System.out.println("USER "+optVerfication.getUsername());
			Optional<LoginOTP> loginOTPs= loginOTPRepo.getByUserName(optVerfication.getUsername());
			String otp=userServices.generateRandomCode();
			if(loginOTPs.isPresent()){
				LoginOTP loginOTP=loginOTPs.get();
				loginOTP.setOtp_code(otp);
				loginOTP.setGeneratedDate(new Date());
				loginOTPRepo.save(loginOTP);
				
			}else{
				LoginOTP loginOTP=new LoginOTP();
				loginOTP.setUserName(optVerfication.getUsername());
				loginOTP.setOtp_code(otp);
				loginOTP.setGeneratedDate(new Date());
				loginOTPRepo.save(loginOTP);
				
			}
			UserInfo userInfo= list.get();
			responceObject.setCode(200);
			responceObject.setMessage("OTP Send Sucessfully");
			String recipient=userInfo.getEmail();
			System.out.println("Mail  "+userInfo.getEmail());

			String subject="OTP For Login";
			String message="";
			
			Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("Login OTP");
			if(templateOp.isPresent()){
				String tempalateMsg=templateOp.get().getTemplateBody();
				System.out.println("TempalateMsg "+tempalateMsg);
				  String fname=tempalateMsg.replaceAll("#FRNM",userInfo.getFirstName());
				  String lname=fname.replaceAll("#LTNM",userInfo.getLastName()+"</br>");
				  String otpN=lname.replaceAll("#OTP",otp+"</br>");
				  String val=otpN.replaceAll("#VLDT", "10");
				  String val1=val.replaceAll("#BRKL", "</br>");
				String message1=val1;
				String signit=templateOp.get().getSigniture();
				String newSig=signit.replaceAll("#BRKL", "</br>");
				message=message1+"</br> </br> </br>"+signit;
				
				subject=templateOp.get().getSubject();

			}else{
				message="Dear "+userInfo.getFirstName()+" "+userInfo.getLastName()+" , </br> Your One Time Password (OTP) is : "+otp+" </br> It is vaid for next 10 minute </br> Do not share with anyone" ;
			}
			System.out.println("message "+message);

			emailService.sendMail(recipient, subject, message);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responceObject;
	}
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public @ResponseBody Status updateUser(@RequestBody UserInfo userInfo) {
		Status status= new Status();
		try {
			userInfo.setActive(1);
			userInfo.setUpdDatetime(new Date());
			userServices.saveUser(userInfo);
			 status.setCode(200);
			 status.setMessage("User Updated .... Successfully");
			 
			 String subject="OTP For Login";
				String message="";
				
				Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("User Registration");
				if(templateOp.isPresent()){
					String tempalateMsg=templateOp.get().getTemplateBody();
					System.out.println("TempalateMsg "+tempalateMsg);
					  String fname=tempalateMsg.replaceAll("#FRNM",userInfo.getFirstName());
					  String lname=fname.replaceAll("#LTNM",userInfo.getLastName()+"</br>");
					  String otpN=lname.replaceAll("#URNM",userInfo.getUsername()+"</br>");
					  String val=otpN.replaceAll("#PSRD", userInfo.getPassword());
					  String val1=val.replaceAll("#BRKL", "</br>");
					String message1=val1;
					String signit=templateOp.get().getSigniture();
					String newSig=signit.replaceAll("#BRKL", "</br>");
					message=message1+"</br> </br> </br>"+signit;
					
					subject=templateOp.get().getSubject();

				}else{
					//message="Dear "+userInfo.getFirstName()+" "+userInfo.getLastName()+" , </br> Your One Time Password (OTP) is : "+otp+" </br> It is vaid for next 10 minute </br> Do not share with anyone" ;
				}
				System.out.println("message "+message);

				emailService.sendMail(userInfo.getEmail(), subject, message);
					
						
		} catch (Exception e) {
			e.printStackTrace();
			 status.setCode(500);
			 status.setMessage("Something Wrong");
		}
		return status;
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public @ResponseBody Status deleteUser(@RequestBody UserInfo userInfo) {
		Status status= new Status();
		try {
			
			userServices.deleteUser(userInfo);
			 status.setCode(200);
			 status.setMessage("User deleted .... Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			 status.setCode(500);
			 status.setMessage("Something Wrong");
		}
		return status;
	}
	
	
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody List<UserInfo> getAllUsers() {
		List<UserInfo> list= new  ArrayList<UserInfo>();
		try {	
			
			list=userServices.getAllUsers();
			System.out.println("LIST "+list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getAllRoles", method = RequestMethod.GET)
	public @ResponseBody List<Role> getAllRoles() {
		List<Role> list= new  ArrayList<Role>();
		try {	
			
			list=userServices.getAllRoles();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getUserByLimit/{page_no}/{item_per_page}", method = RequestMethod.GET)
	public @ResponseBody List<UserInfo> getUserByLimit(@PathVariable("page_no") int page_no,@PathVariable("item_per_page") int item_per_page) {
		List<UserInfo> list= new  ArrayList<UserInfo>();
		try {	
			list=userServices.getUserByLimit(page_no,item_per_page);

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping(value = "/getUserByLimitAndSearch", method = RequestMethod.GET)
	public @ResponseBody List<UserInfo> getUserByLimitAndSearch(@RequestParam("searchText") String searchText,@RequestParam("pageNo") int pageNo,@RequestParam("perPage") int perPage) {
		List<UserInfo> list= new  ArrayList<UserInfo>();
		try {	
			list=userServices.getUserByLimitAndSearch(searchText,pageNo,perPage);

		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/getUserCountAndSearch", method = RequestMethod.GET)
	public @ResponseBody int  getUserCountAndSearch(@RequestParam("searchText") String searchText) {
		int  count= 0;
		try {
			count= userServices.getUserCountAndSearch(searchText);

		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	@RequestMapping(value = "/getUserCount", method = RequestMethod.GET)
	public @ResponseBody int  getUserCount() {
		int  supplierCount= 0;
		try {
			supplierCount= userServices.getUserCount();

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierCount;
	}
	@RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
	public @ResponseBody ResponceObject  checkUserName(@RequestParam("userName") String userName) {
		ResponceObject  object= new ResponceObject();
		try {
			Optional<UserInfo> optional= userServices.getUserByUserName(userName);
			if(optional.isPresent()){
				object.setCode(200);
				object.setMessage("Valid User");
				object.setData(optional.get());
			}else{
				object.setCode(500);
				object.setMessage("Invalid User");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public @ResponseBody Status forgotPassword(@RequestBody UserInfo userInfo) {
		Status status= new Status();
		try {
//		/	System.out.println("New Forgot "+generateRandomPassword(8));
			String newPassword=generateRandomPassword(8);
			userInfo.setPassword(newPassword);
			userServices.saveUser(userInfo);
			if(userInfo.getEmail()==""){
				status.setCode(200);
				status.setMessage("Email Not Found :: Contach Admin for New Passwprd");
			}else{
				status.setCode(200);
				/*status.setMessage("New Password Sent On Email");
				String subject="Asset Tracker : Forgot Password ";
				String 	message ="Hello "+userInfo.getFirstName()+" "+userInfo.getLastName()+"</br> </br> Youre new <b>Asset Tracker </b> login credential is following </br> </br> <b>User Name : </b>"+userInfo.getUsername()+"</br> <b>Password :</b> "+newPassword;
				emailService.sendMail(userInfo.getEmail(),subject, message);*/
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			 status.setCode(500);
			 status.setMessage("Something Wrong");
		}
		return status;
	}
	
	 public static String generateRandomPassword(int len)
	    {
	        // ASCII range – alphanumeric (0-9, a-z, A-Z)
	        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	 
	        SecureRandom random = new SecureRandom();
	        StringBuilder sb = new StringBuilder();
	 
	        // each iteration of the loop randomly chooses a character from the given
	        // ASCII range and appends it to the `StringBuilder` instance
	 
	        for (int i = 0; i < len; i++)
	        {
	            int randomIndex = random.nextInt(chars.length());
	            sb.append(chars.charAt(randomIndex));
	        }
	 
	        return sb.toString();
	    }
	
}
