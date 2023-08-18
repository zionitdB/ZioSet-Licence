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
@Table(name="ram_detials")
public class RamDetials {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ram_detial_id")
	private int ram_detialId;
	
	@Column(name="size")
	private double size;
	
	
	
	@Transient
	private double totalSize;
	
	@Column(name="system_serial_no")
	private String systemSerialNo;
	
	
	@Column(name="manufacture")
	private String manufacture;
	
	
	@Column(name="serial_no")
	private String serialNo;
	
	@Column(name="part_no")
	private String partNo;
	
	
	@Column(name="added_date")
	private Date addedDate;
	
	
	public Date getAddedDate() {
		return addedDate;
	}


	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}


	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;


	public int getRam_detialId() {
		return ram_detialId;
	}


	public void setRam_detialId(int ram_detialId) {
		this.ram_detialId = ram_detialId;
	}


	public double getSize() {
		return size;
	}


	public void setSize(double size) {
		this.size = size;
	}


	public String getSystemSerialNo() {
		return systemSerialNo;
	}


	public void setSystemSerialNo(String systemSerialNo) {
		this.systemSerialNo = systemSerialNo;
	}


	public String getManufacture() {
		return manufacture;
	}


	public void setManufacture(String manufacture) {
		this.manufacture = manufacture;
	}


	public String getSerialNo() {
		return serialNo;
	}


	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}


	public String getPartNo() {
		return partNo;
	}


	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}



	public Asset getAsset() {
		return asset;
	}


	public void setAsset(Asset asset) {
		this.asset = asset;
	}


	public double getTotalSize() {
		return totalSize;
	}


	public void setTotalSize(double totalSize) {
		this.totalSize = totalSize;
	}
	
	
	
	
	
	
	

}
