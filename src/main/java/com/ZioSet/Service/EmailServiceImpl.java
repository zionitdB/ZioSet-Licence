package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

//import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ZioSet.model.EmailSenderDetials;
import com.ZioSet.configuration.EmailUtility;
import com.ZioSet.configuration.SendEmailData;
import com.ZioSet.dto.ResponceObject;
import com.ZioSet.model.EmailReceiver;
import com.ZioSet.model.EmailTemplate;
import com.ZioSet.Repo.EmailReceiverRepo;
import com.ZioSet.Repo.EmailSenderDetialsRepo;
import com.ZioSet.Repo.EmailTemplateRepo;


@Service
public class EmailServiceImpl implements EmailService{
   // private final JavaMailSender mailSender;

	
	@Autowired
	EmailSenderDetialsRepo emailSenderDetialsRepo;
	
	@Autowired
	EmailReceiverRepo emailReceiverRepo;
	@Autowired
	EmailTemplateRepo emailTemplateRepo;


	@Override
	public List<EmailSenderDetials> getAllEmailSenderDetials() {
		// TODO Auto-generated method stub
		return emailSenderDetialsRepo.findAll();
	}

	@Override
	public void addEmailDetial(EmailSenderDetials emailSenderDetials) {
		// TODO Auto-generated method stub
		emailSenderDetialsRepo.save(emailSenderDetials);
		
	}

	@Override
	public void deletEmailDetial(EmailSenderDetials emailSenderDetials) {
		// TODO Auto-generated method stub
		emailSenderDetialsRepo.delete(emailSenderDetials);
	}

	@Override
	public void addEmailReceiver(EmailReceiver emailReceiver) {
		// TODO Auto-generated method stub
		emailReceiverRepo.save(emailReceiver);
	}

	@Override
	public List<EmailReceiver> getAllEmailReceiver() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.findAll();
	}

	@Override
	public void deletEmailReceiver(EmailReceiver emailReceiver) {
		// TODO Auto-generated method stub
		emailReceiverRepo.delete(emailReceiver);
	}

	@Override
	public ResponceObject sendMail(String recipient,String subject, String message) {
		// TODO Auto-generated method stub
		try {
			ResponceObject object = new ResponceObject();
			Optional<EmailSenderDetials> senders=emailSenderDetialsRepo.getActiveSender();
			//System.out.println("EMAI");
			if(senders.isPresent()){
				EmailUtility emailUtility = new EmailUtility();
				SendEmailData sendEmailData= new SendEmailData();
				sendEmailData.setDetials(senders.get());
			//	sendEmailData.setMessage(message+senders.get().getSigniture());
				sendEmailData.setMessage(message);
				sendEmailData.setRecipient(recipient);
				sendEmailData.setSubject(subject);
//				try {
//					emailUtility.sendEmail(sendEmailData);
//					
//					sendEmailData.toString();
//				} catch (MessagingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					object.setCode(500);
//					object.setMessage("Email Not Send :");
//				}
			}else{
				
				object.setCode(500);
				object.setMessage("Email Not Send :");
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int getActiveRciverCount() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.getActiveRciverCount();
	}

	@Override
	public int getAllEmailReceiverCount() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.getAllEmailReceiverCount();
	}

	@Override
	public List<EmailReceiver> getActiveAllEmailReceiver() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.getActiveAllEmailReceiver();
	}

	@Override
	public List<EmailReceiver> getPuneAllEmailReceiver() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.getPuneAllEmailReceiver();
	}

	@Override
	public List<EmailReceiver> getBengaluruAllEmailReceiver() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.getBengaluruAllEmailReceiver();
	}

	@Override
	public int getPuneAllEmailReceiverCount() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.getPuneAllEmailReceiverCount();
	}

	@Override
	public int getBengaluruAllEmailReceiverCount() {
		// TODO Auto-generated method stub
		return emailReceiverRepo.getBengaluruAllEmailReceiverCount();
	}

	@Override
	public void addEmailTemplate(EmailTemplate emailTemplate) {
		// TODO Auto-generated method stub
		emailTemplateRepo.save(emailTemplate);
	}

	@Override
	public void deletEmailTemplate(EmailTemplate emailTemplate) {
		// TODO Auto-generated method stub
		emailTemplateRepo.delete(emailTemplate);
	}

	@Override
	public List<EmailTemplate> getAllEmailTemplate() {
		// TODO Auto-generated method stub
		return emailTemplateRepo.findAll();
	}

	@Override
	public ResponceObject testSendMail(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

	@Override
	public Optional<EmailTemplate> getEmailTemplateFor(String templateFor) {
		// TODO Auto-generated method stub
		return emailTemplateRepo.getEmailTemplateFor(templateFor);
	}


}
