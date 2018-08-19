/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solenoid.connector.dto.VatDTO;
import com.solenoid.connector.dto.VatResponseDTO;
import com.solenoid.connector.exact.response.ExactVatPostResponse;
import com.solenoid.connector.exact.response.ExactVatResponse;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.service.VatService;
import com.solenoid.connector.util.ExactURL;

/**
 * VAT service to manage VAT related operations.
 */
@Service
@Qualifier("vatService")
public class VatServiceImpl extends ExactBaseService implements VatService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CustomerServiceImpl.class);

	@Override
	public VatResponseDTO createVat(String code, String description)
			throws ExactException {
		
		VatDTO vatDTO = new VatDTO();
		
		vatDTO.setDescription(description);
		ResponseEntity<ExactVatPostResponse> postResponse = null;
		String cc = code.substring(0,2);
		vatDTO.setCode(cc);
		VatResponseDTO returnVat = this.find(cc);
		if(returnVat == null){
		
			postResponse = restTemplate.exchange(
	                ExactURL.getURL() + "/vat/VATCodes", HttpMethod.POST, this.getHttpEntity(vatDTO),
	                ExactVatPostResponse.class);
			
			return postResponse.getBody().getVatResponseDTO();
		}else{
			return returnVat;
		}
		
	}
	
	
	public VatResponseDTO find(String code) throws ExactException {
		LOGGER.info("find vat: " + code);
		ResponseEntity<ExactVatResponse> response = restTemplate
				.exchange(
						ExactURL.getURL()
								+ "/vat/VATCodes?$select=ID,Code,Description&$filter=Code eq '"
								+ code + "'", HttpMethod.GET, this.getHttpEntity(null), ExactVatResponse.class);
		ExactVatResponse result = response.getBody();
		VatResponseDTO returnVat = null;
		// If found return that only, otherwise create new one.
		if (result != null && result.getD() != null
				&& result.getD().getResults() != null
				&& !result.getD().getResults().isEmpty()
				&& result.getD().getResults().get(0) != null) {
			returnVat = result.getD().getResults().get(0);
			LOGGER.info("Vat found: " + returnVat);
		} else {
			LOGGER.info("Vat not found !! ");
		}
		return returnVat;
	}


}
