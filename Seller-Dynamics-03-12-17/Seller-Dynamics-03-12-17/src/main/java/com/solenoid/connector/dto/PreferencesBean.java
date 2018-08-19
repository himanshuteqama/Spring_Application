/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.solenoid.connector.oauth2.bean.OAuthToken;

/**
 * PreferencesBean to hold the data from Preferences.json
 *
 */
public class PreferencesBean {

	private String encryptedLogin;
	private String retailerID;
	private String warehouse;

	private String frequency;
	private String frequencyDur;

	private int divisionId;
	private int salesOrderCheckValue;
	private int productsCheckValue;
	private int customerCheckValue;
	private int marketplaceCheckValue;
	private OAuthToken oAuthToken;
	private boolean isSchedulerStarted;
	

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date startSyncDate;

	/*
	 * public String getFetchType() { return fetchType; }
	 * 
	 * public void setFetchType(String fetchType) { this.fetchType = fetchType;
	 * }
	 */

	public String getRetailerID() {
		return retailerID;
	}

	public String getEncryptedLogin() {
		return encryptedLogin;
	}

	public void setEncryptedLogin(String encryptedLogin) {
		this.encryptedLogin = encryptedLogin;
	}

	public void setRetailerID(String retailerID) {
		this.retailerID = retailerID;
	}

	/*
	 * public String getSelectWay() { return selectWay; }
	 * 
	 * public void setSelectWay(String selectWay) { this.selectWay = selectWay;
	 * }
	 */

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public int getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}

	public int getSalesOrderCheckValue() {
		return salesOrderCheckValue;
	}

	public void setSalesOrderCheckValue(int salesOrderCheckValue) {
		this.salesOrderCheckValue = salesOrderCheckValue;
	}

	public int getProductsCheckValue() {
		return productsCheckValue;
	}

	public void setProductsCheckValue(int productsCheckValue) {
		this.productsCheckValue = productsCheckValue;
	}

	public int getCustomerCheckValue() {
		return customerCheckValue;
	}

	public void setCustomerCheckValue(int customerCheckValue) {
		this.customerCheckValue = customerCheckValue;
	}

	public int getMarketplaceCheckValue() {
		return marketplaceCheckValue;
	}

	public void setMarketplaceCheckValue(int marketplaceCheckValue) {
		this.marketplaceCheckValue = marketplaceCheckValue;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyDur() {
		return frequencyDur;
	}

	public void setFrequencyDur(String frequencyDur) {
		this.frequencyDur = frequencyDur;
	}

	public Date getStartSyncDate() {
		return startSyncDate;
	}

	public void setStartSyncDate(Date startSyncDate) {
		this.startSyncDate = startSyncDate;
	}

	public OAuthToken getoAuthToken() {
		return oAuthToken;
	}

	public void setoAuthToken(OAuthToken oAuthToken) {
		this.oAuthToken = oAuthToken;
	}
	
	public boolean isSchedulerStarted() {
		return isSchedulerStarted;
	}

	public void setSchedulerStarted(boolean isSchedulerStarted) {
		this.isSchedulerStarted = isSchedulerStarted;
	}

	@Override
	public String toString() {
		return "PreferencesBean [encryptedLogin=" + encryptedLogin
				+ ", retailerID=" + retailerID + ", warehouse=" + warehouse
				+ ", frequency=" + frequency + ", frequencyDur=" + frequencyDur
				+ ", divisionId=" + divisionId + ", salesOrderCheckValue="
				+ salesOrderCheckValue + ", productsCheckValue="
				+ productsCheckValue + ", customerCheckValue="
				+ customerCheckValue + ", marketplaceCheckValue="
				+ marketplaceCheckValue + ", oAuthToken=" + oAuthToken
				+ ", isSchedulerStarted=" + isSchedulerStarted
				+ ", startSyncDate=" + startSyncDate + "]";
	}


	
}
