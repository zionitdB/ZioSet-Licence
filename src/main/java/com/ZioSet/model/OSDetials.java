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
@Table(name="os_detials")
public class OSDetials {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="os_detials_id")
	private int osDetialsId;
	
	
	@Column(name="name")
	private String name;
	@Column(name="version")
	private String version;
	
	@Column(name="time_zone")
	private String timeZone;
	
	@Column(name="last_boot_time")
	private String lastBootTime;
	
	@Column(name="os_serial_number")
	 private String osSerialNumber;
	    
	    

		@Column(name="sync_updated_date")
	    private Date syncUpdatedDate;
		
		
		@Column(name="serial_no")
	    private String serialNo;
		
		@ManyToOne
		@JoinColumn(name="asset_id")
		Asset asset;

		public int getOsDetialsId() {
			return osDetialsId;
		}

		public void setOsDetialsId(int osDetialsId) {
			this.osDetialsId = osDetialsId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getTimeZone() {
			return timeZone;
		}

		public void setTimeZone(String timeZone) {
			this.timeZone = timeZone;
		}

		public String getLastBootTime() {
			return lastBootTime;
		}

		public void setLastBootTime(String lastBootTime) {
			this.lastBootTime = lastBootTime;
		}

	

		public String getOsSerialNumber() {
			return osSerialNumber;
		}

		public void setOsSerialNumber(String osSerialNumber) {
			this.osSerialNumber = osSerialNumber;
		}

		public Date getSyncUpdatedDate() {
			return syncUpdatedDate;
		}

		public void setSyncUpdatedDate(Date syncUpdatedDate) {
			this.syncUpdatedDate = syncUpdatedDate;
		}

		public String getSerialNo() {
			return serialNo;
		}

		public void setSerialNo(String serialNo) {
			this.serialNo = serialNo;
		}

		public Asset getAsset() {
			return asset;
		}

		public void setAsset(Asset asset) {
			this.asset = asset;
		}

}
