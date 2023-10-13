package com.ZioSet.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.Repo.NotificationRepo;
import com.ZioSet.model.CPUDetials;
import com.ZioSet.model.DiskDetials;
import com.ZioSet.model.EmailReceiver;
import com.ZioSet.model.EmailTemplate;
import com.ZioSet.model.Notification;
import com.ZioSet.model.OSDetials;
import com.ZioSet.model.RamDetials;

@Service
public class NotificationServiceImpl implements NotificationService {

	
	@Autowired
	NotificationRepo notificationRepo;
	
	@Autowired
	EmailService emailService;
	@Override
	public void sendNotificationForCPUChange(CPUDetials cpuDetials, CPUDetials cPU) {
		// TODO Auto-generated method stub
		Notification notification= new Notification();
		String message="Alert we are found that CPU are change on "+cpuDetials.getAsset().getMake()+"-"+cpuDetials.getAsset().getModel()+"-"+cpuDetials.getAsset().getSerialNo()+" From "+cpuDetials.getProcessorName()+" to  New "+cPU.getProcessorName();
		
		notification.setMesssge(message);
		notification.setNotificationDatatime(new Date());
		notification.setView_bit(0);
		Optional<Notification> optional= notificationRepo.getNotificationListByMessage(message);
		if(!optional.isPresent()){
			notificationRepo.save(notification);
			Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("CPU Change");
			if(templateOp.isPresent()){
				String tempalateMsg=templateOp.get().getTemplateBody();
				System.out.println("TempalateMsg "+tempalateMsg);
				  String otpN=tempalateMsg.replaceAll("#ASMK",cpuDetials.getAsset().getMake()+"</br>");
				  String val=otpN.replaceAll("#ASML",cpuDetials.getAsset().getModel()+"</br>");
				  String val1=val.replaceAll("#SRNO", cpuDetials.getAsset().getSerialNo()+"</br>");
				  String val12=val1.replaceAll("#OLCP", cpuDetials.getProcessorName()+"</br>");
				  String val122=val12.replaceAll("#NWCP", cPU.getProcessorName()+"</br>");

				String message1=val122;
				String signit=templateOp.get().getSigniture();
				String newSig=signit.replaceAll("#BRKL", "</br>");
				message=message1+"</br> </br> </br>"+signit;
				
				String subject=templateOp.get().getSubject();
				sendMailToSender(subject,message);
				//emailService.sendMail("dattatray.bodhale@a2mee.com", subject, message);

			}
		}
		
		
	}

	@Override
	public void sendNotificationForOS(String string, OSDetials osDetials, OSDetials osDetials2) {
		// TODO Auto-generated method stub
		
		
		Notification notification= new Notification();
		String message="Alert we are found that "+string+" are change on "+osDetials2.getAsset().getMake()+"-"+osDetials2.getAsset().getModel()+"-"+osDetials2.getAsset().getSerialNo()+" From "+osDetials2.getName()+" to  New "+osDetials2.getName();
		
		notification.setMesssge(message);
		notification.setNotificationDatatime(new Date());
		notification.setView_bit(0);
		Optional<Notification> optional= notificationRepo.getNotificationListByMessage(message);
		if(!optional.isPresent()){
			notificationRepo.save(notification);
			Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("OS Change");
			if(templateOp.isPresent()){
				String tempalateMsg=templateOp.get().getTemplateBody();
				System.out.println("TempalateMsg "+tempalateMsg);
				  //String fname=tempalateMsg.replaceAll("#EMPN","Dattatray");
				  String otpN=tempalateMsg.replaceAll("#ASMK",osDetials2.getAsset().getMake()+"</br>");
				  String val=otpN.replaceAll("#ASML",osDetials2.getAsset().getModel()+"</br>");
				  String val1=val.replaceAll("#SRNO", osDetials2.getAsset().getSerialNo()+"</br>");
				  String val12=val1.replaceAll("#OLOS", osDetials2.getName()+"</br>");
				  String val122=val12.replaceAll("#NWOS", osDetials.getName()+"</br>");
				  String val1221=val122+"</br></br></br>"+string;
				String message1=val1221;
				String signit=templateOp.get().getSigniture();
				String newSig=signit.replaceAll("#BRKL", "</br>");
				message=message1+"</br> </br> </br>"+signit;
				
				String subject=templateOp.get().getSubject();
				sendMailToSender(subject, message);

			}
		}
		
	}

	@Override
	public void sendRamNotification(String changeString, RamDetials ramDetials, RamDetials ramDetials2) {
		// TODO Auto-generated method stub
		Notification notification= new Notification();
		String message="Alert we are found that "+changeString+" has change on "+ramDetials.getAsset().getMake()+"-"+ramDetials.getAsset().getModel()+"-"+ramDetials.getAsset().getSerialNo()+"";
		if(changeString.equalsIgnoreCase("Manufacture")){
			message+="OLD Manufature is "+ramDetials.getManufacture()+" And NEW Manufature "+ramDetials2.getManufacture();
		}
		if(changeString.equalsIgnoreCase("Part No")){
			message+="OLD Part No is "+ramDetials.getPartNo()+" And NEW Part No "+ramDetials2.getPartNo();
		
		}
		if(changeString.equalsIgnoreCase("Serial Number")){
			message+="OLD Serial Number is "+ramDetials.getSerialNo()+" And NEW Serial Number "+ramDetials2.getSerialNo();

		}
		if(changeString.equalsIgnoreCase("Size")){
			message+="OLD Size is "+ramDetials.getSize()+" And NEW Size "+ramDetials2.getSize();

		}
		Optional<Notification> optional= notificationRepo.getNotificationListByMessage(message);
		if(!optional.isPresent()){
			notification.setMesssge(message);
			notification.setNotificationDatatime(new Date());
			notification.setView_bit(0);
			notificationRepo.save(notification);
			Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("CPU Change");
			if(templateOp.isPresent()){
				String tempalateMsg=templateOp.get().getTemplateBody();
				System.out.println("TempalateMsg "+tempalateMsg);
				  //String fname=tempalateMsg.replaceAll("#EMPN","Dattatray");
				  String otpN=tempalateMsg.replaceAll("#ASMK",ramDetials.getAsset().getMake()+"</br>");
				  String val=otpN.replaceAll("#ASML",ramDetials.getAsset().getModel()+"</br>");
				  String val1=val.replaceAll("#SRNO", ramDetials.getAsset().getSerialNo()+"</br></br>   OLD RAM DETAILS");
				  String val12=val1.replaceAll("#OLMF", ramDetials2.getManufacture()+"</br>");
				  String oldP=val12.replaceAll("#OLPN", ramDetials2.getPartNo()+"</br>");
				  String oldS=oldP.replaceAll("#OLPN", ramDetials2.getSerialNo()+"</br>");
				  String oldSz=oldS.replaceAll("#OLPN", ramDetials2.getSize()+"</br> </br> NEW RAM DETAILS");

				  
				  String newM=oldSz.replaceAll("#NWMF", ramDetials.getManufacture()+"</br>");
				  String newP=newM.replaceAll("#NWPN", ramDetials.getPartNo()+"</br>");
				  String newS=newP.replaceAll("#NWPN", ramDetials.getSerialNo()+"</br>");
				  String newSz=newP.replaceAll("#NWPN", ramDetials.getSize()+"</br>");
				String message1=newSz;
				String signit=templateOp.get().getSigniture();
				String newSig=signit.replaceAll("#BRKL", "</br>");
				message=message1+"</br> </br> </br>"+signit;
				
				String subject=templateOp.get().getSubject();
				//emailService.sendMail("dattatray.bodhale@a2mee.com", subject, message);
				sendMailToSender(subject, message);
			}
		}
		
	}

	@Override
	public void sendDiskUtilizationNotification(DiskDetials diskDetials, double per) {
		// TODO Auto-generated method stub
		Notification notification= new Notification();
		String message="Alert we are found that Disk Utilization "+per+"% on "+diskDetials.getAsset().getMake()+"-"+diskDetials.getAsset().getModel()+"-"+diskDetials.getAsset().getSerialNo();
		
		notification.setMesssge(message);
		notification.setNotificationDatatime(new Date());
		notification.setView_bit(0);
		notificationRepo.save(notification);
		Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("CPU Change");
		if(templateOp.isPresent()){
			String tempalateMsg=templateOp.get().getTemplateBody();
			System.out.println("TempalateMsg "+tempalateMsg);
			 // String fname=tempalateMsg.replaceAll("#EMPN","Dattatray");
			  String otpN=tempalateMsg.replaceAll("#ASMK",diskDetials.getAsset().getMake()+"</br>");
			  String val=otpN.replaceAll("#ASML",diskDetials.getAsset().getModel()+"</br>");
			  String val1=val.replaceAll("#SRNO", diskDetials.getAsset().getSerialNo()+"</br></br>   OLD RAM DETAILS");
			
			  
			  String newM=val1.replaceAll("#UTPR", per+"</br>");
			
			String message1=newM;
			String signit=templateOp.get().getSigniture();
			String newSig=signit.replaceAll("#BRKL", "</br>");
			message=message1+"</br> </br> </br>"+signit;
			
			String subject=templateOp.get().getSubject();
			//emailService.sendMail("dattatray.bodhale@a2mee.com", subject, message);
			sendMailToSender(subject, message);
		}
	}
	public void sendMailToSender(String message,String subject) {
		List<EmailReceiver>  recivers=emailService.getActiveAllEmailReceiver();
		for(EmailReceiver emailReceiver: recivers){
			String messageNew=message.replaceAll("#EMPN","Dattatray");
			emailService.sendMail(emailReceiver.getEmailId(), subject, messageNew);

		}
		

	}

	@Override
	public List<Notification> getNotificationByLimit(int page_no, int item_per_page) {
		// TODO Auto-generated method stub
		return notificationRepo.getNotificationByLimit(page_no,item_per_page);
	}

	@Override
	public List<Notification> getNotificationByLimitAndSearch(String searchText, int pageNo, int perPage) {
		// TODO Auto-generated method stub
		return notificationRepo.getNotificationByLimitAndSearch(searchText,pageNo,perPage);
	}

	@Override
	public int getNotificationCount() {
		// TODO Auto-generated method stub
		return notificationRepo.getNotificationCount();
	}

	@Override
	public int getNotificationCountAndSearch(String searchText) {
		// TODO Auto-generated method stub
		return notificationRepo.getNotificationCountAndSearch(searchText);
	}

	@Override
	public int getUnseenNotificationCount() {
		// TODO Auto-generated method stub
		return notificationRepo.getUnseenNotificationCount();
	}

	@Override
	public void saveNotification(Notification notification) {
		// TODO Auto-generated method stub
		notificationRepo.save(notification);
	}
	

}
