/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */


package com.solenoid.connector.service;

import java.util.List;

import com.solenoid.connector.dto.ItemWarehouseDTO;
import com.solenoid.connector.exception.ExactException;

/**
 * ItemWarehouseService to check Item is mapped with Warehouse 
 */

public interface ItemWarehouseService {

	public List<ItemWarehouseDTO> checkMapping(String item) throws ExactException;
}
