package com.ZioSet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email_template")
public class EmailTemplate {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="template_id")
	private int templateId;
	
	@Column(name="template_for")
	private String templateFor;
	
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="signiture")
	private String signiture;
	
	
	@Column(name="template_body")
	private String templateBody;
	
	@Column(name="added_by")
	private String addedBy;
	
	@Column(name="added_date")
	private Date addedDate;
	
	@Column(name="active")
	private int active;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getTemplateFor() {
		return templateFor;
	}

	public void setTemplateFor(String templateFor) {
		this.templateFor = templateFor;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSigniture() {
		return signiture;
	}

	public void setSigniture(String signiture) {
		this.signiture = signiture;
	}

	public String getTemplateBody() {
		return templateBody;
	}

	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	
	
	
}
