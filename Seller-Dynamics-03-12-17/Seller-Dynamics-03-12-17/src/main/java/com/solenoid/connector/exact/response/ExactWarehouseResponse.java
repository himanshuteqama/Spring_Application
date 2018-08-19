/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * EXACT warehouse response.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactWarehouseResponse {

	@JsonProperty(value = "d")
	private WarehouseResult data;

	public WarehouseResult getResults() {
		return data;
	}

	public void setResults(WarehouseResult data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ExactWarehouseResponse [data=" + data + "]";
	}

}
