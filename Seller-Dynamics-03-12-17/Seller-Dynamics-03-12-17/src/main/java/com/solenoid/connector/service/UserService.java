/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.service;

import java.util.List;

import com.solenoid.connector.dto.UserDTO;
import com.solenoid.connector.exception.ExactException;

/**
 * User service to manage User operation.
 */

public interface UserService {

	public List<UserDTO> gerUsersFromExact() throws ExactException;
}
