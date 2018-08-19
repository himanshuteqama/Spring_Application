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
public class ExactVatResponse {

	private ExactVatResult d;

	public ExactVatResult getD() {
		return d;
	}

	public void setD(ExactVatResult d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "ExactResponse [d=" + d + "]";
	}
}
