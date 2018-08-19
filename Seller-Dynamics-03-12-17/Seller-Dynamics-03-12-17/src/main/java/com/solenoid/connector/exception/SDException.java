/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exception;

import java.util.HashSet;
import java.util.Set;

import com.solenoid.connector.error.SDError;

/**
 * SD exception.
 */
public class SDException extends Exception {

	private static final long serialVersionUID = 2803422280753934914L;

	private Set<SDError> errors = new HashSet<SDError>();

	public SDException(SDError sdError, Throwable cause) {
		super(cause);
		this.errors.add(sdError);
	}

	public SDException(SDError sdError) {
		this.errors.add(sdError);
	}

	public Set<SDError> getErrors() {
		return errors;
	}

	public void setErrors(Set<SDError> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "SDException [errors=" + errors + "]";
	}
}
