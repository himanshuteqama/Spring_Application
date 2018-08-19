/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solenoid.connector.dto.SalesOrderResponseDTO;

/**
 * EXACT response for Sales Order.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactResponseSalesOrder {

	@JsonProperty(value = "d")
	private SalesOrderResponseDTO results;

	public SalesOrderResponseDTO getResults() {
		return results;
	}

	public void setResults(SalesOrderResponseDTO results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ExactResponseSalesOrder [results=" + results + "]";
	}

}
