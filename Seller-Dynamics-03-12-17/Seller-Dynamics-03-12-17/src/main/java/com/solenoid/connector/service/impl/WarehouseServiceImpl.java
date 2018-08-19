/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import com.solenoid.connector.dto.LinkItemToWarehouseDTO;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exact.response.ExactLinkItemToWarehousePostResponse;
import com.solenoid.connector.exact.response.ExactWarehouseResponse;
import com.solenoid.connector.exact.response.Warehouse;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.service.WarehouseService;
import com.solenoid.connector.util.ExactURL;

/**
 * Warehouse service to manage warehouse related operations. 
 */
@Service
public class WarehouseServiceImpl extends ExactBaseService implements WarehouseService {

    private final static Logger LOGGER = LoggerFactory.getLogger(WarehouseServiceImpl.class);

    @Override
    public List<Warehouse> getAll() throws ExactException {
        LOGGER.info("Get all Warehouse.");
        ResponseEntity<ExactWarehouseResponse> response = restTemplate.exchange(
                ExactURL.getURL() + "/inventory/Warehouses?$select=ID,Code,Description", HttpMethod.GET,
                this.getHttpEntity(null), ExactWarehouseResponse.class);
        if (response == null || response.getBody() == null || response.getBody().getResults() == null
                || response.getBody().getResults().getResults().isEmpty()) {
            throw new ExactException(ExactError.EXACT_ERROR_WAREHOUSE_NOT_FOUND);
        }
        return response.getBody().getResults().getResults();
    }

    @Override
	public ResponseEntity<ExactLinkItemToWarehousePostResponse> linkItemAndWarehosue(
			LinkItemToWarehouseDTO linkItemToWarehouseDTO)
            throws ExactException {
        LOGGER.info("Linking item with warehosue");
        ResponseEntity<ExactLinkItemToWarehousePostResponse> postResponse = restTemplate.exchange(
                ExactURL.getURL() + "/inventory/ItemWarehouses", HttpMethod.POST,
                this.getHttpEntity(linkItemToWarehouseDTO), ExactLinkItemToWarehousePostResponse.class);

        if (postResponse == null || postResponse.getBody() == null
                || postResponse.getBody().getLinkItemToWarehouseDTO() == null) {
            throw new ExactException(ExactError.EXACT_ERROR_WAREHOUSE_LINKING_WITH_ITEM_FAILED);
        }
        return postResponse;
    }

}
