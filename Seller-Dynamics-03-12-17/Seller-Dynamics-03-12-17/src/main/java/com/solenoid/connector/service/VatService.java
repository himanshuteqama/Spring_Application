/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import com.solenoid.connector.dto.VatResponseDTO;
import com.solenoid.connector.exception.ExactException;

/**
 * VAT service to manage VAT operation.
 */
public interface VatService {
	
	public VatResponseDTO createVat(String code, String description)throws ExactException;
}
