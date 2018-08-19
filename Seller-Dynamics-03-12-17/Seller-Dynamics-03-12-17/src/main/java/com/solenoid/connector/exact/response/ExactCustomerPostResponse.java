/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.solenoid.connector.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * EXACT Customer post response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactCustomerPostResponse {

	@JsonProperty(value = "d")
	private CustomerDTO customerDTO;

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	@Override
	public String toString() {
		return "ExactCustomerPostResponse [customerDTO=" + customerDTO + "]";
	}

}
