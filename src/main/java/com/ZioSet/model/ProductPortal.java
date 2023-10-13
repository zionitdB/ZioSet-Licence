package com.ZioSet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="product_portal")
public class ProductPortal {
	

	@Override
	public String toString() {
		return "ProductPortal [id=" + id + ", productName=" + productName + ", publisher=" + publisher + "]";
	}

	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="product_name")
	 private String productName;
	
	
	
	@ManyToOne
	@JoinColumn(name="publisher_id")
	private PublisherPortal publisher;
	
	
	

	public PublisherPortal getPublisher() {
		return publisher;
	}

	public void setPublisher(PublisherPortal publisher) {
		this.publisher = publisher;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
	

}
