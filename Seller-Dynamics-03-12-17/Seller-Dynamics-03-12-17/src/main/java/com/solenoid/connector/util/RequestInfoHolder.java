/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.util;

import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.oauth2.bean.OAuthToken;

/**
 * Request info holder to hold RequestInfo POJO.
 */
public class RequestInfoHolder {

    private static final ThreadLocal<RequestInfo> REQUESTINFOFOLDER = new ThreadLocal<RequestInfo>();

    public static void setDivisionId(int divisionId) {
        RequestInfo requestInfo = REQUESTINFOFOLDER.get();
        if (requestInfo == null) {
            requestInfo = new RequestInfo();
        }
        requestInfo.setDivisionId(divisionId);
        REQUESTINFOFOLDER.set(requestInfo);
    }

    public static int getDivisionId() throws ExactException {
        int divisionId = REQUESTINFOFOLDER.get() != null ? REQUESTINFOFOLDER.get().getDivisionId() : 0;
        if (divisionId <= 0) {
            throw new ExactException(ExactError.EXACT_ERROR_DIVISION_NOT_FOUND);
        }
        return divisionId;
    }

    public static OAuthToken getOAuthToken() throws ExactException {
        OAuthToken accessToken = REQUESTINFOFOLDER.get() != null ? REQUESTINFOFOLDER.get().getoAuthToken() : null;
        if (accessToken == null) {
            throw new ExactException(ExactError.EXACT_ERROR_ACCESS_TOKEN_NOT_FOUND);
        }
        return accessToken;
    }

    public static void setOAuthToken(OAuthToken oAuthToken) {
        RequestInfo requestInfo = REQUESTINFOFOLDER.get();
        if (requestInfo == null) {
            requestInfo = new RequestInfo();
        }
        requestInfo.setoAuthToken(oAuthToken);
        REQUESTINFOFOLDER.set(requestInfo);
    }
}
