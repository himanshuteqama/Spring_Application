/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.error.SDError;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.exception.SDException;
import com.solenoid.connector.sd.SDApiClient;
import com.solenoid.connector.sd.beans.GetRetailerMarketplaces;
import com.solenoid.connector.sd.beans.GetRetailerMarketplacesResponse;
import com.solenoid.connector.sd.beans.RetailerMarketplace;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.MarketplacesService;

/**
 * Market service to manage marketplaces operations.
 */
@Service
public class MarketplacesServiceImpl implements MarketplacesService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(MarketplacesServiceImpl.class);

	@Autowired
	private SDApiClient sdApiClient;

	@Autowired
	private FileOperationService fileOperationService;

	@Override
	public List<RetailerMarketplace> getMarketplacesFromSD(PreferencesBean prefBean)
			throws SDException, ExactException {
//		PreferencesBean preferencesBean = fileOperationService
//				.getPreferencesDetail(prefBean.getDivisionId());
		String encryptedLogin = prefBean.getEncryptedLogin();
		String retailerId = prefBean.getRetailerID();
		GetRetailerMarketplaces market = new GetRetailerMarketplaces();
		market.setEncryptedLogin(encryptedLogin);
		market.setRetailerId(retailerId);

		LOGGER.info("Get Marketplaces from Seller Dynamics. ");
		GetRetailerMarketplacesResponse apiResponse1 = (GetRetailerMarketplacesResponse) this.sdApiClient
				.callApi(
						"https://my.sellerdynamics.com/GetRetailerMarketplaces",
						market);

		if (apiResponse1 == null
				|| apiResponse1.getGetRetailerMarketplacesResult() == null
				|| apiResponse1.getGetRetailerMarketplacesResult()
						.getRetailerMarketplaces() == null
				|| apiResponse1.getGetRetailerMarketplacesResult()
						.getRetailerMarketplaces().getRetailerMarketplace()
						.isEmpty()) {
			throw new SDException(SDError.SD_ERROR_MARKETPLACES_NOT_FOUND);
		}

		return apiResponse1.getGetRetailerMarketplacesResult()
				.getRetailerMarketplaces().getRetailerMarketplace();
	}

}
