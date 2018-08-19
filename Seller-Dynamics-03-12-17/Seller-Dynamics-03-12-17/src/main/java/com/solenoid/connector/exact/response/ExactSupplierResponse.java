/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * EXACT response for Supplier to hold supplier details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactSupplierResponse {

	private ExactSupplierResult d;

	public ExactSupplierResult getD() {
		return d;
	}

	public void setD(ExactSupplierResult d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "ExactSupplierResponse [d=" + d + "]";
	}

}
