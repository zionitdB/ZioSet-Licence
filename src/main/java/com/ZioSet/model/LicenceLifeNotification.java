package com.ZioSet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="licence_life_notification")
public class LicenceLifeNotification {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	
	@Column(name="licence_type")
	private String type;
	

	@Column(name="notification_before_days")
	private int notificationBeforeDays;
	
	
	@Column(name="active")
	private int active;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getNotificationBeforeDays() {
		return notificationBeforeDays;
	}


	public void setNotificationBeforeDays(int notificationBeforeDays) {
		this.notificationBeforeDays = notificationBeforeDays;
	}


	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}
	

}
