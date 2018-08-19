package com.solenoid.connector.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.util.RequestInfoHolder;

public class ExactBaseService {

    @Autowired
    @Qualifier("exactRestTemplate")
    protected RestTemplate restTemplate;

    protected HttpEntity<Object> getHttpEntity(Object object) throws ExactException {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        String accessToken = RequestInfoHolder.getOAuthToken().getAccessToken();
        headerMap.put("Authorization", Arrays.asList("Bearer " + accessToken));
        headerMap.put("Content-Type", Arrays.asList("application/json"));
        headerMap.put("Accept", Arrays.asList("application/json"));
        HttpEntity<Object> httpEntity = null;
        if (object != null) {
            httpEntity = new HttpEntity<>(object, headerMap);
        } else {
            httpEntity = new HttpEntity<>(null, headerMap);
        }
        return httpEntity;
    }
}
