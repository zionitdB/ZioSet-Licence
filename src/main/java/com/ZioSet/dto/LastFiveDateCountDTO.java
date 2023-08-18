package com.ZioSet.dto;

import java.util.List;

public class LastFiveDateCountDTO {
	private List<String> dates;
	private List<Integer> counts;
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	public List<Integer> getCounts() {
		return counts;
	}
	public void setCounts(List<Integer> counts) {
		this.counts = counts;
	}
	
	
	

}
