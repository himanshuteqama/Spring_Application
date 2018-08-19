/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.solenoid.connector.dto.LinkItemToWarehouseDTO;
import com.solenoid.connector.exact.response.ExactLinkItemToWarehousePostResponse;
import com.solenoid.connector.exact.response.Warehouse;
import com.solenoid.connector.exception.ExactException;

/**
 * WarehouseService to manage warehouse related operations.
 */
public interface WarehouseService {

	List<Warehouse> getAll() throws ExactException;

	ResponseEntity<ExactLinkItemToWarehousePostResponse> linkItemAndWarehosue(
			LinkItemToWarehouseDTO linkItemToWarehouseDTO)
			throws ExactException;
}
