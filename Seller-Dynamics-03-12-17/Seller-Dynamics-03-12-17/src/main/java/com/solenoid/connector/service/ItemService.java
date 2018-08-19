/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import java.util.ArrayList;

import com.solenoid.connector.dto.ItemDTO;
import com.solenoid.connector.dto.ItemResponseDTO;
import com.solenoid.connector.exception.ExactException;

/**
 * Item service to manage item related operations.
 */
public interface ItemService {

	ItemResponseDTO create(ItemDTO itemDTO) throws ExactException;

	ItemResponseDTO find(String productCode) throws ExactException;

	ArrayList<ItemResponseDTO> getItemsFromExact(String lastItemId, int divisionId) throws ExactException;
	
} 
