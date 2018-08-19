/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.solenoid.connector.dto.SupplierDTO;

/**
 * EXACT result to hold supplier results.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactSupplierResult {

	private List<SupplierDTO> results;

	public List<SupplierDTO> getResults() {
		return results;
	}

	public void setResults(List<SupplierDTO> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ExactSupplierResult [results=" + results + "]";
	}

}
