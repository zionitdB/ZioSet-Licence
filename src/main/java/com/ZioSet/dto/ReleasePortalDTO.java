package com.ZioSet.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ZioSet.model.ProductPortal;
import com.ZioSet.model.PublisherPortal;

public class ReleasePortalDTO {

	
	
	


	
	
	
	private ProductDTO product;
	
	
	
	
	
	private String edition;
	
	
	
	private Date releaseDate;
	
	
	
	private Date retirementDate;
	
	
	
	private Date premiumSupportEndDate;
	
	
	
	private Date extendedSupportEndDate;







	public String getEdition() {
		return edition;
	}



	public ProductDTO getProduct() {
		return product;
	}



	public void setProduct(ProductDTO product) {
		this.product = product;
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
