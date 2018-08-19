/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.sd;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class SDApiClient extends WebServiceGatewaySupport {

	public Object callApi(String action, Object request) {
		return getWebServiceTemplate().marshalSendAndReceive(
				SDConstants.SD_API, request, new SoapActionCallback(action));
	}

}
