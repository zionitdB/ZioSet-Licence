package com.ZioSet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="memory_details")
public class MemoryDetails {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="memory_details_id")
	private int memoryDetailsId;
	
	@Column(name="physical_total_installed")
    private String physicalTotalInstalled;
  
	@Column(name="physical_free")
    private String physicalFree;
  
	@Column(name="physical_in_use")
    private String physicalInUse;
   
	@Column(name="virtual_total_installed")
    private String virtualTotalInstalled;
   
	@Column(name="virtual_free")
    private String virtualFree;
  
	@Column(name="virtual_in_use")
    private String virtualInUse;
    
	
	@Column(name="sync_updated_date")
    private Date syncUpdatedDate;
	
	
	@Column(name="serial_no")
    private String serialNo;
	
	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;

	public int getMemoryDetailsId() {
		return memoryDetailsId;
	}

	public void setMemoryDetailsId(int memoryDetailsId) {
		this.memoryDetailsId = memoryDetailsId;
	}

	public String getPhysicalTotalInstalled() {
		return physicalTotalInstalled;
	}

	public void setPhysicalTotalInstalled(String physicalTotalInstalled) {
		this.physicalTotalInstalled = physicalTotalInstalled;
	}

	public String getPhysicalFree() {
		return physicalFree;
	}

	public void setPhysicalFree(String physicalFree) {
		this.physicalFree = physicalFree;
	}

	public String getPhysicalInUse() {
		return physicalInUse;
	}

	public void setPhysicalInUse(String physicalInUse) {
		this.physicalInUse = physicalInUse;
	}

	public String getVirtualTotalInstalled() {
		return virtualTotalInstalled;
	}

	public void setVirtualTotalInstalled(String virtualTotalInstalled) {
		this.virtualTotalInstalled = virtualTotalInstalled;
	}

	public String getVirtualFree() {
		return virtualFree;
	}

	public void setVirtualFree(String virtualFree) {
		this.virtualFree = virtualFree;
	}

	public String getVirtualInUse() {
		return virtualInUse;
	}

	public void setVirtualInUse(String virtualInUse) {
		this.virtualInUse = virtualInUse;
	}

	public Date getSyncUpdatedDate() {
		return syncUpdatedDate;
	}

	public void setSyncUpdatedDate(Date syncUpdatedDate) {
		this.syncUpdatedDate = syncUpdatedDate;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
  

}
