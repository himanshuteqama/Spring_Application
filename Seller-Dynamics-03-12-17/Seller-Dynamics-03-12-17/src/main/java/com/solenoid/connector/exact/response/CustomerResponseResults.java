/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exact.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Customer response result to hold customer id and name.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerResponseResults {

	@JsonProperty(value = "Name")
	private String Name;

	@JsonProperty(value = "ID")
	private String ID;

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	@Override
	public String toString() {
		return "Results [Name=" + Name + ", ID=" + ID + "]";
	}

}
