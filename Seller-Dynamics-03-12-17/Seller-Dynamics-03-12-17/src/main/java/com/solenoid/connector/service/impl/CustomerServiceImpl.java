/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */
package com.solenoid.connector.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.solenoid.connector.ConnectorConstants;
import com.solenoid.connector.dto.CustomerDTO;
import com.solenoid.connector.dto.ItemDTO;
import com.solenoid.connector.dto.ItemResponseDTO;
import com.solenoid.connector.dto.ItemWarehouseDTO;
import com.solenoid.connector.dto.LinkItemToWarehouseDTO;
import com.solenoid.connector.dto.SalesOrderLines;
import com.solenoid.connector.dto.VatResponseDTO;
import com.solenoid.connector.exact.response.ExactCustomerPostResponse;
import com.solenoid.connector.exact.response.ExactLinkItemToWarehousePostResponse;
import com.solenoid.connector.exact.response.ExactResponseSalesOrder;
import com.solenoid.connector.exact.response.GetCustomer;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.beans.CustomerOrder;
import com.solenoid.connector.service.CustomerService;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.ItemService;
import com.solenoid.connector.service.ItemWarehouseService;
import com.solenoid.connector.service.SalesOrderService;
import com.solenoid.connector.service.VatService;
import com.solenoid.connector.service.WarehouseService;
import com.solenoid.connector.util.ExactURL;

/**
 * Customer service to manage customer related operations.
 */
@Service
@Qualifier("customerService")
public class CustomerServiceImpl extends ExactBaseService implements CustomerService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(CustomerServiceImpl.class);

	@Autowired
	private ItemService itemService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private SalesOrderService salesOrderService;
	
	@Autowired
	private VatService vatService;
	
	@Autowired
	private FileOperationService fileOperationService;
	
	@Autowired
	private ItemWarehouseService itemWarehouseService;

	@Override
	public CustomerDTO createCustomerAndSalesOrder(
			ArrayList<CustomerOrder> getCustomerOrdersResponse, String warehouse)
			throws ExactException {
		CustomerDTO customerDTO = new CustomerDTO();
		ResponseEntity<ExactCustomerPostResponse> postResponse = null;
		CustomerDTO customer = new CustomerDTO();

		// Get country and their ISO codes, Reason is EXACT get country codes only to set for Customer
		Map<String, String> countries = new HashMap<>();
		for (String iso : Locale.getISOCountries()) {
			Locale l = new Locale("", iso);
			countries.put(l.getDisplayCountry(), iso);
		} // get country code ends

		for (int i = 0; i < getCustomerOrdersResponse.size(); i++) {
			
			if(getCustomerOrdersResponse.get(i).getSKU()!=null){
				customerDTO.setAccountName(getCustomerOrdersResponse.get(i)
						.getCustomerName());
				customerDTO.setEmail(getCustomerOrdersResponse.get(i)
						.getCustomerEmail());
				customerDTO.setPhone(getCustomerOrdersResponse.get(i)
						.getCustomerPhone());
				customerDTO.setAddressLine1(getCustomerOrdersResponse.get(i)
						.getShippingAddress1());
				customerDTO.setAddressLine2(getCustomerOrdersResponse.get(i)
						.getShippingAddress2());
				customerDTO.setAddressLine3(getCustomerOrdersResponse.get(i)
						.getShippingAddress3());
				customerDTO.setCity(getCustomerOrdersResponse.get(i)
						.getShippingAddressTown());
				customerDTO.setPostCode(getCustomerOrdersResponse.get(i)
						.getShippingAddressPostZIPCode());
				customerDTO.setCountry(getCustomerOrdersResponse.get(i)
						.getShippingAddressCountry());
				customerDTO.setStatus(ConnectorConstants.CUSTOMERSTATUS);
	
				String countryCode = null;
				countryCode = countries.get(getCustomerOrdersResponse.get(i)
						.getShippingAddressCountry());
				if (countryCode != null)
					customerDTO.setCountry(countryCode);
	
				String customerName = customerDTO.getAccountName();
				ResponseEntity<GetCustomer> response = restTemplate
						.exchange(ExactURL.getURL()
								+ "/crm/Accounts?$select=ID,Name&$filter=Name eq '"
								+ customerName + "'", HttpMethod.GET, this.getHttpEntity(null), GetCustomer.class);
				GetCustomer checkExistingCustomer = response.getBody();
				if (checkExistingCustomer.getD().getResults().size() == 0) {
					LOGGER.info("Creating new CUSTOMER");
					postResponse = restTemplate.exchange(ExactURL.getURL()
							+ "/crm/Accounts", HttpMethod.POST, this.getHttpEntity(customerDTO),
							ExactCustomerPostResponse.class);
					customer.setCustomerID(postResponse.getBody().getCustomerDTO()
							.getCustomerID());
				}
				
				/*PreferencesBean pb = fileOperationService.getPreferencesDetail(59221);
				OAuthToken oAuthToken = new OAuthToken();
		        oAuthToken.setAccessToken(pb.getoAuthToken().getAccessToken());
		        oAuthToken.setRefreshToken(pb.getoAuthToken().getRefreshToken());
		        RequestInfoHolder.setOAuthToken(oAuthToken);*/
		        
				//code for item creation
				ItemDTO itemDTO = new ItemDTO();
				itemDTO.setItemCode(getCustomerOrdersResponse.get(i).getSKU());
				itemDTO.setDescription(getCustomerOrdersResponse.get(i).getItemTitle());
				ItemResponseDTO itemDTOtoCreate = itemService.create(itemDTO);
				
				List<ItemWarehouseDTO> checkMappedWarehouse = itemWarehouseService.checkMapping(itemDTOtoCreate.getItem());
				//Code to check item is mapped with warehouse or not
				boolean checkItemWarehousemapping = false;
				for(int j=0;j<checkMappedWarehouse.size();j++){
					if(checkMappedWarehouse.get(j).getWarehouse().equalsIgnoreCase(warehouse))
						checkItemWarehousemapping = true;
				}
				//code for link warehouse with item
				if(!checkItemWarehousemapping){
					LinkItemToWarehouseDTO linkItemToWarehouse = new LinkItemToWarehouseDTO();
					linkItemToWarehouse.setItem(itemDTOtoCreate.getItem());
					linkItemToWarehouse.setWarehouse(warehouse);
					ResponseEntity<ExactLinkItemToWarehousePostResponse> linkItemToWarehouseCreate = warehouseService
							.linkItemAndWarehosue(linkItemToWarehouse);
					LOGGER.info("Item and warehouse link sucessfully: "+linkItemToWarehouseCreate.getStatusCodeValue());
				}
				
				String orderedBy = "";
				if (postResponse != null) {
					orderedBy = postResponse.getBody().getCustomerDTO()
							.getCustomerID();
				} else {
					orderedBy = checkExistingCustomer.getD().getResults().get(0)
							.getID();
				}
				// Code for create and get VAT 
				String vatCode = getCustomerOrdersResponse.get(i).getTaxRate();
				String vatDescription = "SD "+vatCode;
				VatResponseDTO getVat =  vatService.createVat(vatCode, vatDescription);
				
				//code for sales order creation on EXACT side.
				SalesOrderLines salesOrderLines = new SalesOrderLines();
				salesOrderLines.setItem(itemDTOtoCreate.getItem());
				salesOrderLines.setVatCode(getVat.getCode());
				String description = "SD " + itemDTOtoCreate.getItemCode();
				String warehouseID = warehouse;
				List<SalesOrderLines> orderLines = new ArrayList<SalesOrderLines>();
				orderLines.add(salesOrderLines);
				ResponseEntity<ExactResponseSalesOrder> salesOrder = salesOrderService
						.create(description, warehouseID, orderedBy, orderLines);
				LOGGER.info("Sales order created with name: "+salesOrder.getBody().getResults().getDescription());
			}
		}
		return customer;
	}

}
