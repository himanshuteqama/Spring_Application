/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solenoid.connector.ConnectorConstants;
import com.solenoid.connector.config.ConnectorProperties;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.dto.SyncCustomer;
import com.solenoid.connector.dto.SyncDelivery;
import com.solenoid.connector.dto.SyncDetailsJSON;
import com.solenoid.connector.dto.SyncItem;
import com.solenoid.connector.dto.SyncSalesOrder;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.oauth2.bean.OAuthToken;
import com.solenoid.connector.service.FileOperationService;

/**
 * File operation service to manage JSON file related operations.
 */
@Service
public class FileOperationServiceImpl implements FileOperationService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(FileOperationServiceImpl.class);

	@Autowired
	private ConnectorProperties connectiorProperties;

	@Override
	public void createAndUpdatePrefernces(PreferencesBean quoteClient,
			int divisionId, OAuthToken oAuthToken) throws ExactException {

		File file = new File(connectiorProperties.getFileDirectory()
				+ divisionId + ConnectorConstants.FILE_NAME_PREFERENCES);
		if (!file.exists()) {

			ObjectMapper objectMapper = new ObjectMapper();
			PreferencesBean preferencesBean = new PreferencesBean();
			preferencesBean.setDivisionId(divisionId);
			preferencesBean.setEncryptedLogin(quoteClient.getEncryptedLogin());
			preferencesBean.setRetailerID(quoteClient.getRetailerID());
			preferencesBean.setSalesOrderCheckValue(quoteClient
					.getSalesOrderCheckValue());
			preferencesBean.setCustomerCheckValue(quoteClient
					.getCustomerCheckValue());
			preferencesBean.setProductsCheckValue(quoteClient
					.getProductsCheckValue());
			preferencesBean.setMarketplaceCheckValue(quoteClient
					.getMarketplaceCheckValue());
			preferencesBean.setFrequency(quoteClient.getFrequency());
			preferencesBean.setFrequencyDur(quoteClient.getFrequencyDur());
			preferencesBean.setWarehouse(quoteClient.getWarehouse());
			preferencesBean.setStartSyncDate(quoteClient.getStartSyncDate());
			preferencesBean.setoAuthToken(oAuthToken);
			preferencesBean.setSchedulerStarted(false);

			try {
				String json = objectMapper.writeValueAsString(preferencesBean);
				boolean dirCreate = new File(
						connectiorProperties.getFileDirectory() + divisionId)
						.mkdirs();
				String path = "";
				if (dirCreate) {
					path = connectiorProperties.getFileDirectory() + divisionId
							+ "/";
				}
				FileWriter createPreferences = null;
				createPreferences = new FileWriter(path
						+ ConnectorConstants.FILE_NAME_PREFERENCES);
				try {
					createPreferences.write(json);
					createPreferences.flush();
				} catch (IOException e) {
					LOGGER.error("Something wrong while write in preferences.json from createAndUpdatePreferncesFile of FileOperationServiceImpl.");
					throw new ExactException(
							ExactError.FERROR_WHILE_WRITE_PREFERENCES);
				} finally {
					createPreferences.close();
				}
			} catch (Exception e) {
				throw new ExactException(
						ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
			}

		} else {
			try {
				String readContent = "";
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new FileReader(
						connectiorProperties.getFileDirectory() + divisionId
								+ ConnectorConstants.FILE_NAME_PREFERENCES));
				try {
					String line = br.readLine();
					while (line != null) {
						sb.append(line);
						line = br.readLine();
					}
					readContent = sb.toString();
				} catch (Exception e) {
					LOGGER.error("Something wrong while read syncDetails.json from updateSyncDetailsFile of FileOperationServiceImpl.");
					throw new ExactException(
							ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
				} finally {
					br.close();
				}

				ObjectMapper objectMapper = new ObjectMapper();
				byte[] bytes = readContent.getBytes("UTF-8");
				PreferencesBean preferencesBean = objectMapper.readValue(bytes,
						PreferencesBean.class);
				if (preferencesBean.getCustomerCheckValue() != quoteClient
						.getCustomerCheckValue()) {
					preferencesBean.setCustomerCheckValue(quoteClient
							.getCustomerCheckValue());
				}
				if (preferencesBean.getSalesOrderCheckValue() != quoteClient
						.getSalesOrderCheckValue()) {
					preferencesBean.setSalesOrderCheckValue(quoteClient
							.getSalesOrderCheckValue());
				}
				if (preferencesBean.getProductsCheckValue() != quoteClient
						.getProductsCheckValue()) {
					preferencesBean.setProductsCheckValue(quoteClient
							.getProductsCheckValue());
				}
				if (preferencesBean.getMarketplaceCheckValue() != quoteClient
						.getMarketplaceCheckValue()) {
					preferencesBean.setMarketplaceCheckValue(quoteClient
							.getMarketplaceCheckValue());
				}
				if (preferencesBean.getWarehouse() != quoteClient
						.getWarehouse()) {
					preferencesBean.setWarehouse(quoteClient.getWarehouse());
				}
				if (preferencesBean.getFrequency() != quoteClient
						.getFrequency()) {
					preferencesBean.setFrequency(quoteClient.getFrequency());
				}
				if (preferencesBean.getFrequencyDur() != quoteClient
						.getFrequencyDur()) {
					preferencesBean.setFrequencyDur(quoteClient
							.getFrequencyDur());
				}
				if(quoteClient.isSchedulerStarted()){
					preferencesBean.setSchedulerStarted(true);
				}else{
					preferencesBean.setSchedulerStarted(false);
				}
				if(!preferencesBean.getoAuthToken().equals(oAuthToken)){
					preferencesBean.setoAuthToken(oAuthToken);	
				}
				String json = objectMapper.writeValueAsString(preferencesBean);
				FileWriter fileWriter = new FileWriter(
						connectiorProperties.getFileDirectory() + divisionId
								+ ConnectorConstants.FILE_NAME_PREFERENCES);
				try {
					fileWriter.write(json);
					fileWriter.flush();
				} catch (IOException e) {
					LOGGER.error("Something wrong while read syncDetails.json from updateSyncDetailsFile of FileOperationServiceImpl.");
					throw new ExactException(
							ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
				} finally {
					fileWriter.close();
				}
			} catch (Exception e) {
				throw new ExactException(
						ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
			}
		}
	}

	@Override
	public void createSyncDetails(int divisionId, Date syncStartDate)
			throws ExactException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		ObjectMapper objectMapper = new ObjectMapper();
		SyncDetailsJSON syncDetailsJSON = new SyncDetailsJSON();

		SyncItem syncItem = new SyncItem();
		syncItem.setExactToSd(sdf.format(syncStartDate));
		syncDetailsJSON.setSyncItem(syncItem);

		SyncCustomer syncCustomer = new SyncCustomer();
		syncCustomer.setSdToExact(sdf.format(syncStartDate));
		syncDetailsJSON.setSyncCustomer(syncCustomer);

		SyncDelivery syncDelivery = new SyncDelivery();
		syncDelivery.setSdToExact(sdf.format(syncStartDate));
		syncDetailsJSON.setSyncDelivery(syncDelivery);

		SyncSalesOrder syncSalesOrder = new SyncSalesOrder();
		syncSalesOrder.setSdToExact(sdf.format(syncStartDate));
		syncDetailsJSON.setSyncSalesOrder(syncSalesOrder);
		FileWriter fileWriter = null;
		try {
			String json = objectMapper.writeValueAsString(syncDetailsJSON);

			fileWriter = new FileWriter(connectiorProperties.getFileDirectory()
					+ divisionId + ConnectorConstants.FILE_NAME_SYNC_DETAILS);

			fileWriter.write(json);
			fileWriter.flush();
		} catch (IOException e) {
			LOGGER.error("Something wrong while read syncDetails.json from generateSyncDetailsFile of FileOperationServiceImpl.");
			throw new ExactException(
					ExactError.FILE_NAME_SYNC_DETAILS_NOT_FOUND);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				throw new ExactException(
						ExactError.FILE_NAME_SYNC_DETAILS_NOT_FOUND);
			}
		}
	}

	@Override
	public void updateSyncDetails(int divisionId) throws ExactException {

		File file = new File(connectiorProperties.getFileDirectory()
				+ divisionId + "/" + ConnectorConstants.FILE_NAME_SYNC_DETAILS);
		try {
			if (file.exists()) {
				String readContent = "";
				BufferedReader br = new BufferedReader(new FileReader(
						connectiorProperties.getFileDirectory() + divisionId
								+ ConnectorConstants.FILE_NAME_SYNC_DETAILS));
				StringBuilder sb = new StringBuilder();
				try {
					String line = br.readLine();
					while (line != null) {
						sb.append(line);
						line = br.readLine();
					}
					readContent = sb.toString();
				} catch (Exception e) {
					LOGGER.error("Something wrong while read syncDetails.json from updateSyncDetailsFile of FileOperationServiceImpl.");
					throw new ExactException(
							ExactError.FILE_NAME_SYNC_DETAILS_NOT_FOUND);
				} finally {
					br.close();
				}
				ObjectMapper objectMapper = new ObjectMapper();
				byte[] bytes = readContent.getBytes("UTF-8");
				SyncDetailsJSON syncDetailsJSON = objectMapper.readValue(bytes,
						SyncDetailsJSON.class);

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss");
				Date todaysDate = new Date();

				SyncItem syncItem = new SyncItem();
				syncItem.setExactToSd(sdf.format(todaysDate));
				syncDetailsJSON.setSyncItem(syncItem);

				SyncCustomer syncCustomer = new SyncCustomer();
				syncCustomer.setSdToExact(sdf.format(todaysDate));
				syncDetailsJSON.setSyncCustomer(syncCustomer);

				SyncDelivery syncDelivery = new SyncDelivery();
				syncDelivery.setSdToExact(sdf.format(todaysDate));
				syncDetailsJSON.setSyncDelivery(syncDelivery);

				SyncSalesOrder syncSalesOrder = new SyncSalesOrder();
				syncSalesOrder.setSdToExact(sdf.format(todaysDate));
				syncDetailsJSON.setSyncSalesOrder(syncSalesOrder);

				String json = objectMapper.writeValueAsString(syncDetailsJSON);
				FileWriter fileWriter = new FileWriter(
						connectiorProperties.getFileDirectory() + "/"
								+ divisionId + "/"
								+ ConnectorConstants.FILE_NAME_SYNC_DETAILS);
				try {
					fileWriter.write(json);
					fileWriter.flush();
				} catch (IOException e) {
					LOGGER.error("Something wrong while read syncDetails.json from updateSyncDetailsFile of FileOperationServiceImpl.");
					throw new ExactException(
							ExactError.FILE_NAME_SYNC_DETAILS_NOT_FOUND);
				} finally {
					fileWriter.close();
				}
			}
		} catch (Exception e) {
			throw new ExactException(
					ExactError.FILE_NAME_SYNC_DETAILS_NOT_FOUND);
		}

	}

	@Override
	public PreferencesBean getPreferencesDetail(int division)
			throws ExactException {
		File file = new File(connectiorProperties.getFileDirectory() + division
				+ "/" + ConnectorConstants.FILE_NAME_PREFERENCES);
		PreferencesBean preferencesBean = new PreferencesBean();
		BufferedReader br = null;
		try {
			if (file.exists()) {
				String readContent = "";
				br = new BufferedReader(new FileReader(
						connectiorProperties.getFileDirectory() + division
								+ ConnectorConstants.FILE_NAME_PREFERENCES));
				StringBuilder sb = new StringBuilder();
				try {
					String line = br.readLine();
					while (line != null) {
						sb.append(line);
						line = br.readLine();
					}
					readContent = sb.toString();
				} catch (Exception e) {
					LOGGER.error("Something wrong while read syncDetails.json from updateSyncDetailsFile of FileOperationServiceImpl.");
					throw new ExactException(
							ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
				} finally {
					br.close();
				}

				ObjectMapper objectMapper = new ObjectMapper();
				byte[] bytes = readContent.getBytes("UTF-8");
				preferencesBean = objectMapper.readValue(bytes,
						PreferencesBean.class);
			}
			return preferencesBean;
		} catch (Exception e) {
			throw new ExactException(ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
		}
	}

	@Override
	public SyncDetailsJSON getSyncDetail(int division) throws ExactException {
		File file = new File(connectiorProperties.getFileDirectory() + division
				+ "/" + ConnectorConstants.FILE_NAME_SYNC_DETAILS);
		SyncDetailsJSON syncDetails = new SyncDetailsJSON();
		BufferedReader br = null;
		try {
			if (file.exists()) {
				String readContent = "";
				br = new BufferedReader(new FileReader(
						connectiorProperties.getFileDirectory() + division
								+ ConnectorConstants.FILE_NAME_SYNC_DETAILS));
				StringBuilder sb = new StringBuilder();
				try {

					String line = br.readLine();
					while (line != null) {
						sb.append(line);
						line = br.readLine();
					}
					readContent = sb.toString();
				} catch (Exception e) {
					LOGGER.error("Something wrong while read syncDetails.json from updateSyncDetailsFile of FileOperationServiceImpl.");
					throw new ExactException(
							ExactError.FILE_NAME_SYNC_DETAILS_NOT_FOUND);
				} finally {
					br.close();
				}

				ObjectMapper objectMapper = new ObjectMapper();
				byte[] bytes = readContent.getBytes("UTF-8");
				syncDetails = objectMapper.readValue(bytes,
						SyncDetailsJSON.class);
			}
			return syncDetails;
		} catch (Exception e) {
			throw new ExactException(
					ExactError.FILE_NAME_SYNC_DETAILS_NOT_FOUND);
		}
	}

}
