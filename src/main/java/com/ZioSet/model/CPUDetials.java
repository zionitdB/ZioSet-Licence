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

@Entity
@Table(name="cpu_detials")
public class CPUDetials {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cpu_detials_id")
	private int cpuDetialsId;
	
	@Column(name="processor_name")
    private String processorName;
	
	@Column(name="processor_speed")
    private String processorSpeed;
    
	@Column(name="number_of_processors")
	private int numberOfProcessors;
	
	@Column(name="number_of_cores")
    private int numberOfCores;
	
	
	@Column(name="sync_updated_date")
    private Date syncUpdatedDate;
	
	
	@Column(name="serial_no")
    private String serialNo;
	
	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;
	
	
	public String getProcessorName() {
		return processorName;
	}
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	public String getProcessorSpeed() {
		return processorSpeed;
	}
	public void setProcessorSpeed(String processorSpeed) {
		this.processorSpeed = processorSpeed;
	}
	public int getNumberOfProcessors() {
		return numberOfProcessors;
	}
	public void setNumberOfProcessors(int numberOfProcessors) {
		this.numberOfProcessors = numberOfProcessors;
	}
	public int getNumberOfCores() {
		return numberOfCores;
	}
	public void setNumberOfCores(int numberOfCores) {
		this.numberOfCores = numberOfCores;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public int getCpuDetialsId() {
		return cpuDetialsId;
	}
	public void setCpuDetialsId(int cpuDetialsId) {
		this.cpuDetialsId = cpuDetialsId;
	}
	public Asset getAsset() {
		return asset;
	}
	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	public Date getSyncUpdatedDate() {
		return syncUpdatedDate;
	}
	public void setSyncUpdatedDate(Date syncUpdatedDate) {
		this.syncUpdatedDate = syncUpdatedDate;
	}
    
    
    
    
}
