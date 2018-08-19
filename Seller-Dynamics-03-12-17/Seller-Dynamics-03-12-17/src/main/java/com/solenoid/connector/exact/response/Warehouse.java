/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

/**
 * EXACT Warehouse
 * 
 **/

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for Warehouse, to hold warehouse details. 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Warehouse {

	@JsonProperty(value = "ID")
	private String id;

	@JsonProperty(value = "Code")
	private String code;

	@JsonProperty(value = "Description")
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", code=" + code + ", description="
				+ description + "]";
	}

}
