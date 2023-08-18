/**
 * @ Dattatray Bodhale
 * Jan 27, 2020
 */
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
import javax.persistence.Transient;
@Entity
@Table(name="licence_mst")
public class Licence {
	@Override
	public String toString() {
		return "Licence [id=" + id + ", branch=" + branch + ", associate=" + associate + ", product=" + product
				+ ", licenceType=" + licenceType + ", projectName=" + projectName + ", licenceVersion=" + licenceVersion
				+ ", licencePeriod=" + licencePeriod + ", licencePeriodUnit=" + licencePeriodUnit + ", licenceStatus="
				+ licenceStatus + ", cost=" + cost + ", purchaseDate=" + purchaseDate + ", installlationDate="
				+ installlationDate + ", licenceExpiryDate=" + licenceExpiryDate + ", licenceKey=" + licenceKey
				+ ", srNo=" + srNo + ", purDate=" + purDate + ", installDate=" + installDate + "]";
	}
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="branch_id")
	Branch branch;
	
	@ManyToOne
	@JoinColumn(name="associate_id")
	Associate associate;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	Product product;
		
	@Column(name="licence_type")
	private String licenceType;
	
	@Column(name="project_name")
	private String projectName;
	
	@Column(name="licence_version")
	private String licenceVersion;
	
	@Column(name="licence_period")
	private int licencePeriod;
	
	@Column(name="licence_period_unit")
	private String licencePeriodUnit;
	
	
	@Column(name="licence_status")
	private String licenceStatus;
	
	@Column(name="cost")
	private String cost;
	
	
	@Column(name="purchase_date")
	private Date purchaseDate;
	
	
	@Column(name="installlation_date")
	private Date installlationDate;
	
	
	@Column(name="licence_expiry_date")
	private Date licenceExpiryDate;

	
	@Column(name="licence_key")
	private String licenceKey;

	@Transient
	private int srNo;
	@Transient
	private String purDate;
	@Transient
	private String installDate;
	@Transient
	private String period;
	
	
	
	
	@Transient
	private String expDate;
	
	
	
	
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public Associate getAssociate() {
		return associate;
	}
	public void setAssociate(Associate associate) {
		this.associate = associate;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getLicenceType() {
		return licenceType;
	}
	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getLicenceVersion() {
		return licenceVersion;
	}
	public void setLicenceVersion(String licenceVersion) {
		this.licenceVersion = licenceVersion;
	}
	public int getLicencePeriod() {
		return licencePeriod;
	}
	public void setLicencePeriod(int licencePeriod) {
		this.licencePeriod = licencePeriod;
	}
	public String getLicencePeriodUnit() {
		return licencePeriodUnit;
	}
	public void setLicencePeriodUnit(String licencePeriodUnit) {
		this.licencePeriodUnit = licencePeriodUnit;
	}
	public String getLicenceStatus() {
		return licenceStatus;
	}
	public void setLicenceStatus(String licenceStatus) {
		this.licenceStatus = licenceStatus;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getInstalllationDate() {
		return installlationDate;
	}
	public void setInstalllationDate(Date installlationDate) {
		this.installlationDate = installlationDate;
	}
	public Date getLicenceExpiryDate() {
		return licenceExpiryDate;
	}
	public void setLicenceExpiryDate(Date licenceExpiryDate) {
		this.licenceExpiryDate = licenceExpiryDate;
	}
	public String getLicenceKey() {
		return licenceKey;
	}
	public void setLicenceKey(String licenceKey) {
		this.licenceKey = licenceKey;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getPurDate() {
		return purDate;
	}
	public void setPurDate(String purDate) {
		this.purDate = purDate;
	}
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	


	



	
	
}
