/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.solenoid.connector.dto.ItemWarehouseDTO;

/**
 * EXACT result DTO to hold results.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactItemWarehouseResult {

	private List<ItemWarehouseDTO> results;

	public List<ItemWarehouseDTO> getResults() {
		return results;
	}

	public void setResults(List<ItemWarehouseDTO> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ExactItemWarehouseResult [results=" + results + "]";
	}

}
