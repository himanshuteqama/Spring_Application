/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.solenoid.connector.dto.ItemDTO;
import com.solenoid.connector.dto.ItemResponseDTO;
import com.solenoid.connector.dto.SyncDetailsJSON;
import com.solenoid.connector.exact.response.ExactItemPostResponse;
import com.solenoid.connector.exact.response.ExactResponse;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.ItemService;
import com.solenoid.connector.service.StockuploadService;
import com.solenoid.connector.util.ExactURL;

@Service
@Qualifier("itemService")
public class ItemServiceImpl extends ExactBaseService implements ItemService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ItemServiceImpl.class);

	@Autowired
	@Qualifier("stockuploadService")
	private StockuploadService stockuploadService;

	@Autowired
    private FileOperationService fileOperationService;
	
	@Override
	public ItemResponseDTO create(ItemDTO itemDTO) throws ExactException {
		ItemResponseDTO returnItem = this.find(itemDTO.getItemCode());
		ResponseEntity<ExactItemPostResponse> postResponse = null;
		if (returnItem == null) {
			// Create new item.
			LOGGER.info("Item not found, creating new one!");
            postResponse = restTemplate.exchange(
                    ExactURL.getURL() + "/logistics/Items", HttpMethod.POST, this.getHttpEntity(itemDTO),
                    ExactItemPostResponse.class);
		returnItem = postResponse.getBody().getItemDTO();
		LOGGER.info("Created Item: " + returnItem);
		}
		return this.findById(returnItem.getItem());//returnItem;//postResponse.getBody().getItemDTO();//this.findById(returnItem.getItem());
	}

	@Override
	public ItemResponseDTO find(String productCode) throws ExactException {
		LOGGER.info("find item: " + productCode);
		ResponseEntity<ExactResponse> response = restTemplate
				.exchange(
						ExactURL.getURL()
								+ "/logistics/Items?$select=ID,Code,Description,Unit,CostPriceStandard,GLStock&$filter=Code eq '"
								+ productCode + "'", HttpMethod.GET, this.getHttpEntity(null), ExactResponse.class);
		ExactResponse result = response.getBody();
		ItemResponseDTO returnItem = null;
		// If found return that only, otherwise create new one.
		if (result != null && result.getD() != null
				&& result.getD().getResults() != null
				&& !result.getD().getResults().isEmpty()
				&& result.getD().getResults().get(0) != null) {
			returnItem = result.getD().getResults().get(0);
			LOGGER.info("Item found: " + returnItem);
		} else {
			LOGGER.info("Item not found !! ");
		}
		return returnItem;
	}

	public ItemResponseDTO findById(String itemID) throws ExactException {
//		LOGGER.info("" + restTemplate.getAccessToken());
		LOGGER.info("find item: " + itemID);
		ResponseEntity<ExactResponse> response = restTemplate
				.exchange(
						ExactURL.getURL()
								+ "/logistics/Items?$select=ID,Code,Description,Unit,CostPriceStandard,GLStock&$filter=ID eq guid'"
								+ itemID + "'", HttpMethod.GET, this.getHttpEntity(null), ExactResponse.class);
		ExactResponse result = response.getBody();
		ItemResponseDTO returnItem = null;
		// If found return that only, otherwise create new one.
		if (result != null && result.getD() != null
				&& result.getD().getResults() != null
				&& !result.getD().getResults().isEmpty()
				&& result.getD().getResults().get(0) != null) {
			returnItem = result.getD().getResults().get(0);
			LOGGER.info("Item found: " + returnItem);
		} else {
			LOGGER.info("Item not found !! ");
		}
		return returnItem;
	}

	@Override
	public ArrayList<ItemResponseDTO> getItemsFromExact(String lastItemId, int divisionId) throws ExactException {
		
		SyncDetailsJSON syncDetailsJSON = fileOperationService.getSyncDetail(divisionId);
		String lastSyncDate = syncDetailsJSON.getSyncItem().getExactToSd();
		lastSyncDate = lastSyncDate+"Z";
		
		String url = ExactURL.getURL()
				+ "/logistics/Items?$select=ID,Code,Description,Unit,CostPriceStandard,Barcode,Stock,GLStock&$filter=Modified gt datetime'"+ lastSyncDate +"'";
		if (!StringUtils.isEmpty(lastItemId)) {
			url += "&$skiptoken=guid'" + lastItemId + "'";
		}

		LOGGER.info("Get Items from EXACT ");
		ResponseEntity<ExactResponse> response = restTemplate.exchange(url, HttpMethod.GET, this.getHttpEntity(null), 
                ExactResponse.class);
		ExactResponse result = response.getBody();
		String linkNextItems = result.getD().getNextLink();
		ArrayList<ItemResponseDTO> totalItems = new ArrayList<ItemResponseDTO>();
		totalItems.addAll((Collection<? extends ItemResponseDTO>) result.getD()
				.getResults());
		
		
		if(linkNextItems!=null){
			String[] sa = linkNextItems.split("'");
	
			lastItemId = sa[3];
			url += "&$skiptoken=guid'" + lastItemId + "'";
			while (lastItemId != null) {
				LOGGER.info("Get all Items from EXACT through Pagination");
				response = restTemplate.exchange(url, HttpMethod.GET, this.getHttpEntity(null), 
						ExactResponse.class);
				result = response.getBody();
				if (result.getD().getNextLink() != null) {
					totalItems.addAll((Collection<? extends ItemResponseDTO>) result
							.getD().getResults());
					String linkNextItems1 = result.getD().getNextLink();
					String[] sa1 = linkNextItems1.split("'");
					lastItemId = sa1[3];
					url = ExactURL.getURL()
							+ "/logistics/Items?$select=ID,Code,Description,Unit,CostPriceStandard,Barcode,Stock,GLStock&$filter=Modified gt datetime'"+ lastSyncDate +"'";
					url += "&$skiptoken=guid'" + lastItemId + "'";
				} else {
					lastItemId = null;
				}
			}
		}

		return totalItems;

	}

}
