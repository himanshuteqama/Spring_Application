/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.error;

/**
 * All Seller Dynamics error codes.
 */
public enum SDError {

    SD_ERROR_UNKNOWN_ERROR(1000, "Someting went wrong."),
    SD_ERROR_MARKETPLACES_NOT_FOUND(1002, "No Marketplaces found, check details on Seller Dynamics.");
    
    private int errorCode;

    private String errorMessage;

    private SDError(int errorCode, String errorMessage) {
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
