/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Current Division Result to hold Current Division list.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentDivisionResult {

	private List<CurrentDivision> results;

	public List<CurrentDivision> getResults() {
		return results;
	}

	public void setResults(List<CurrentDivision> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "CurrentDivisionResult [results=" + results + "]";
	}

}
