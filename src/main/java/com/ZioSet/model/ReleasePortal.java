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
@Table(name="release_portal")
public class ReleasePortal {
	@Override
	public String toString() {
		return "ReleasePortal [id=" + id + ", publisher=" + publisher + ", product=" + product + ", edition=" + edition
				+ ", releaseDate=" + releaseDate + ", retirementDate=" + retirementDate + ", premiumSupportEndDate="
				+ premiumSupportEndDate + ", extendedSupportEndDate=" + extendedSupportEndDate + "]";
	}



	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@ManyToOne
	@JoinColumn(name="publisher_id")
	private PublisherPortal publisher;
	
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private ProductPortal product;
	
	
	
	
	@Column(name="edition")
	private String edition;
	
	
	@Column(name="release_date")
	private Date releaseDate;
	
	
	@Column(name="retirement_date")
	private Date retirementDate;
	
	
	@Column(name="premium_support_end_date")
	private Date premiumSupportEndDate;
	
	
	
	@Column(name="extended_support_end_date")
	private Date extendedSupportEndDate;



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public PublisherPortal getPublisher() {
		return publisher;
	}



	public void setPublisher(PublisherPortal publisher) {
		this.publisher = publisher;
	}



	public ProductPortal getProduct() {
		return product;
	}



	public void setProduct(ProductPortal product) {
		this.product = product;
	}



	public String getEdition() {
		return edition;
	}



	public void setEdition(String edition) {
		this.edition = edition;
	}



	public Date getReleaseDate() {
		return releaseDate;
	}



	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}



	public Date getRetirementDate() {
		return retirementDate;
	}



	public void setRetirementDate(Date retirementDate) {
		this.retirementDate = retirementDate;
	}



	public Date getPremiumSupportEndDate() {
		return premiumSupportEndDate;
	}



	public void setPremiumSupportEndDate(Date premiumSupportEndDate) {
		this.premiumSupportEndDate = premiumSupportEndDate;
	}



	public Date getExtendedSupportEndDate() {
		return extendedSupportEndDate;
	}



	public void setExtendedSupportEndDate(Date extendedSupportEndDate) {
		this.extendedSupportEndDate = extendedSupportEndDate;
	}
	
	
	
	
	
	
	
}
