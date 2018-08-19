/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service;

import java.util.ArrayList;

import com.solenoid.connector.dto.ItemResponseDTO;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.beans.UploadStockResponse;

/**
 * StockuploadService to manage StockUpload operation.
 */
public interface StockuploadService {

	UploadStockResponse upload(ArrayList<ItemResponseDTO> result, int divisionId) throws ExactException;

}
