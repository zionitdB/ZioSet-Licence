package com.ZioSet.Service;


import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.Repo.NotificationRepo;
import com.ZioSet.model.CPUDetials;
import com.ZioSet.model.EmailTemplate;
import com.ZioSet.model.Notification;
import com.ZioSet.model.OSDetials;

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
		notificationRepo.save(notification);
		Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("CPU Change");
		if(templateOp.isPresent()){
			String tempalateMsg=templateOp.get().getTemplateBody();
			System.out.println("TempalateMsg "+tempalateMsg);
			  String fname=tempalateMsg.replaceAll("#EMPN","Dattatray");
			  String otpN=fname.replaceAll("#ASMK",cpuDetials.getAsset().getMake()+"</br>");
			  String val=otpN.replaceAll("#ASML",cpuDetials.getAsset().getModel()+"</br>");
			  String val1=val.replaceAll("#SRNO", cpuDetials.getAsset().getSerialNo()+"</br>");
			  String val12=val1.replaceAll("#OLCP", cpuDetials.getProcessorName()+"</br>");
			  String val122=val12.replaceAll("#NWCP", cPU.getProcessorName()+"</br>");

			String message1=val122;
			String signit=templateOp.get().getSigniture();
			String newSig=signit.replaceAll("#BRKL", "</br>");
			message=message1+"</br> </br> </br>"+signit;
			
			String subject=templateOp.get().getSubject();
			emailService.sendMail("dattatray.bodhale@a2mee.com", subject, message);

		}
		System.out.println("Raised NOtification For CPU .......................");
		System.out.println("Message    "+message);

	}

	@Override
	public void sendNotificationForOS(String string, OSDetials osDetials, OSDetials osDetials2) {
		// TODO Auto-generated method stub
		
		
		Notification notification= new Notification();
		String message="Alert we are found that "+string+" are change on "+osDetials2.getAsset().getMake()+"-"+osDetials2.getAsset().getModel()+"-"+osDetials2.getAsset().getSerialNo()+" From "+osDetials2.getName()+" to  New "+osDetials2.getName();
		
		notification.setMesssge(message);
		notification.setNotificationDatatime(new Date());
		notification.setView_bit(0);
		notificationRepo.save(notification);
		Optional<EmailTemplate> templateOp=emailService.getEmailTemplateFor("OS Change");
		if(templateOp.isPresent()){
			String tempalateMsg=templateOp.get().getTemplateBody();
			System.out.println("TempalateMsg "+tempalateMsg);
			  String fname=tempalateMsg.replaceAll("#EMPN","Dattatray");
			  String otpN=fname.replaceAll("#ASMK",osDetials2.getAsset().getMake()+"</br>");
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
			emailService.sendMail("dattatray.bodhale@a2mee.com", subject, message);

		}
	}

}
