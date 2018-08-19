/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Supplier, to hold Supplier details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDTO {

	@JsonProperty(value = "ID")
	private String supplierID;

	@JsonProperty(value = "Code")
	private String code;

	@JsonProperty(value = "Name")
	private String name;

	public String getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(String supplierID) {
		this.supplierID = supplierID;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SupplierDTO [supplierID=" + supplierID + ", code=" + code
				+ ", name=" + name + "]";
	}

}
