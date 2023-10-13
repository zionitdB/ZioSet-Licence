package com.ZioSet.dto;

public class LicenceCountDto {
private String licenceType;
private String associateName;
private String productName;
private String categoryName;
private String bundleName;

private String applicationName;

private int count;
private boolean check;
public String getLicenceType() {
	return licenceType;
}
public void setLicenceType(String licenceType) {
	this.licenceType = licenceType;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public String getAssociateName() {
	return associateName;
}
public void setAssociateName(String associateName) {
	this.associateName = associateName;
}
public boolean isCheck() {
	return check;
}
public void setCheck(boolean check) {
	this.check = check;
}
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getCategoryName() {
	return categoryName;
}
public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}
public String getBundleName() {
	return bundleName;
}
public void setBundleName(String bundleName) {
	this.bundleName = bundleName;
}
public String getApplicationName() {
	return applicationName;
}
public void setApplicationName(String applicationName) {
	this.applicationName = applicationName;
}




}
