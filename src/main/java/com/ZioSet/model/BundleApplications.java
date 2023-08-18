package com.ZioSet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bundle_application")
public class BundleApplications {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bundle_application_id")
	private int bundleApplicationId;
	
	
	@ManyToOne
	@JoinColumn(name="bundle_id")
	private Bundle bundle;
	
	@Column(name="application_name")
	private String applicationName;
	
	@Column(name="version")
	private String version;
	
	
	@Column(name="exprity_date")
	private Date exprityDate;


	public int getBundleApplicationId() {
		return bundleApplicationId;
	}


	public void setBundleApplicationId(int bundleApplicationId) {
		this.bundleApplicationId = bundleApplicationId;
	}


	public Bundle getBundle() {
		return bundle;
	}


	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}


	public String getApplicationName() {
		return applicationName;
	}


	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Date getExprityDate() {
		return exprityDate;
	}


	public void setExprityDate(Date exprityDate) {
		this.exprityDate = exprityDate;
	}
	
	
	

}
