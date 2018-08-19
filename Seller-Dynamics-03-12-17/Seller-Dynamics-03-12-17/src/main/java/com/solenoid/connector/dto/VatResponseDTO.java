/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for VAT, to hold VAT details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VatResponseDTO {

	@JsonProperty(value = "ID")
	private String vatID;

	@JsonProperty(value = "Code")
	private String code;

	@JsonProperty(value = "Description")
	private String description;

	/*@JsonProperty(value = "VATPercentages")
	private String VATPercentages;*/

	public String getCode() {
		return code;
	}

	public String getVatID() {
		return vatID;
	}

	public void setVatID(String vatID) {
		this.vatID = vatID;
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
		return "VatDTO [vatID=" + vatID + ", code=" + code + ", description="
				+ description + "]";
	}

}

