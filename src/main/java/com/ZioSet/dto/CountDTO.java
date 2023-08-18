package com.ZioSet.dto;

import java.util.List;

public class CountDTO {
	private List<String> names;
	private List<Integer> counts;
	private List<Double> percentage;
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public List<Integer> getCounts() {
		return counts;
	}
	public void setCounts(List<Integer> counts) {
		this.counts = counts;
	}
	public List<Double> getPercentage() {
		return percentage;
	}
	public void setPercentage(List<Double> percentage) {
		this.percentage = percentage;
	}
	
	
	
	

}
