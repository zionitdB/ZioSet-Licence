package com.ZioSet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="login_otp")
public class LoginOTP {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="login_otp_id")
	private int loginOtpId;
	
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="generated_date")
	private Date generatedDate;
	
	
	@Column(name="otp_code")
	private String otp_code;
	
	@Column(name="system_name")
	private String systemName;

	public int getLoginOtpId() {
		return loginOtpId;
	}

	public void setLoginOtpId(int loginOtpId) {
		this.loginOtpId = loginOtpId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public String getOtp_code() {
		return otp_code;
	}

	public void setOtp_code(String otp_code) {
		this.otp_code = otp_code;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	
	
	
}
