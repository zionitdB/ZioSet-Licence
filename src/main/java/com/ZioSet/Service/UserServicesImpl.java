/**
 * @ Dattatray Bodhale
 * Jan 27, 2020
 */
package com.ZioSet.Service;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.dto.UserDto;
import com.ZioSet.model.EmailTemplate;
import com.ZioSet.model.LoginOTP;
import com.ZioSet.model.Role;
import com.ZioSet.model.UserInfo;
import com.ZioSet.model.UserLoginLogs;
import com.ZioSet.Repo.LoginOTPRepo;
import com.ZioSet.Repo.RoleRepo;
import com.ZioSet.Repo.UserLoginLogsRepo;
import com.ZioSet.Repo.UserRepo;

@Service
@Transactional
public class UserServicesImpl implements UserServices {

	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	UserLoginLogsRepo userLoginLogsRepo;
	@Autowired
	EmailService emailService;
	@Autowired 
	LoginOTPRepo loginOTPRepo;
	public String generateRandomCode() {
		int digits=4;
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < digits; i++) {
            int digit = random.nextInt(10); // Generates a random number between 0 and 9 (inclusive)
            codeBuilder.append(digit);
        }

        return codeBuilder.toString();
    }
	@Override
	public UserDto loginUser(UserInfo user) {
		UserDto dto=new UserDto();
		try {
			Optional<UserInfo> list =userRepo.getUsersByUsername(user.getUsername());
			if(list.isPresent()) {
				InetAddress localHost = InetAddress.getLocalHost();
	            String ipAddress = localHost.getHostAddress();
	            Optional<UserLoginLogs> optional=userLoginLogsRepo.getUserLoginLogByUserNameAndIP(user.getUsername());
	            if(optional.isPresent()){ 
	            	Date lastlogin=optional.get().getLastLogin();
	            	Date today=new Date();
	            	long diff = today.getTime()-lastlogin.getTime();
	            	 System.out.println("lastlogin  "+lastlogin);

	            	 long days_difference = (diff / (1000*60*60*24)) % 365; 
	            	 System.out.println("days_difference  "+days_difference);
	            	 if(days_difference>=30){
		            	 System.out.println("TRY WITH OTP "+user.isOtpVerified());
	 	            		UserInfo userInfo = list.get();

	            		 if(user.isOtpVerified()){

	 	            		if(userInfo.getPassword().equals(user.getPassword())) {
	 							dto.setCode(200);
	 							dto.setMassage("Sucessfully");
	 							dto.setData(userInfo);
	 							Optional<LoginOTP> otpOptional=loginOTPRepo.getByUserName(user.getUsername());
	 							loginOTPRepo.delete(otpOptional.get());
	 		            		UserLoginLogs loginLogs;
	 			            	
	 			            	Optional<UserLoginLogs> userlogOp=userLoginLogsRepo.getUserLoginLogByUserNameAndIP(user.getUsername());
	 			            	if(userlogOp.isPresent()){
	 			            		loginLogs=userlogOp.get();
	 			            	}else{
	 			            		loginLogs	= new UserLoginLogs();
	 			            	}
	 			            	loginLogs.setLastLogin(new Date());
	 			            	loginLogs.setUser(userInfo);
	 			            	loginLogs.setUserName(user.getUsername());
	 			            	userLoginLogsRepo.save(loginLogs);
	 						}else {
	 							System.out.println("Password NOt  OK");
	 							dto.setCode(500);
	 							dto.setMassage("Password Invalid");
	 						}
	 	            		
	 	            	}else{
	 	            		dto.setCode(300);
	 						dto.setMassage("Enter OTP");
	 						
	 						LoginOTP loginOTP;
	 						Optional<LoginOTP> lognOTPOP=loginOTPRepo.getByUserName(user.getUsername());
	 						if(lognOTPOP.isPresent()){
	 							 loginOTP= lognOTPOP.get();
	 						}else{
	 							 loginOTP= new LoginOTP();
	 						}
	 						String otp=generateRandomCode();
	 						loginOTP.setGeneratedDate(new Date());
	 						loginOTP.setOtp_code(otp);
	 						loginOTP.setUserName(user.getUsername());
	 						loginOTPRepo.save(loginOTP);
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
	 	            	}
	            		 
	            	 }else{
	            		 UserInfo userInfo = list.get();
	 	            	if(userInfo.getPassword().equals(user.getPassword())) {
	 	            		UserLoginLogs loginLogs;
	 	            		Optional<UserLoginLogs> userlogOp=userLoginLogsRepo.getUserLoginLogByUserNameAndIP(user.getUsername());
 			            	if(userlogOp.isPresent()){
 			            		loginLogs=userlogOp.get();
 			            	}else{
 			            		loginLogs	= new UserLoginLogs();
 			            	}
 			            	loginLogs.setLastLogin(new Date());
 			            	loginLogs.setUser(userInfo);
 			            	loginLogs.setUserName(user.getUsername());
 			            	userLoginLogsRepo.save(loginLogs);
	 						System.out.println("Password OK");
	 						dto.setCode(200);
	 						dto.setMassage("Sucessfully");
	 						dto.setData(userInfo);
	 					}else {
	 						System.out.println("Password NOt  OK");
	 						dto.setCode(500);
	 						dto.setMassage("Password Invalid");
	 					}
	            	 }
	            	
	            	
	            }else{
	            	if(user.isOtpVerified()){
	            		UserInfo userInfo = list.get();

	            		if(userInfo.getPassword().equals(user.getPassword())) {
							dto.setCode(200);
							dto.setMassage("Sucessfully");
							dto.setData(userInfo);
							Optional<LoginOTP> otpOptional=loginOTPRepo.getByUserName(user.getUsername());
							loginOTPRepo.delete(otpOptional.get());
		            		UserLoginLogs loginLogs= new UserLoginLogs();
			            	loginLogs.setLastLogin(new Date());
			            	loginLogs.setUser(userInfo);
			            	loginLogs.setUserName(user.getUsername());
			            	userLoginLogsRepo.save(loginLogs);
			            	
						}else {
							System.out.println("Password NOt  OK");
							dto.setCode(500);
							dto.setMassage("Password Invalid");
						}
	            		
	            	}else{
	            		dto.setCode(300);
						dto.setMassage("Enter OTP");
	            		UserInfo userInfo = list.get();

						LoginOTP loginOTP;
						Optional<LoginOTP> lognOTPOP=loginOTPRepo.getByUserName(user.getUsername());
						if(lognOTPOP.isPresent()){
							 loginOTP= lognOTPOP.get();
						}else{
							 loginOTP= new LoginOTP();
						}
						String otp=generateRandomCode();
						loginOTP.setGeneratedDate(new Date());
						loginOTP.setOtp_code(otp);
						loginOTP.setUserName(userInfo.getUsername());
						loginOTPRepo.save(loginOTP);
						
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
						
	            	}
	            	
	            	/*UserInfo userInfo = list.get();
	            	UserLoginLogs loginLogs= new UserLoginLogs();
	            	loginLogs.setLastLogin(new Date());
	            	loginLogs.setUser(userInfo);
	            	loginLogs.setUserName(user.getUsername());
	            	userLoginLogsRepo.save(loginLogs);
	            	
	            	*/
	            	
	            }
				
			}else {
				dto.setCode(500);
				dto.setMassage("User name Invalid");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			dto.setCode(500);
			dto.setMassage("Something Wrong");
		}
		return dto;
	}


	/* (non-Javadoc)
	 * @see com.net.services.UserServices#getUserById(int)
	 */
	@Override
	public UserInfo getUserById(int userId) {
		// TODO Auto-generated method stub
		Optional<UserInfo> list = userRepo.findById(userId);
		return list.isPresent()?list.get():null;
	}


	@Override
	public void saveUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userRepo.save(userInfo);
	}


	@Override
	public List<UserInfo> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}


	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return roleRepo.findAll();
	}


	@Override
	public void deleteUser(UserInfo userInfo) {
		// TODO Auto-generated method stub
		userRepo.delete(userInfo);
	}


	@Override
	public List<UserInfo> getUserByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return userRepo.getUserByLimit(page_no,item_per_page);
	}


	@Override
	public List<UserInfo> getUserByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return userRepo.getUserByLimitAndSearch(searchText,pageNo,perPage);
	}


	@Override
	public int getUserCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return userRepo.getUserCountAndSearch(searchText);
	}


	@Override
	public int getUserCount() {
		// TODO Auto-generated method stub
		return userRepo.getUserCount();
	}


	@Override
	public Optional<UserInfo> getUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepo.getUsersByUsername(userName);
	}


	
	


}
