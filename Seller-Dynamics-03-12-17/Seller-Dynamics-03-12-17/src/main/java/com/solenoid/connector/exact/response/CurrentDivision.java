/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Current Division DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentDivision {

	@JsonProperty(value = "CurrentDivision")
	private int division;

	@JsonProperty(value = "FullName")
	private String fullName;

	public int getDivision() {
		return division;
	}

	public void setDivision(int division) {
		this.division = division;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "CurrentDivision [division=" + division + ", fullName="
				+ fullName + "]";
	}

}
