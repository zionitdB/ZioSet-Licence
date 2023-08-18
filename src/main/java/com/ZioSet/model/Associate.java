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
@Table(name="associate_mst")
public class Associate {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	
	@Column(name="associate_name")
	private String associateName;
	

	@Column(name="associate_id")
	private String associateId;
	
	
	
	@Column(name="associate_contact")
	private String associateContact;
	
	@Column(name="associate_mail")
	private String associateMail;
	
	@Column(name="address")
	private String address;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getAssociateContact() {
		return associateContact;
	}

	public void setAssociateContact(String associateContact) {
		this.associateContact = associateContact;
	}

	public String getAssociateMail() {
		return associateMail;
	}

	public void setAssociateMail(String associateMail) {
		this.associateMail = associateMail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	
		
}
