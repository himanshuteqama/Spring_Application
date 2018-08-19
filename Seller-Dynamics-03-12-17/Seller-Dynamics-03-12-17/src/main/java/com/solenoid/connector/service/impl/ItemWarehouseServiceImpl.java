/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solenoid.connector.dto.ItemWarehouseDTO;
import com.solenoid.connector.exact.response.ExactItemWarehouseResponse;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.service.ItemWarehouseService;
import com.solenoid.connector.util.ExactURL;

/**
 * ItemWarehouseService to check Item is mapped with Warehouse 
 */
@Service
public class ItemWarehouseServiceImpl extends ExactBaseService implements ItemWarehouseService{

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ItemWarehouseServiceImpl.class);
	
	@Override
	public List<ItemWarehouseDTO> checkMapping(String item) throws ExactException {
	
		LOGGER.info("Checking Item is mapped with Warehouse.");
		ResponseEntity<ExactItemWarehouseResponse> response = restTemplate
				.exchange(
						ExactURL.getURL()
								+ "/inventory/ItemWarehouses?$select=Item,Warehouse&$filter=Item eq guid'"
								+ item + "'",
						HttpMethod.GET, this.getHttpEntity(null),
						ExactItemWarehouseResponse.class);
		
		ExactItemWarehouseResponse result = response.getBody();
		List<ItemWarehouseDTO> returnMappingResult= null;
		
		if (result != null && result.getD() != null
				&& result.getD().getResults() != null
				&& !result.getD().getResults().isEmpty()
				&& result.getD().getResults().get(0) != null) {
			
			returnMappingResult = result.getD().getResults();
			LOGGER.info("Item is mapped with warehouse.");
		}
		return returnMappingResult;
	}

}
