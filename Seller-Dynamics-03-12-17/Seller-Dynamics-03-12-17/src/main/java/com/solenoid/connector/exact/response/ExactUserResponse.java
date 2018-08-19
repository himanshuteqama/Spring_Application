/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * EXACT user response.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactUserResponse {

	@JsonProperty(value = "d")
	private UserResult data;

	public UserResult getData() {
		return data;
	}

	public void setData(UserResult data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ExactUserResponse []";
	}

}

