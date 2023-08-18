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
@Table(name = "category_application")
public class CategoryApplications {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_application_id")
	private int categoryApplicationId;
	
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column(name="application_name")
	private String applicationName;

	public int getCategoryApplicationId() {
		return categoryApplicationId;
	}

	public void setCategoryApplicationId(int categoryApplicationId) {
		this.categoryApplicationId = categoryApplicationId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	

	
	

}
