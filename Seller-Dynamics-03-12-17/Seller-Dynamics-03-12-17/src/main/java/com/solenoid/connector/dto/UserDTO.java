/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for User, to hold user details and to use it in EXACT API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

	@JsonProperty(value = "UserID")
	private String userId;

	@JsonProperty(value = "FullName")
	private String userFullName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userFullName=" + userFullName
				+ "]";
	}
}



