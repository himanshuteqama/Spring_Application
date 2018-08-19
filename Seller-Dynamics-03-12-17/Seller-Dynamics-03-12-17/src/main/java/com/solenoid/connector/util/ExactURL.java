/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.util;

import com.solenoid.connector.exception.ExactException;

/**
 * Utility class for EXACT URL.
 */
public class ExactURL {

	public static final String BASE_API_URL = "https://start.exactonline.co.uk/api/v1/";

	public static String getURL() throws ExactException {
		return BASE_API_URL + RequestInfoHolder.getDivisionId();
	}
}
