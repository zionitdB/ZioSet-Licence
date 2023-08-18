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

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="disk_detials")
public class DiskDetials {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="disk_detials_id")
	private int diskDetialsId;
	
	@Column(name="name")
	 private String name;
	@Column(name="volume_name")
	private String volumeName;
	
	@Column(name="serial")
    private String serial;
	
	@Column(name="description")
    private String description;
	
	@Column(name="file_system")
    private String fileSystem;
	
	

	@Column(name="free_space")
    private String freeSpace;
   
	@Column(name="size")
    private String size;
    
    
	
	@Column(name="sync_updated_date")
    private Date syncUpdatedDate;
	
	
	@Column(name="serial_no")
    private String serialNo;
	
	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;

	public int getDiskDetialsId() {
		return diskDetialsId;
	}

	public void setDiskDetialsId(int diskDetialsId) {
		this.diskDetialsId = diskDetialsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public void setVolumeName(String volumeName) {
		this.volumeName = volumeName;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(String fileSystem) {
		this.fileSystem = fileSystem;
	}

	public String getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(String freeSpace) {
		this.freeSpace = freeSpace;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
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
