/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import java.util.Date;

import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.dto.SyncDetailsJSON;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.oauth2.bean.OAuthToken;

/**
 * FileOperationService to manage json file related operations.
 */
public interface FileOperationService {

	public void createAndUpdatePrefernces(PreferencesBean quoteClient,
			int divisionId, OAuthToken oAuthToken) throws ExactException;

	public void createSyncDetails(int divisionId, Date syncStartDate)
			throws ExactException;

	public void updateSyncDetails(int divisionId) throws ExactException;

	public PreferencesBean getPreferencesDetail(int division)
			throws ExactException;

	public SyncDetailsJSON getSyncDetail(int division) throws ExactException;

}
