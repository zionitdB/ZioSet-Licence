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
@Table(name="expiry_update")
public class ExpiryUpdate {

	

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="expiry_update_id")
	private int expiryUpdateId;

	@Column(name="cost")
	private String cost;
	
	@Column(name="existing_expiry_date")
	private Date  existingExpiryDate;
	
	@Column(name="new_expiry_date")
	private Date newExpiryDate;
	
	
	@Transient
	private Date newExpDate;
	
	@ManyToOne
	@JoinColumn(name="licence_id")
	private Licence licence;


	public int getExpiryUpdateId() {
		return expiryUpdateId;
	}


	public void setExpiryUpdateId(int expiryUpdateId) {
		this.expiryUpdateId = expiryUpdateId;
	}


	public String getCost() {
		return cost;
	}


	public void setCost(String cost) {
		this.cost = cost;
	}


	public Date getExistingExpiryDate() {
		return existingExpiryDate;
	}


	public void setExistingExpiryDate(Date existingExpiryDate) {
		this.existingExpiryDate = existingExpiryDate;
	}


	public Date getNewExpiryDate() {
		return newExpiryDate;
	}


	public void setNewExpiryDate(Date newExpiryDate) {
		this.newExpiryDate = newExpiryDate;
	}


	public Licence getLicence() {
		return licence;
	}


	public void setLicence(Licence licence) {
		this.licence = licence;
	}


	public Date getNewExpDate() {
		return newExpDate;
	}


	public void setNewExpDate(Date newExpDate) {
		this.newExpDate = newExpDate;
	}
	
	
	
	
	
	
	
	
}
