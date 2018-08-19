/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Item, to hold item details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemResponseDTO {

	@JsonProperty(value = "ID")
	private String item;

	@JsonProperty(value = "Code")
	private String itemCode;

	@JsonProperty(value = "Description")
	private String description;
	
	@JsonProperty(value = "ItemGroup")
	private String itemGroup;
	
	@JsonIgnore
	private boolean isBOM;

	@JsonIgnore
	private String refItemID;

	@JsonIgnore
	private double quantity;

	@JsonProperty("Unit")
	private String unit;

	@JsonProperty(value = "CostPriceStandard")
	private double unitCost;

	@JsonProperty(value = "Barcode")
	private String barcode;

	@JsonProperty(value = "Stock")
	private double stock;

	@JsonIgnore
	private double netCost;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsBOM() {
		return isBOM;
	}

	public void setIsBOM(boolean isBOM) {
		this.isBOM = isBOM;
	}

	public String getRefItemID() {
		return refItemID;
	}

	public void setRefItemID(String refItemID) {
		this.refItemID = refItemID;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public double getNetCost() {
		return netCost;
	}

	public void setNetCost(double netCost) {
		this.netCost = netCost;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}
	
	public String getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(String itemGroup) {
		this.itemGroup = itemGroup;
	}

	@Override
	public String toString() {
		return "ItemResponseDTO [item=" + item + ", itemCode=" + itemCode
				+ ", description=" + description + ", itemGroup=" + itemGroup
				+ ", isBOM=" + isBOM + ", refItemID=" + refItemID
				+ ", quantity=" + quantity + ", unit=" + unit + ", unitCost="
				+ unitCost + ", barcode=" + barcode + ", stock=" + stock
				+ ", netCost=" + netCost + "]";
	}

}