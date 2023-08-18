package com.ZioSet.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "bundle")
public class Bundle {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bundle_id")
	private int bundleId;
	
	
	@Column(name="bundle_name")
	private String bundleName;
	
	
	@Column(name="created_date")
	private Date createdDate;

	@Transient
	private List<BundleApplications> applications;
	
	
	public int getBundleId() {
		return bundleId;
	}


	public void setBundleId(int bundleId) {
		this.bundleId = bundleId;
	}


	public String getBundleName() {
		return bundleName;
	}


	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public List<BundleApplications> getApplications() {
		return applications;
	}


	public void setApplications(List<BundleApplications> applications) {
		this.applications = applications;
	}
	
	
}
