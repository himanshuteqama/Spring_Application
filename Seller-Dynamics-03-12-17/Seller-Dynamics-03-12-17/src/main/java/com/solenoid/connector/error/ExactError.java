/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.error;

/**
 * All exact error codes.
 */
public enum ExactError {

    EXACT_ERROR_UNKNOWN_ERROR(1000, "Someting went wrong."),
    EXACT_ERROR_ITEM_NOT_FOUND(1001, "Item not found to Sync."),
    EXACT_ERROR_ACCOUNT_NOT_FOUND(1002, "Order by account not found. Please enter valid number."),
    EXACT_ERROR_PRODUCT_CODE_INVALID(1003, "Invalid product code."),
    EXACT_ERROR_QUOTATION_NOT_CREATED(1004, "Failed to create quotation."),
    EXACT_ERROR_SHOP_ORDER_NOT_CREATED(1005, "Failed to create shop order."),
    EXACT_ERROR_DIVISION_NOT_FOUND(1006, "Invalid division, please login."),
    EXACT_ERROR_PURCHASE_ORDER_DOES_NOT_EXISTS(1007, "Purchase Order doesn't exits. Please create Purchase Order."),
    EXACT_ERROR_PURCHASE_ORDER_LINE_NOT_FOUND(1008, "Line items doesn't exists for selected Purchase Order."),
    EXACT_ERROR_PURCHASE_ORDER_LINE_REQUIRED_ITEM_NOT_FOUND(1009,
            "Required item in selected purchase order does not present."),
    EXACT_ERROR_SALES_ORDER_LINE_QUANTITY_INVALID(1010,
            "Quantity Delivered is less than one or Sales Order is already delivered."),
    EXACT_ERROR_SALES_ORDER_NOT_FOUND(1011, "Sales Order not found with this order number."),
    EXACT_ERROR_SALES_ORDER_LINE_NOT_FOUND(1012, "Sales Order Line not found."),
    EXACT_ERROR_SALES_ORDER_DELIVERY_FAILED(1013,
            "Failed to deliver sales order. Please check whether it is already delivered."),
    EXACT_ERROR_EXCEL_PARSING_FAILED(1014, "Failed to parse excel."),
    EXACT_ERROR_SHIPPPING_METHOD_NOT_FOUND(1015, "Shipping method not found."),
    EXACT_ERROR_WAREHOUSE_NOT_FOUND(1016, "No warehouse found, please create warehouse and try again."),
    EXACT_ERROR_ACCESS_TOKEN_NOT_FOUND(1017, "Invalid authorization details to call EXACT APIs."),
    EXACT_ERROR_WAREHOUSE_LINKING_WITH_ITEM_FAILED(1018, "Could not link warehouse with item."),
    FILE_NAME_PREFERENCES_NOT_FOUND(1019, "Preferences.json file not found, please try after refresh."),
    FERROR_WHILE_WRITE_PREFERENCES(1020, "Occour error while writting in Preferences.json."),
    FILE_NAME_SYNC_DETAILS_NOT_FOUND(1021, "SyncDetails.json file not found."),
    EXACT_ERROR_PROBLEM_WITH_SALES_ORDER_SYNC(1022, "Something goes wrong while sync SalesOrder, plese try again."),
    EXACT_ERROR_PROBLEM_WITH_PRODUCT_SYNC(1023, "Something goes wrong while sync Products, plese try again."),
    PROBLEM_WHILE_CREATE_OR_UPDATE_PREFERENCES(1024, "Something goes wrong while create or update preferences, plese try again."),
    EXACT_ERROR_SALES_ORDER_NOT_AVAILABLE(1025, "No sales order found, please check start sync date."),
    EXACT_ERROR_SALES_NOT_CREATED(1026, "Something goes wrong while create sales order, please try again."),
    EXACT_ERROR_USER_NOT_FOUND(1027, "No User found, contact Admin.");
    
    private int errorCode;

    private String errorMessage;

    private ExactError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return this.errorCode + " : " + this.errorMessage;
    }
}
