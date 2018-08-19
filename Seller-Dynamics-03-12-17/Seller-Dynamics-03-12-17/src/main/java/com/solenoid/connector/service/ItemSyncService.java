/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.service;

import com.solenoid.connector.exception.ExactException;

/**
 * ItemSyncService to manage sync operation.
 */
public interface ItemSyncService {
	public void itemSync(int divisionId) throws ExactException;

}
