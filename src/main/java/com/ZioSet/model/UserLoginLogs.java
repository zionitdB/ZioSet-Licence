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
@Table(name="user_login_log")
public class UserLoginLogs {

	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_login_log_id")
	private int userLoginLogId;
	
	@Column(name="user_name")
	private String userName;
	
	@Column(name="login_system_ip")
	private String loginSystemIp;
	
	@Column(name="last_login")
	private Date lastLogin;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserInfo user;

	public int getUserLoginLogId() {
		return userLoginLogId;
	}

	public void setUserLoginLogId(int userLoginLogId) {
		this.userLoginLogId = userLoginLogId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginSystemIp() {
		return loginSystemIp;
	}

	public void setLoginSystemIp(String loginSystemIp) {
		this.loginSystemIp = loginSystemIp;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	
	
	
	
}
