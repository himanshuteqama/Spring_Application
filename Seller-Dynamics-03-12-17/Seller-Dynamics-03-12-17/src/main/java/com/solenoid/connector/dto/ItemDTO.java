/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Item, to hold item details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {

	/*@JsonProperty(value = "ID")
	private String item;*/

	@JsonProperty(value = "Code")
	private String itemCode;

	@JsonProperty(value = "Description")
	private String description;

	/*public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}*/

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

	@Override
	public String toString() {
		return "ItemDTO [itemCode=" + itemCode + ", description=" + description
				+ "]";
	}
}
































