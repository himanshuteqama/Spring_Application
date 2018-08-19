/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.util;

/**
 * Item code utility methods.
 */
public class CodeUtil {

	public static String toCode(String value) {
		String returnValue = value;
		if (value != null && !value.equals("") && value.length() > 3) {
			returnValue = value.substring(0, 3);
		}
		return returnValue;
	}

}
