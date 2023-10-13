package com.ZioSet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

@Entity
@Table(name="customer_supplied_software")
public class CustomerSuppliedSoftware {

	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="form_sr_no")
	private String formSrNo;
	
	@Column(name="asset_tag_no")
	private String assetTagNo;
	
	@Column(name="title")
	private String title;
	
	@Column(name="software_version")
	private String version;
	
	@Column(name="software_language")
	private String language;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="licence_count")
	private int licenceCount;
	
	@Column(name="added_date")
	private Date added_date;
	
	@Transient
	private int srNo;
	
	

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFormSrNo() {
		return formSrNo;
	}

	public void setFormSrNo(String formSrNo) {
		this.formSrNo = formSrNo;
	}

	public String getAssetTagNo() {
		return assetTagNo;
	}

	public void setAssetTagNo(String assetTagNo) {
		this.assetTagNo = assetTagNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getLicenceCount() {
		return licenceCount;
	}

	public void setLicenceCount(int licenceCount) {
		this.licenceCount = licenceCount;
	}

	public Date getAdded_date() {
		return added_date;
	}

	public void setAdded_date(Date added_date) {
		this.added_date = added_date;
	}

	@Override
	public String toString() {
		return "CustomerSuppliedSoftware [id=" + id + ", formSrNo=" + formSrNo + ", assetTagNo=" + assetTagNo
				+ ", title=" + title + ", version=" + version + ", language=" + language + ", remark=" + remark
				+ ", licenceCount=" + licenceCount + ", added_date=" + added_date + "]";
	}
	
	
	
	
	
	
	
}
