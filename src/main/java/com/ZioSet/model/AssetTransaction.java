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
@Table(name="asset_transaction_tr")
public class AssetTransaction {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="asset_tracking_id")
	private int assetTrackingId;

	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;
	
	
	@ManyToOne
	@JoinColumn(name="employee_id")
	Employee employee;
	
	
	@Column(name="transaction_date")
	private Date transactionDate;
	
	@Column(name="tranaction_type")
	private String tranactionType;

	public int getAssetTrackingId() {
		return assetTrackingId;
	}

	public void setAssetTrackingId(int assetTrackingId) {
		this.assetTrackingId = assetTrackingId;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTranactionType() {
		return tranactionType;
	}

	public void setTranactionType(String tranactionType) {
		this.tranactionType = tranactionType;
	}
	
	@Transient
	private String tDate;
	@Transient
	private int srNo;

	public String gettDate() {
		return tDate;
	}

	public void settDate(String tDate) {
		this.tDate = tDate;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
	
}
