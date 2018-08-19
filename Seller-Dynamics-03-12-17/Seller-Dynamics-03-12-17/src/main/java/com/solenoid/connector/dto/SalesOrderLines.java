/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for SalesOrderLine, to hold data for SalesOrder creation
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderLines {
	@JsonProperty(value = "Item")
	private String item;

	@JsonProperty(value = "VATCode")
	private String vatCode;
	
	public String getItem() {
		return item;
	}

	public void setItem(String Item) {
		this.item = Item;
	}

	public String getVatCode() {
		return vatCode;
	}

	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}

	@Override
	public String toString() {
		return "SalesOrderLines [item=" + item + ", vatCode=" + vatCode + "]";
	}
	
	

}