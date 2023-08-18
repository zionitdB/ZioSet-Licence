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
@Table(name="asset_licence")
public class AssetLicence {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int Id;
	
	
	@ManyToOne
	@JoinColumn(name="asset_id")
	Asset asset;
	
	
	@ManyToOne
	@JoinColumn(name="licence_id")
	Licence licence;
	
	
	@Column(name="licence_key")
	private String licenceKey;
	
	@Column(name="assing_by")
	private String assingBy;
	
	@Column(name="assing_date")
	private Date assingDate;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Licence getLicence() {
		return licence;
	}

	public void setLicence(Licence licence) {
		this.licence = licence;
	}

	public String getLicenceKey() {
		return licenceKey;
	}

	public void setLicenceKey(String licenceKey) {
		this.licenceKey = licenceKey;
	}

	public String getAssingBy() {
		return assingBy;
	}

	public void setAssingBy(String assingBy) {
		this.assingBy = assingBy;
	}

	public Date getAssingDate() {
		return assingDate;
	}

	public void setAssingDate(Date assingDate) {
		this.assingDate = assingDate;
	}

	
	
	

}
