package com.ZioSet.dto;

public class LicencenPercentageDto {
	private double instalPercentage;
	private double saasPercentage;
	
	
	private double totalCount;
	private double activeCount;
	private String date;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	

}
