/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.oauth2;

// import java.io.BufferedReader;
import java.io.IOException;
// import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * Request interceptor to just log request and response.
 */
public class RequestInterceptor implements ClientHttpRequestInterceptor {

	final static Logger LOGGER = LoggerFactory
			.getLogger(RequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body)
			throws IOException {
		// LOGGER.info("===========================request
		// begin================================================");
		// log.debug("===========================request
		// begin================================================");

		/*
		 * System.out.println("URI         : {}" + request.getURI());
		 * System.out.println("Method      : {}" + request.getMethod());
		 * System.out.println("Headers     : {}" + request.getHeaders());
		 * System.out.println("Request body: {}" + new String(body, "UTF-8"));
		 * System.out. println(
		 * "==========================request end================================================"
		 * );
		 */

	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		/*
		 * StringBuilder inputStringBuilder = new StringBuilder();
		 * BufferedReader bufferedReader = new BufferedReader(new
		 * InputStreamReader(response.getBody(), "UTF-8")); String line =
		 * bufferedReader.readLine(); while (line != null) {
		 * inputStringBuilder.append(line); inputStringBuilder.append('\n');
		 * line = bufferedReader.readLine(); } System.out. println(
		 * "============================response begin=========================================="
		 * ); System.out.println("Status code  : {}" +
		 * response.getStatusCode()); System.out.println("Status text  : {}" +
		 * response.getStatusText()); System.out.println("Headers      : {}" +
		 * response.getHeaders()); System.out.println("Response body: {}" +
		 * inputStringBuilder.toString()); System.out. println(
		 * "=======================response end================================================="
		 * );
		 */
	}

}