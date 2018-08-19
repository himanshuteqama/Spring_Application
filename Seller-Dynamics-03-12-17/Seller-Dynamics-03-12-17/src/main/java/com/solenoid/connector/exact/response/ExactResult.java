/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solenoid.connector.dto.ItemResponseDTO;

/**
 * EXACT result DTO to hold results.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactResult {

	private List<ItemResponseDTO> results;

	@JsonProperty("__next")
	private String nextLink;

	public List<ItemResponseDTO> getResults() {
		return results;
	}

	public String getNextLink() {
		return nextLink;
	}

	public void setNextLink(String nextLink) {
		this.nextLink = nextLink;
	}

	public void setResults(List<ItemResponseDTO> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ExactResult [results=" + results + ", link=" + nextLink + "]";
	}

}
