/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * EXACT response to hold customer details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCustomer {

	private GetCustomerResponse d;

	public GetCustomerResponse getD() {
		return d;
	}

	public void setD(GetCustomerResponse d) {
		this.d = d;
	}

	@Override
	public String toString() {
		return "GetCustomer [d=" + d + "]";
	}

}
