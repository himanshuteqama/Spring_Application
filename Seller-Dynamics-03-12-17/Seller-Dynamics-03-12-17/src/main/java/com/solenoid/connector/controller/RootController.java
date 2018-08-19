/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Root controller to just redirect to quotation controller.
 */
@Controller
@RequestMapping("/")
public class RootController {

	@RequestMapping("/")
	public String getRoot() {
		return "redirect:/connector";
	}
}
