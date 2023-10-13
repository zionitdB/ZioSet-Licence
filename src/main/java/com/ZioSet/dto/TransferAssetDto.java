package com.ZioSet.dto;

import com.ZioSet.model.AssetEmployeeAssigned;
import com.ZioSet.model.Employee;

public class TransferAssetDto {
	private Employee newemployee;
	private AssetEmployeeAssigned assetEmployeeAssigned;

	public Employee getNewemployee() {
		return newemployee;
	}
	public void setNewemployee(Employee newemployee) {
		this.newemployee = newemployee;
	}
	public AssetEmployeeAssigned getAssetEmployeeAssigned() {
		return assetEmployeeAssigned;
	}
	public void setAssetEmployeeAssigned(AssetEmployeeAssigned assetEmployeeAssigned) {
		this.assetEmployeeAssigned = assetEmployeeAssigned;
	}

	
	
}
