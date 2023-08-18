package com.ZioSet.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private int categorId;
	
	
	@Column(name="category_name")
	private String categoryName;
	
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Transient
	List<CategoryApplications> applications;


	


	public int getCategorId() {
		return categorId;
	}


	public void setCategorId(int categorId) {
		this.categorId = categorId;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public List<CategoryApplications> getApplications() {
		return applications;
	}


	public void setApplications(List<CategoryApplications> applications) {
		this.applications = applications;
	}
	
	
	
	
	

}
