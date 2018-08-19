/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.solenoid.connector.dto.VatResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * EXACT VAT post response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExactVatPostResponse {

	@JsonProperty(value = "d")
	private VatResponseDTO vatResponseDTO;

	public VatResponseDTO getVatResponseDTO() {
		return vatResponseDTO;
	}

	public void setVatResponseDTO(VatResponseDTO vatResponseDTO) {
		this.vatResponseDTO = vatResponseDTO;
	}

	@Override
	public String toString() {
		return "ExactVatPostResponse [vatResponseDTO=" + vatResponseDTO + "]";
	}

}
