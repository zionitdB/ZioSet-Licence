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
@Table(name="asset_emp_map")
public class AssetEmployeeAssigned {

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="asset_emp_map_id")
	private int assetEmpMapId;
	
	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;
	@ManyToOne
	@JoinColumn(name="employee_id")
	Employee employee;
	
	@Column(name="mapped_date")
	private Date mappedDate;
	
	@Column(name="mapped_by")
	private String mappedBy;
	
	@Column(name="mapped_status")
	private int mappedStatus;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@Transient
	private String mapDateStr;
	
	@Transient
	private int srNo;
	

	@Transient
	private String empName;
	
	@Transient
	private String fromDate;
	@Transient
	private String toDate;
	
	
	

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public int getAssetEmpMapId() {
		return assetEmpMapId;
	}

	public void setAssetEmpMapId(int assetEmpMapId) {
		this.assetEmpMapId = assetEmpMapId;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getMappedDate() {
		return mappedDate;
	}

	public void setMappedDate(Date mappedDate) {
		this.mappedDate = mappedDate;
	}

	public String getMappedBy() {
		return mappedBy;
	}

	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}

	public int getMappedStatus() {
		return mappedStatus;
	}

	public void setMappedStatus(int mappedStatus) {
		this.mappedStatus = mappedStatus;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getMapDateStr() {
		return mapDateStr;
	}

	public void setMapDateStr(String mapDateStr) {
		this.mapDateStr = mapDateStr;
	}
	
	
	
	
	
}
