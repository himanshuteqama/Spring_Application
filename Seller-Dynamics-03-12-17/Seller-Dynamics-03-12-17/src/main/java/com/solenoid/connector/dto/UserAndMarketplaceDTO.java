/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.dto;

import java.util.List;

import com.solenoid.connector.sd.beans.RetailerMarketplace;

/**
 * DTO for User and Marketplace, to hold user and marketplaces details and to use it in EXACT API.
 */
public class UserAndMarketplaceDTO {
	List<RetailerMarketplace> marketPlaces;
	List<UserDTO> users;
	
	public List<RetailerMarketplace> getMarketPlaces() {
		return marketPlaces;
	}
	
	public void setMarketPlaces(List<RetailerMarketplace> marketPlaces) {
		this.marketPlaces = marketPlaces;
	}
	
	public List<UserDTO> getUsers() {
		return users;
	}
	
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return "UserAndMarketplaceDTO [marketPlaces=" + marketPlaces
				+ ", users=" + users + "]";
	}
}
