/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Sales Order, to hold item details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderDTO {

	@JsonProperty(value = "Description")
	private String description;

	@JsonProperty(value = "OrderedBy")
	private String orderedBy;

	@JsonProperty(value = "WarehouseID")
	private String warehouseID;

	@JsonProperty(value = "SalesOrderLines")
	private List<SalesOrderLines> salesOrderLines;

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

	public List<SalesOrderLines> getSalesOrderLines() {
		return salesOrderLines;
	}

	public void setSalesOrderLines(List<SalesOrderLines> salesOrderLines) {
		this.salesOrderLines = salesOrderLines;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	@Override
	public String toString() {
		return "SalesOrderDTO [description=" + description + ", orderedBy="
				+ orderedBy + ", warehouseID=" + warehouseID
				+ ", SalesOrderLines=" + salesOrderLines + "]";
	}

}
