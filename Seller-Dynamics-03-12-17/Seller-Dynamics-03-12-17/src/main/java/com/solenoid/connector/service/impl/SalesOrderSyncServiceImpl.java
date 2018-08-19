/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;

import com.solenoid.connector.dto.CustomerDTO;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.beans.CustomerOrder;
import com.solenoid.connector.service.CustomerService;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.SalesOrderService;
import com.solenoid.connector.service.SalesOrderSyncService;

/**
 * SalesOrderSync service to manage sync operation.
 */
@Service
public class SalesOrderSyncServiceImpl implements SalesOrderSyncService {

	@Autowired
	private FileOperationService fileOperationService;

	@Autowired
	private SalesOrderService salesOrderService;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings("unused")
	@Override
	public void salesOrderSync(int divisionId, String warehouse)
			throws ExactException {
		ArrayList<CustomerOrder> getCustomerOrdersResponse = null;
		try {
			getCustomerOrdersResponse = salesOrderService
					.getSalesOrderFromSD(divisionId);
			System.out.println();
		} catch (ParseException e) {
			throw new ExactException(
					ExactError.EXACT_ERROR_SALES_ORDER_NOT_AVAILABLE);
		}
		if(getCustomerOrdersResponse.size()>0){
			CustomerDTO customerDTO = customerService.createCustomerAndSalesOrder(
				getCustomerOrdersResponse, warehouse);
		}
		fileOperationService.updateSyncDetails(divisionId);
	}
}
