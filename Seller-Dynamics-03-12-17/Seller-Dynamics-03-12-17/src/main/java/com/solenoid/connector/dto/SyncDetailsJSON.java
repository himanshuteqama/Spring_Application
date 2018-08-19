/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.dto;

/**
 * DTO for SyncDetailsJSON, hold the data for sync operation, and details of syncDetails.json
 *
 */
public class SyncDetailsJSON {

	
	private SyncCustomer syncCustomer;

    private SyncSalesOrder syncSalesOrder;

    private SyncDelivery syncDelivery;

    private SyncItem syncItem;

	public SyncCustomer getSyncCustomer() {
		return syncCustomer;
	}

	public void setSyncCustomer(SyncCustomer syncCustomer) {
		this.syncCustomer = syncCustomer;
	}

	public SyncSalesOrder getSyncSalesOrder() {
		return syncSalesOrder;
	}

	public void setSyncSalesOrder(SyncSalesOrder syncSalesOrder) {
		this.syncSalesOrder = syncSalesOrder;
	}

	public SyncDelivery getSyncDelivery() {
		return syncDelivery;
	}

	public void setSyncDelivery(SyncDelivery syncDelivery) {
		this.syncDelivery = syncDelivery;
	}

	public SyncItem getSyncItem() {
		return syncItem;
	}

	public void setSyncItem(SyncItem syncItem) {
		this.syncItem = syncItem;
	}

	@Override
	public String toString() {
		return "SyncDetailsJSON [syncCustomer=" + syncCustomer
				+ ", syncSalesOrder=" + syncSalesOrder + ", syncDelivery="
				+ syncDelivery + ", syncItem=" + syncItem + "]";
	}

}
