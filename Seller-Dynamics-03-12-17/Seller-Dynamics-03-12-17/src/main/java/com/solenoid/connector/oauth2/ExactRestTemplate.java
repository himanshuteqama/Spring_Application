/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Customized Exact REST template to for Exact API.
 */
public class ExactRestTemplate extends RestTemplate {

	@Override
	public void setMessageConverters(
			List<HttpMessageConverter<?>> messageConverters) {

		/*
		 * List<HttpMessageConverter<?>> messageConvertersTemp =
		 * this.getMessageConverters(); Jaxb2RootElementHttpMessageConverter
		 * jaxbMessageConverter = new Jaxb2RootElementHttpMessageConverter();
		 * List<MediaType> mediaTypes = new ArrayList<MediaType>();
		 * mediaTypes.add(MediaType.TEXT_HTML);
		 * jaxbMessageConverter.setSupportedMediaTypes(mediaTypes);
		 * messageConvertersTemp.add(jaxbMessageConverter);
		 */
		// messageConverters.add(new
		// ExactFormOAuth2AccessTokenMessageConverter());
		super.setMessageConverters(messageConverters);
	}

	@Override
	public void setInterceptors(List<ClientHttpRequestInterceptor> interceptors) {
		List<ClientHttpRequestInterceptor> interceptorss = this
				.getInterceptors();
		interceptorss.add(new RequestInterceptor());
		super.setInterceptors(interceptorss);
	}

	public ExactRestTemplate() {
		this.setInterceptors(null);
	}

	@Override
	protected <T> T doExecute(URI url, HttpMethod method,
			RequestCallback requestCallback,
			ResponseExtractor<T> responseExtractor) throws RestClientException {

		Assert.notNull(url, "'url' must not be null");
		Assert.notNull(method, "'method' must not be null");
		ClientHttpResponse response = null;
		try {
			ClientHttpRequest request = createRequest(url, method);
			if (requestCallback != null) {
				requestCallback.doWithRequest(request);
			}
			response = request.execute();
			// response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			handleResponse(url, method, response);
			if (responseExtractor != null) {
				return responseExtractor.extractData(response);
			} else {
				return null;
			}
		} catch (IOException ex) {
			throw new ResourceAccessException("I/O error on " + method.name()
					+ " request for \"" + url + "\": " + ex.getMessage(), ex);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
}
