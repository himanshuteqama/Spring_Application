/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.exception;

import java.util.HashSet;
import java.util.Set;

import com.solenoid.connector.error.ExactError;

/**
 * Exact exception.
 */
public class ExactException extends Exception {

	private static final long serialVersionUID = 2803422280753934914L;

	private Set<ExactError> errors = new HashSet<ExactError>();

	public ExactException(ExactError exactError, Throwable cause) {
		super(cause);
		this.errors.add(exactError);
	}

	public ExactException(ExactError exactError) {
		this.errors.add(exactError);
	}

	public Set<ExactError> getErrors() {
		return errors;
	}

	public void setErrors(Set<ExactError> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "ExactException [errors=" + this.errors + "]";
	}
}
