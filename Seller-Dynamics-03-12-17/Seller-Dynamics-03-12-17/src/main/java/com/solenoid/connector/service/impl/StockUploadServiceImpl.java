/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import java.security.SecureRandom;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.solenoid.connector.dto.ItemResponseDTO;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.SDApiClient;
import com.solenoid.connector.sd.beans.UploadStock;
import com.solenoid.connector.sd.beans.UploadStockResponse;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.StockuploadService;

/**
 * SalesOrder service to manage sales order related operations.
 */
@Service
@Qualifier("stockuploadService")
public class StockUploadServiceImpl extends WebServiceGatewaySupport implements
		StockuploadService {
	private final static Logger LOGGER = LoggerFactory
			.getLogger(StockUploadServiceImpl.class);

	static final String numbers = "0123456789";
	static SecureRandom random = new SecureRandom();
	
	@Autowired
	private SDApiClient sdApiClient;
	@Autowired
	private FileOperationService fileOperationService;

	@Override
	public UploadStockResponse upload(ArrayList<ItemResponseDTO> result, int divisionId) throws ExactException {
		UploadStockResponse uploadStockResponse = null;
		UploadStock request = new UploadStock();
		LOGGER.info("Uploading stock to SD");
		PreferencesBean preferencesBean;
		try {
			preferencesBean = fileOperationService.getPreferencesDetail(divisionId);
		} catch (ExactException e) {
			throw new ExactException(ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
		}		
		request.setEncryptedLogin(preferencesBean.getEncryptedLogin());
		request.setRetailerId(preferencesBean.getRetailerID());
		// request.setStockString("RetailerSKU,Barcode,Alternative Desc,CostPrice,AvailableStock TEST26458,5060199420398,Test 38,9.30,1123");
		
		String stockString = "RetailerSKU,Barcode,Alternative Desc,CostPrice,AvailableStock\r\n";
		String addToStockString = "";
		for (int i = 0; i < result.size(); i++) {
			double stock = result.get(i).getStock();
			int availableStock = (int) stock;
			String barcodeRandom = randomNumber(13);
		
			addToStockString = addToStockString+ result.get(i).getItemCode()
					+ ","
					+ barcodeRandom
					+ ","
					+ result.get(i).getDescription()
					+ ","
					+ result.get(i).getNetCost()
					+ ","
					+ availableStock+"\n";
		}
		
		stockString = stockString + addToStockString;
		request.setStockString(stockString);
		request.setImportName("Importing stock from Exact");
		request.setImportDescription("Importing stock from Exact");
		
		Object apiResponse = this.sdApiClient.callApi(
				"https://my.sellerdynamics.com/UploadStock", request);
		
		if (apiResponse != null) {
			uploadStockResponse = (UploadStockResponse) apiResponse;
		}
		/*for (int i = 0; i <= result.size(); i++) {
			double stock = result.get(i).getStock();
			int availableStock = (int) stock;
			String barcodeRandom = randomNumber(13);
			request.setStockString("RetailerSKU,Barcode,Alternative Desc,CostPrice,AvailableStock\r\n"
					+ result.get(i).getItemCode()
					+ ","
					+ barcodeRandom
					+ ","
					+ result.get(i).getDescription()
					+ ","
					+ result.get(i).getNetCost()
					+ ","
					+ availableStock + "\n"
					+"5050966035955OWN1"+","+"3038190746121"+","+"Wolsey Shirt Test"+","+"0.0"+","+"0");

			request.setImportName(result.get(i)
					.getItemCode());
			request.setImportDescription("Importing stock from Exact");
			LOGGER.info("Request SD :");
			for (Field field : request.getClass().getDeclaredFields()) {
			    field.setAccessible(true);
			    String name = field.getName();
			    Object value = null;
				try {
					value = field.get(request);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    System.out.printf("%s : %s%n", name, value);
			}
			Object apiResponse = this.sdApiClient.callApi(
					"https://my.sellerdynamics.com/UploadStock", request);

			if (apiResponse != null) {
				uploadStockResponse = (UploadStockResponse) apiResponse;
			}
		}*/
		LOGGER.info("Uploaded stock to SD successfully.");
		return uploadStockResponse;
	}

	static String randomNumber(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(numbers.charAt(random.nextInt(numbers.length())));
		return sb.toString();
	}
}
