/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * EXACT response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactItemWarehouseResponse {

	private ExactItemWarehouseResult d;

	public ExactItemWarehouseResult getD() {
		return d;
	}

	public void setD(ExactItemWarehouseResult d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "ExactItemWarehouseResponse [d=" + d + "]";
	}
}
