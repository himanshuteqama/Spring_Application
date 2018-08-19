/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Link item to warehouse, to hold details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkItemToWarehouseDTO {

	@JsonProperty(value = "Item")
	private String item;

	@JsonProperty(value = "Warehouse")
	private String warehouse;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	@Override
	public String toString() {
		return "LinkItemToWarehouseDTO [item=" + item + ", warehouse="
				+ warehouse + "]";
	}

}
