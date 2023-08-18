package com.ZioSet.dto;

import java.util.List;

public class ComparisonGraphDTO {
	private List<String> names;
	private List<Integer> installed;
	private List<Integer> saas;
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public List<Integer> getInstalled() {
		return installed;
	}
	public void setInstalled(List<Integer> installed) {
		this.installed = installed;
	}
	public List<Integer> getSaas() {
		return saas;
	}
	public void setSaas(List<Integer> saas) {
		this.saas = saas;
	}
	
	
	
	

}
