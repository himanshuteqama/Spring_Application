/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.util;

import com.solenoid.connector.oauth2.bean.OAuthToken;

/**
 * Request info POJO.
 */
public class RequestInfo {
    private int divisionId;

    private OAuthToken oAuthToken;

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public OAuthToken getoAuthToken() {
        return oAuthToken;
    }

    public void setoAuthToken(OAuthToken oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

}
