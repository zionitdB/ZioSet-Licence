package com.ZioSet.configuration;

import com.ZioSet.model.EmailSenderDetials;

public class SendEmailData {
	private EmailSenderDetials detials;
	private String recipient;
	private String subject;
	private String message;
	public EmailSenderDetials getDetials() {
		return detials;
	}
	public void setDetials(EmailSenderDetials detials) {
		this.detials = detials;
	}

	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "SendEmailData [detials=" + detials + ", recipient=" + recipient + ", subject=" + subject + ", message="
				+ message + "]";
	}
	
	
	

}
