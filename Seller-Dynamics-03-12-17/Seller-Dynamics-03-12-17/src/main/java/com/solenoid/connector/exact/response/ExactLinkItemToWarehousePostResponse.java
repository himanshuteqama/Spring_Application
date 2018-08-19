/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solenoid.connector.dto.LinkItemToWarehouseDTO;

/**
 * EXACT LinkItemToWarehouse post response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactLinkItemToWarehousePostResponse {

	@JsonProperty(value = "d")
	private LinkItemToWarehouseDTO linkItemToWarehouseDTO;

	public LinkItemToWarehouseDTO getLinkItemToWarehouseDTO() {
		return linkItemToWarehouseDTO;
	}

	public void setLinkItemToWarehouseDTO(
			LinkItemToWarehouseDTO linkItemToWarehouseDTO) {
		this.linkItemToWarehouseDTO = linkItemToWarehouseDTO;
	}

}
