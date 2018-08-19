/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.solenoid.connector.dto.ItemResponseDTO;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.beans.UploadStockResponse;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.ItemService;
import com.solenoid.connector.service.ItemSyncService;
import com.solenoid.connector.service.StockuploadService;

/**
 * ItemSync service to manage sync operation
 */
@Service
public class ItemSyncServiceImpl implements ItemSyncService{

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ItemSyncServiceImpl.class);
	
	@Autowired
	@Qualifier("stockuploadService")
	private StockuploadService stockuploadService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	FileOperationService jsonFileService;
	
	@SuppressWarnings("unused")
	@Override
	public void itemSync(int divisionId) throws ExactException{
		ArrayList<ItemResponseDTO> result = itemService.getItemsFromExact(null, divisionId);
		if(result.size()>0){
			UploadStockResponse res = stockuploadService.upload(result,divisionId);
		}else{
			throw new ExactException(
					ExactError.EXACT_ERROR_ITEM_NOT_FOUND);
		}
		jsonFileService.updateSyncDetails(divisionId);
		LOGGER.info("Items sync sucessfully.");
	}
}
