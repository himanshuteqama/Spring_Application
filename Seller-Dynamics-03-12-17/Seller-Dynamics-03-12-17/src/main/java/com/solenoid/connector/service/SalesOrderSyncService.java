/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.service;

import com.solenoid.connector.exception.ExactException;

/**
 * SalesOrderSyncService to manage SalesOrder sync operation.
 */
public interface SalesOrderSyncService {

	public void salesOrderSync(int divisionId,
			String warehouse) throws ExactException;
}
