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
@Table(name="licence_install_stock")
public class InstallLicenceStock {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="licence_install_stock_id")
	private int licenceInstallStockId;
	
	
	@ManyToOne
	@JoinColumn(name="associate_id")
	Associate associate;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	Product product;
	
	@Column(name="product_version")
	private String productVersion;

	
	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;
	
	
	
	@Column(name="detected_date")
	private Date detectedDate;
	
	

	@Column(name="install_date")
	private Date installDate;

	@Column(name="computer_name")
	private String computerName;
	
	@Column(name="edition")
	private String edition;
	
	

	
	public String getEdition() {
		return edition;
	}



	public void setEdition(String edition) {
		this.edition = edition;
	}



	@Transient
	private String expiryDate;
	

	@Transient
	private String detDate;
	
	
	
	public String getDetDate() {
		return detDate;
	}



	public void setDetDate(String detDate) {
		this.detDate = detDate;
	}



	public String getExpiryDate() {
		return expiryDate;
	}



	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}



	public String getComputerName() {
		return computerName;
	}



	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}



	@Transient
	private String insDate;

	@Transient
	private int srNo;
	
	@Transient
	private Employee employee;
	
	
	
	


	public Employee getEmployee() {
		return employee;
	}



	public void setEmployee(Employee employee) {
		this.employee = employee;
	}



	public int getLicenceInstallStockId() {
		return licenceInstallStockId;
	}



	public void setLicenceInstallStockId(int licenceInstallStockId) {
		this.licenceInstallStockId = licenceInstallStockId;
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



	public String getProductVersion() {
		return productVersion;
	}



	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}



	public Asset getAsset() {
		return asset;
	}



	public void setAsset(Asset asset) {
		this.asset = asset;
	}



	public Date getDetectedDate() {
		return detectedDate;
	}



	public void setDetectedDate(Date detectedDate) {
		this.detectedDate = detectedDate;
	}



	public Date getInstallDate() {
		return installDate;
	}



	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}



	public String getInsDate() {
		return insDate;
	}



	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}



	public int getSrNo() {
		return srNo;
	}



	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
