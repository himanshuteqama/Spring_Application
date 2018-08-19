/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * EXACT response to hold Customer list.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCustomerResponse {

	private List<CustomerResponseResults> results;

	public List<CustomerResponseResults> getResults() {
		return results;
	}

	public void setResults(List<CustomerResponseResults> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ClassPojo [results = " + results + "]";
	}
}
