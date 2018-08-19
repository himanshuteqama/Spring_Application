/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * EXACT WarehouseResult DTO, to hold list of Warehouse.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WarehouseResult {

	@JsonProperty(value = "results")
	private List<Warehouse> results;

	public List<Warehouse> getResults() {
		return results;
	}

	public void setResults(List<Warehouse> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "WarehouseResult [results=" + results + "]";
	}

}
