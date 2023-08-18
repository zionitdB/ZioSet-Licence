/**
 * @ Dattatray Bodhale
 * Jan 30, 2020
 */
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
@Table(name="employee_mst")
public class Employee {
	


	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employee_id")
	private int employeeId;

	@Column(name="employee_name")
	private String employeeName;
	
	@Column(name="employee_no")
	private String employeeNo;
	
	@Column(name="email")
	private String email;
	
	
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Column(name="uhf_code")
	private String uhfCode;
	@ManyToOne
	@JoinColumn(name="branch_id")
	private Branch branch;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="employee_type")
	private String employeeType;
	
	@Column(name="bussiness_unit")
	private String bussinessUnit;
	
	
	@Column(name="cost_center")
	private String costCenter;
	
	
	
	@Column(name="team")
	private String team;	
	
	@Column(name="work_location")
	private String workLocation;
	
	
	@Column(name="manager")
	private String manager;
	
	@Column(name="hire_date")
	private Date hireDate;
	
	
	@Column(name="username")
	private String username;
	
	
	@Column(name="active")
	private int active;
	
	/*@Column(name="added_date")
	private Date addedDate;*/
	
/*	@Column(name="upd_datetime")
	private Date updDatetime;
	*/
	
	@ManyToOne
	@JoinColumn(name="added_by", referencedColumnName="user_id")
	private UserInfo addedBy;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	
	@Transient
	private int srNo;
	@Transient
	private String addBy;
	@Transient
	private String  addDate;
	
	
	@Transient
	private String  status;
	
	
	@Transient
	private String hrDate;
	


	public int getSrNo() {
		return srNo;
	}


	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}


	public String getAddBy() {
		return addBy;
	}


	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}


	public String getAddDate() {
		return addDate;
	}


	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}


	public String getHrDate() {
		return hrDate;
	}


	public void setHrDate(String hrDate) {
		this.hrDate = hrDate;
	}


	/**
	 * @return the employeeId
	 */
	public int getEmployeeId() {
		return employeeId;
	}


	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}






	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the employeeName
	 */
	public String getEmployeeName() {
		return employeeName;
	}


	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	/**
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}


	/**
	 * @param employeeNo the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}


	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	/**
	 * @return the uhfCode
	 */
	public String getUhfCode() {
		return uhfCode;
	}


	/**
	 * @param uhfCode the uhfCode to set
	 */
	public void setUhfCode(String uhfCode) {
		this.uhfCode = uhfCode;
	}


	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}


	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}


	public Branch getBranch() {
		return branch;
	}


	public void setBranch(Branch branch) {
		this.branch = branch;
	}





	/**
	 * @return the updDatetime
	 *//*
	public Date getUpdDatetime() {
		return updDatetime;
	}


	*//**
	 * @param updDatetime the updDatetime to set
	 *//*
	public void setUpdDatetime(Date updDatetime) {
		this.updDatetime = updDatetime;
	}*/


	/**
	 * @return the addedBy
	 */
	public UserInfo getAddedBy() {
		return addedBy;
	}


	/**
	 * @param addedBy the addedBy to set
	 */
	public void setAddedBy(UserInfo addedBy) {
		this.addedBy = addedBy;
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmployeeType() {
		return employeeType;
	}


	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}


	public String getBussinessUnit() {
		return bussinessUnit;
	}


	public void setBussinessUnit(String bussinessUnit) {
		this.bussinessUnit = bussinessUnit;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public String getWorkLocation() {
		return workLocation;
	}


	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}


	public String getManager() {
		return manager;
	}


	public void setManager(String manager) {
		this.manager = manager;
	}


	public Date getHireDate() {
		return hireDate;
	}


	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getCostCenter() {
		return costCenter;
	}


	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}


	
	
	
	
}
