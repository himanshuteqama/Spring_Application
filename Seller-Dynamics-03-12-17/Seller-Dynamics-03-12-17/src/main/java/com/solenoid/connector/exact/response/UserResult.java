/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solenoid.connector.dto.UserDTO;

/**
 * EXACT User DTO, to hold list of users.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResult {

	@JsonProperty(value = "results")
	private List<UserDTO> results;

	public List<UserDTO> getResults() {
		return results;
	}

	public void setResults(List<UserDTO> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "UserResult [results=" + results + "]";
	}
}
