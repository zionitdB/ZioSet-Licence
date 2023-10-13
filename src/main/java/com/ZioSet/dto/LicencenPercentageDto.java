package com.ZioSet.dto;

import java.util.Date;

public class LicencenPercentageDto {
	private double instalPercentage;
	private double saasPercentage;
	
	
	private double totalCount;
	private double activeCount;
	private Date date;
	public double getInstalPercentage() {
		return instalPercentage;
	}
	public void setInstalPercentage(double instalPercentage) {
		this.instalPercentage = instalPercentage;
	}
	public double getSaasPercentage() {
		return saasPercentage;
	}
	public void setSaasPercentage(double saasPercentage) {
		this.saasPercentage = saasPercentage;
	}
	public double getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(double totalCount) {
		this.totalCount = totalCount;
	}
	public double getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(double activeCount) {
		this.activeCount = activeCount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	

}
