/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Customer, to hold Customer details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {

	@JsonProperty(value = "ID")
	private String customerID;

	@JsonProperty(value = "Name")
	private String accountName;

	@JsonProperty(value = "Status")
	private String status;

	@JsonProperty(value = "AddressLine1")
	private String addressLine1;

	@JsonProperty(value = "AddressLine2")
	private String addressLine2;

	@JsonProperty(value = "AddressLine3")
	private String addressLine3;

	@JsonProperty(value = "City")
	private String city;

	@JsonProperty(value = "Country")
	private String country;

	@JsonProperty(value = "Email")
	private String email;

	@JsonProperty(value = "Phone")
	private String phone;

	@JsonProperty(value = "Postcode")
	private String postCode;

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "CustomerDTO [customerID=" + customerID + ", accountName="
				+ accountName + ", status=" + status + ", addressLine1="
				+ addressLine1 + ", addressLine2=" + addressLine2
				+ ", addressLine3=" + addressLine3 + ", city=" + city
				+ ", country=" + country + ", email=" + email + ", phone="
				+ phone + ", postCode=" + postCode + "]";
	}

}