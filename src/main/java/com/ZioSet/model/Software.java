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
@Table(name="licence_install")
public class Software {
	
	public String getSystemIp() {
		return systemIp;
	}

	public void setSystemIp(String systemIp) {
		this.systemIp = systemIp;
	}

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;
	
	@Column(name="application_name")
	private String applicationName;
	
	@Column(name="computer_name")
	private String computeName;
	
	@Column(name="system_ip")
	private String systemIp;

	@Column(name="serial_no")
	private String serialNo;
	
	@Column(name="edition")
	private String edition;
	
	
	@Column(name="detected_date")
	private Date detectedDate;
	
	
	
	@Transient
	private Employee employee;
	public Date getDetectedDate() {
		return detectedDate;
	}

	public void setDetectedDate(Date detectedDate) {
		this.detectedDate = detectedDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name="application_version")
	private String version;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="install_date")
	private String installDate;
	
	
	
	
	
	@Column(name="detected_datetime")
	private Date detectedDatetime;
	
	@Transient
	private String dDate;
	@Transient
	private int srNo;
	
	
	@Transient
	private String employeeName ;
	@Transient
	private String employeeCode;
	@Transient
	private String ntId ;



	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getdDate() {
		return dDate;
	}

	public void setdDate(String dDate) {
		this.dDate = dDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public Date getDetectedDatetime() {
		return detectedDatetime;
	}

	public void setDetectedDatetime(Date detectedDatetime) {
		this.detectedDatetime = detectedDatetime;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getNtId() {
		return ntId;
	}

	public void setNtId(String ntId) {
		this.ntId = ntId;
	}

	public String getComputeName() {
		return computeName;
	}

	public void setComputeName(String computeName) {
		this.computeName = computeName;
	}


	@Override
	public String toString() {
		return "Software [id=" + id + ", asset=" + asset + ", applicationName=" + applicationName + ", computeName="
				+ computeName + ", serialNo=" + serialNo + ", edition=" + edition + ", detectedDate=" + detectedDate
				+ ", version=" + version + ", publisher=" + publisher + ", installDate=" + installDate
				+ ", detectedDatetime=" + detectedDatetime + ", dDate=" + dDate + ", srNo=" + srNo + ", employeeName="
				+ employeeName + ", employeeCode=" + employeeCode + ", ntId=" + ntId + "]";
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
	
	
	
	
	
	

}
