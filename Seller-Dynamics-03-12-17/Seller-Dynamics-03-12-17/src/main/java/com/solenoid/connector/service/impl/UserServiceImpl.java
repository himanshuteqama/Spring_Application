/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solenoid.connector.dto.UserDTO;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exact.response.ExactUserResponse;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.service.UserService;
import com.solenoid.connector.util.ExactURL;

/**
 * User service to manage User operation.
 */
@Service
public class UserServiceImpl extends ExactBaseService implements UserService{

	private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public List<UserDTO> gerUsersFromExact() throws ExactException {

		LOGGER.info("Get all Users.");
        ResponseEntity<ExactUserResponse> response = restTemplate.exchange(
                ExactURL.getURL() + "/users/Users?$select=UserID,FullName", HttpMethod.GET,
                this.getHttpEntity(null), ExactUserResponse.class);
        if (response == null || response.getBody() == null || response.getBody().getData().getResults() == null
                || response.getBody().getData().getResults().isEmpty()) {
            throw new ExactException(ExactError.EXACT_ERROR_USER_NOT_FOUND);
        }
        
        return response.getBody().getData().getResults();
	}
}
