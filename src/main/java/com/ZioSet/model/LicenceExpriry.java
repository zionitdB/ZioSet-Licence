package com.ZioSet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
@Entity
@Table(name="licence_expriry")
public class LicenceExpriry {
	
	@Override
	public String toString() {
		return "LicenceExpriry [licenceExpriryid=" + licenceExpriryid + ", publisherName=" + publisherName
				+ ", productName=" + productName + ", releaseName=" + releaseName + ", edition=" + edition
				+ ", version=" + version + ", releaseDate=" + releaseDate + ", retirementDate=" + retirementDate
				+ ", premiumSupportEndDate=" + premiumSupportEndDate + ", extendedSupportEndDate="
				+ extendedSupportEndDate + "]";
	}

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="licence_expriry_id")
	private int licenceExpriryid;
	
	
		
	@Column(name="publisher_name")
	private String publisherName;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="release_name")
	private String releaseName;
	
	@Column(name="edition")
	private String edition;
	
	@Column(name="version")
	private String version;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@Column(name="retirement_date")
	private Date retirementDate;
	
	
	@Column(name="premium_support_end_date")
	private Date premiumSupportEndDate;
	
	@Column(name="extended_support_end_date")
	private Date extendedSupportEndDate;

	public int getLicenceExpriryid() {
		return licenceExpriryid;
	}

	public void setLicenceExpriryid(int licenceExpriryid) {
		this.licenceExpriryid = licenceExpriryid;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(Date retirementDate) {
		this.retirementDate = retirementDate;
	}

	public Date getPremiumSupportEndDate() {
		return premiumSupportEndDate;
	}

	public void setPremiumSupportEndDate(Date premiumSupportEndDate) {
		this.premiumSupportEndDate = premiumSupportEndDate;
	}

	public Date getExtendedSupportEndDate() {
		return extendedSupportEndDate;
	}

	public void setExtendedSupportEndDate(Date extendedSupportEndDate) {
		this.extendedSupportEndDate = extendedSupportEndDate;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	

	



	
	
}
