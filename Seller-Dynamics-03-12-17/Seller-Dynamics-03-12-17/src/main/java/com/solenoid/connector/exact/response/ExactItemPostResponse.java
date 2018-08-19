/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solenoid.connector.dto.ItemResponseDTO;

/**
 * EXACT Item post response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactItemPostResponse {

	@JsonProperty(value = "d")
	private ItemResponseDTO itemDTO;

	public ItemResponseDTO getItemDTO() {
		return itemDTO;
	}

	public void setItemDTO(ItemResponseDTO itemDTO) {
		this.itemDTO = itemDTO;
	}

	@Override
	public String toString() {
		return "ExactPostResponse [itemDTO=" + itemDTO + "]";
	}
}
