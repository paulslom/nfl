package com.pas.valueObject;

import java.io.Serializable;

/**
 * Title: Address
 * Project: Claims Replacement System
 * 
 * Description: 
 * Copyright: Copyright (c) 2003
 * Company: Lincoln Life
 * 
 * @author CGI
 * @version 
 */
 
public class Address implements Serializable, IValueObject
{
	private int addressID = 0;
	private int addressSeqNum = 0;
	private String addressFormat = null;
	private String addressLine1 = null;
	private String addressLine2 = null;
	private String addressLine3 = null;
	private String addressLine4 = null;
	private String city = null;
	private String state = null;
	private String province = null;
	private String zip = null;
	private String zipExtension = null;
	private String postal = null;
	private String country = null;
	private String homePhone = null;
	private String workPhone = null;
	private String extension = null;
	private String fax = null;
	private String cellPhone = null;
	private String email = null;
	private String addressTypeCode = null;
	private String prefAddressInd = null;
	
	/**
	 * Get addressID
	 * @return int
	 */
	public int getAddressID() {
		return addressID;
	}

	/**
	 * Set addressID
	 * @param <code>int</code>
	 */
	public void setAddressID(int p) {
		this.addressID = p;
	}


	/**
	 * Get addressSeqNum
	 * @return int
	 */
	public int getAddressSeqNum() {
		return addressSeqNum;
	}

	/**
	 * Set addressSeqNum
	 * @param <code>int</code>
	 */
	public void setAddressSeqNum(int p) {
		this.addressSeqNum = p;
	}

	/**
	 * Get addressFormat
	 * @return String
	 */
	public String getAddressFormat() {
		return addressFormat;
	}

	/**
	 * Set addressFormat
	 * @param <code>String</code>
	 */
	public void setAddressFormat(String p) {
		this.addressFormat = p;
	}

	/**
	 * Get addressLine1
	 * @return String
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * Set addressLine1
	 * @param <code>String</code>
	 */
	public void setAddressLine1(String p) {
		this.addressLine1 = p;
	}

	/**
	 * Get addressLine2
	 * @return String
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * Set addressLine2
	 * @param <code>String</code>
	 */
	public void setAddressLine2(String p) {
		this.addressLine2 = p;
	}

	/**
	 * Get addressLine3
	 * @return String
	 */
	public String getAddressLine3() {
		return addressLine3;
	}

	/**
	 * Set addressLine3
	 * @param <code>String</code>
	 */
	public void setAddressLine3(String p) {
		this.addressLine3 = p;
	}

	/**
	 * Get addressLine4
	 * @return String
	 */
	public String getAddressLine4() {
		return addressLine4;
	}

	/**
	 * Set addressLine4
	 * @param <code>String</code>
	 */
	public void setAddressLine4(String p) {
		this.addressLine4 = p;
	}


	/**
	 * Get city
	 * @return String
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set 
	 * @param <code>String</code>
	 */
	public void setCity(String p) {
		this.city = p;
	}

	/**
	 * Get state
	 * @return String
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set state
	 * @param <code>String</code>
	 */
	public void setState(String p) {
		this.state = p;
	}

	/**
	 * Get province
	 * @return String
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Set province
	 * @param <code>String</code>
	 */
	public void setProvince(String p) {
		this.province = p;
	}


	/**
	 * Set zip
	 * @param <code>String</code>
	 */
	public void setZip(String p) {
		this.zip = p;
	}

	/**
	 * Get zip
	 * @return String
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * Set zipExtension
	 * @param <code>String</code>
	 */
	public void setZipExtension(String p) {
		this.zipExtension = p;
	}

	/**
	 * Get zipExtension
	 * @return String
	 */
	public String getZipExtension() {
		return zipExtension;
	}

	/**
	 * Set postal
	 * @param <code>String</code>
	 */
	public void setPostal(String p) {
		this.postal = p;
	}

	/**
	 * Get 
	 * @return String
	 */
	public String getPostal() {
		return postal;
	}

	/**
	 * Set country
	 * @param <code>String</code>
	 */
	public void setCountry(String p) {
		this.country = p;
	}

	/**
	 * Get country
	 * @return String
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Set homePhone
	 * @param <code>String</code>
	 */
	public void setHomePhone(String p) {
		this.homePhone = p;
	}

	/**
	 * Get homePhone
	 * @return String
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * Set workPhone
	 * @param <code>String</code>
	 */
	public void setWorkPhone(String p) {
		this.workPhone = p;
	}

	/**
	 * Get workPhone
	 * @return String
	 */
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * Set extension
	 * @param <code>String</code>
	 */
	public void setExtension(String p) {
		this.extension = p;
	}

	/**
	 * Get extension
	 * @return String
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Set fax
	 * @param <code>String</code>
	 */
	public void setFax(String p) {
		this.fax = p;
	}

	/**
	 * Get 
	 * @return String
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Set cellPhone
	 * @param <code>String</code>
	 */
	public void setCellPhone(String p) {
		this.cellPhone = p;
	}

	/**
	 * Get cellPhone
	 * @return String
	 */
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * Set email
	 * @param <code>String</code>
	 */
	public void setEmail(String p) {
		this.email = p;
	}

	/**
	 * Get email
	 * @return String
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Set addressTypeCode
	 * @param <code>String</code>
	 */
	public void setAddressTypeCode(String p) {
		this.addressTypeCode = p;
	}

	/**
	 * Get addressTypeCode
	 * @return String
	 */
	public String getAddressTypeCode() {
		return addressTypeCode;
	}
	
	/**
	 * Set prefAddressInd
	 * @param <code>String</code>
	 */
	public void setPrefAddressInd(String p) {
		this.prefAddressInd = p;
	}

	/**
	 * Get prefAddressInd
	 * @return String
	 */
	public String getPrefAddressInd() {
		return prefAddressInd;
	}

}
