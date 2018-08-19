/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Current Division Response DTO.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentDivisionResponse {

	@JsonProperty(value = "d")
	private CurrentDivisionResult division;

	public CurrentDivisionResult getDivision() {
		return division;
	}

	public void setDivision(CurrentDivisionResult division) {
		this.division = division;
	}

	@Override
	public String toString() {
		return "CurrentDivisionResponse [division=" + division + "]";
	}
}