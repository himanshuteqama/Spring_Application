/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import java.util.List;

import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.exception.SDException;
import com.solenoid.connector.sd.beans.RetailerMarketplace;

/**
 * MarketplacesService to manage MarketPlaces operation.
 */
public interface MarketplacesService {

	public List<RetailerMarketplace> getMarketplacesFromSD(PreferencesBean preferencesBean) throws SDException, ExactException;

}
