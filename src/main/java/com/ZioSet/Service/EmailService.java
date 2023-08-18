package com.ZioSet.Service;

import java.util.List;
import java.util.Optional;

import com.ZioSet.dto.ResponceObject;
import com.ZioSet.model.EmailReceiver;
import com.ZioSet.model.EmailSenderDetials;
import com.ZioSet.model.EmailTemplate;




public interface EmailService {

	List<EmailSenderDetials> getAllEmailSenderDetials();
	void addEmailDetial(EmailSenderDetials emailSenderDetials);
	void deletEmailDetial(EmailSenderDetials emailSenderDetials);
	void addEmailReceiver(EmailReceiver emailReceiver);
	List<EmailReceiver> getAllEmailReceiver();
	void deletEmailReceiver(EmailReceiver emailReceiver);
	ResponceObject sendMail(String recipient, String subject, String message);
	
	int getActiveRciverCount();
	int getAllEmailReceiverCount();
	List<EmailReceiver> getActiveAllEmailReceiver();
	List<EmailReceiver> getPuneAllEmailReceiver();
	List<EmailReceiver> getBengaluruAllEmailReceiver();
	int getPuneAllEmailReceiverCount();
	int getBengaluruAllEmailReceiverCount();
	void addEmailTemplate(EmailTemplate emailTemplate);
	void deletEmailTemplate(EmailTemplate emailTemplate);
	List<EmailTemplate> getAllEmailTemplate();
	ResponceObject testSendMail(String string, String string2, String string3);
	Optional<EmailTemplate> getEmailTemplateFor(String templateFor);
}
