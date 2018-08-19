/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.oauth2.bean.OAuthToken;

/**
 * Job service to manage the job schedular's operation.
 */
public interface JobService {

	public void startItemSchedular(int divisionId, String frequency, String frequencyDur, OAuthToken oAuthToken) throws ExactException;
	public void startSalesOrderSchedular(int divisionId, String frequency, String frequencyDur, OAuthToken oAuthToken) throws ExactException;
	public void stopSchedular(int divisionId) throws ExactException;
	public int convertInSeconds(String frequency, String frequencyDur);
}
