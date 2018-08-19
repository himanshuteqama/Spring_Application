/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Sales Order, to hold item details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderResponseDTO {

	@JsonProperty(value = "Description")
	private String description;

	@JsonProperty(value = "OrderedBy")
	private String orderedBy;

	@JsonProperty(value = "WarehouseID")
	private String warehouseID;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}
}
