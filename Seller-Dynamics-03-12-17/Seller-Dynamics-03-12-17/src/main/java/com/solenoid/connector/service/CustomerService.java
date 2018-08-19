/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import java.util.ArrayList;

import com.solenoid.connector.dto.CustomerDTO;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.beans.CustomerOrder;

/**
 * Customer service to manage customer operation.
 */
public interface CustomerService {
	CustomerDTO createCustomerAndSalesOrder(
			ArrayList<CustomerOrder> getCustomerOrdersResponse, String warehouse)
			throws ExactException;
}
