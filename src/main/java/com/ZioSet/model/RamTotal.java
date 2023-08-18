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
@Table(name="ram_totoal")
public class RamTotal {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ram_total_id")
	private int ramTotalId;
	
	@Column(name="size")
	private double size;
	

	

	
	
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


	
	public double getSize() {
		return size;
	}


	public void setSize(double size) {
		this.size = size;
	}




	


	public int getRamTotalId() {
		return ramTotalId;
	}


	public void setRamTotalId(int ramTotalId) {
		this.ramTotalId = ramTotalId;
	}


	public Asset getAsset() {
		return asset;
	}


	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	
	
	
	
	
	

}
