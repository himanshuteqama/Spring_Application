/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.solenoid.connector.dto.VatResponseDTO;

/**
 * EXACT result DTO to hold results.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactVatResult {

	private List<VatResponseDTO> results;

	public List<VatResponseDTO> getResults() {
		return results;
	}

	public void setResults(List<VatResponseDTO> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ExactVatResult [results=" + results + "]";
	}

}
