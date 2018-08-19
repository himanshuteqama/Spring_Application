/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.solenoid.connector.dto.SalesOrderLines;
import com.solenoid.connector.exact.response.ExactResponseSalesOrder;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.beans.CustomerOrder;

/**
 * SalesOrderService to manage SalesOrder operations.
 */
public interface SalesOrderService {

	ExactResponseSalesOrder getSalesOrders() throws ExactException;

	public ArrayList<CustomerOrder> getSalesOrderFromSD(int divisionId)
			throws ExactException, ParseException;

	ResponseEntity<ExactResponseSalesOrder> create(String description,
			String warehouseID, String orderedBy,
			List<SalesOrderLines> salesOrderLines) throws ExactException;

}
