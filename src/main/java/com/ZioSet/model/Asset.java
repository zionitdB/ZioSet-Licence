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
@Table(name="asset_mst")
public class Asset {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int Id;
	
	@Column(name="make")
	private String make;
	
	@Column(name="model")
	private String model;
	
	@Column(name="serial_no")
	private String serialNo;
	
	@Column(name="asset_id")
	private String assetId;
	
	
	@Column(name="project_id")
	private String projectId;
	
	@Column(name="project_name")
	private String projectName;
	
	
	@Column(name="employee_no")
	private String employeeNo;
	
	
	
	@Column(name="employee_name")
	private String employeeName;
	
	
	@Column(name="email_id")
	private String emailId;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name="asset_type")
	private String assetType;
		
	
	
	@Column(name="system_ip")
	private String systemIp;
	
	@Column(name="purchase_order_no")
	private String purchaseOrderNo;
	
	@Column(name="invoice_no")
	private String invoiceNo;
	
	
	@Column(name="device_grouping")
	private String deviceGrouping;
	
	
	@Column(name="desk_location")
	private String deskLocation;
	
	
	@Column(name="store_location")
	private String storeLocation;
	
	@Column(name="kittng_area")
	private String kittng_area;
	
	
	public String getKittng_area() {
		return kittng_area;
	}

	public void setKittng_area(String kittng_area) {
		this.kittng_area = kittng_area;
	}

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public String getDeviceGrouping() {
		return deviceGrouping;
	}

	public void setDeviceGrouping(String deviceGrouping) {
		this.deviceGrouping = deviceGrouping;
	}

	public String getDeskLocation() {
		return deskLocation;
	}

	public void setDeskLocation(String deskLocation) {
		this.deskLocation = deskLocation;
	}

	@Column(name="invoice_date")
	private Date invoiceDate;
	
	@Column(name="tag_alllocation_status")
	private int tagAlllocationStatus;
	
	
	@Column(name="age")
	private String age;
	
	@Transient
	private int srNo;
	@Transient
	private String locationName;
	@Transient
	private String deviceName;
	@Transient
	private String detectedDate;
	@Transient
	private String allocatedToNo;
	@Transient
	private String allocatedToName;
	
	@Transient
	private String workerStatus;
	
	@Transient
	private String workerStatusDate;
	
	public String getWorkerStatus() {
		return workerStatus;
	}

	public void setWorkerStatus(String workerStatus) {
		this.workerStatus = workerStatus;
	}

	@Transient
	private String detectedTime;
	
	public String getDetectedDate() {
		return detectedDate;
	}

	public void setDetectedDate(String detectedDate) {
		this.detectedDate = detectedDate;
	}

	public String getDetectedTime() {
		return detectedTime;
	}

	public void setDetectedTime(String detectedTime) {
		this.detectedTime = detectedTime;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name="status")
	private String status;
	
	
	@Transient
	private int detectCount;
	
	
	
	public int getDetectCount() {
		return detectCount;
	}

	public void setDetectCount(int detectCount) {
		this.detectCount = detectCount;
	}

	@ManyToOne
	@JoinColumn(name="branch_id")
	Branch branch;
	
	@Column(name="active")
	private int active;
	
	@Column(name="available_status")
	private int availableStatus;
	
	
	@Transient
	private int assetLife;
	
	@Transient
	private String eol;
	

	@Transient
	private String incDate;
	@Transient
	private String epc;
	
	public String getEpc() {
		return epc;
	}

	public void setEpc(String epc) {
		this.epc = epc;
	}

	@Transient
	private String assignedStatus;
	
	public String getIncDate() {
		return incDate;
	}

	public void setIncDate(String incDate) {
		this.incDate = incDate;
	}

	public String getAssignedStatus() {
		return assignedStatus;
	}

	public void setAssignedStatus(String assignedStatus) {
		this.assignedStatus = assignedStatus;
	}

	public int getAssetLife() {
		return assetLife;
	}

	public void setAssetLife(int assetLife) {
		this.assetLife = assetLife;
	}

	public String getEol() {
		return eol;
	}

	public void setEol(String eol) {
		this.eol = eol;
	}

	public int getCurrentAge() {
		return currentAge;
	}

	public void setCurrentAge(int currentAge) {
		this.currentAge = currentAge;
	}

	@Transient
	private int currentAge;
	
	@Transient
	private Employee employee;
	
	public int getAvailableStatus() {
		return availableStatus;
	}

	public void setAvailableStatus(int availableStatus) {
		this.availableStatus = availableStatus;
	}

	@Column(name="added_date")
	private Date addedDate;
		
	@Column(name="added_by")
	private String added_by;
	
	
	@Column(name="tag_code")
	private String tagCode;
	

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}

	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}

	

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public String getAdded_by() {
		return added_by;
	}

	public void setAdded_by(String added_by) {
		this.added_by = added_by;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	public int getTagAlllocationStatus() {
		return tagAlllocationStatus;
	}

	public void setTagAlllocationStatus(int tagAlllocationStatus) {
		this.tagAlllocationStatus = tagAlllocationStatus;
	}

	public String getAllocatedToNo() {
		return allocatedToNo;
	}

	public void setAllocatedToNo(String allocatedToNo) {
		this.allocatedToNo = allocatedToNo;
	}

	public String getAllocatedToName() {
		return allocatedToName;
	}

	public void setAllocatedToName(String allocatedToName) {
		this.allocatedToName = allocatedToName;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getWorkerStatusDate() {
		return workerStatusDate;
	}

	public void setWorkerStatusDate(String workerStatusDate) {
		this.workerStatusDate = workerStatusDate;
	}

	public String getSystemIp() {
		return systemIp;
	}

	public void setSystemIp(String systemIp) {
		this.systemIp = systemIp;
	}
	
	
	
	
	
	
}
