/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.solenoid.connector.ConnectorConstants;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.dto.SalesOrderDTO;
import com.solenoid.connector.dto.SalesOrderLines;
import com.solenoid.connector.dto.SyncDetailsJSON;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exact.response.ExactResponseSalesOrder;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.sd.SDApiClient;
import com.solenoid.connector.sd.beans.CustomerOrder;
import com.solenoid.connector.sd.beans.GetCustomerOrders;
import com.solenoid.connector.sd.beans.GetCustomerOrdersResponse;
import com.solenoid.connector.sd.beans.OrderType;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.SalesOrderService;
import com.solenoid.connector.util.ExactURL;

@Service
@Qualifier("sdSalesOrderService")
public class SdSalesOrderServiceImpl extends ExactBaseService implements SalesOrderService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(SdSalesOrderServiceImpl.class);

	@Autowired
	private SDApiClient sdApiClient;

	@Autowired
	private FileOperationService fileOperationService;
	
//	@Autowired
//	private CustomerService customerService;

    @Override
    public ExactResponseSalesOrder getSalesOrders() throws ExactException {
        LOGGER.info("Getting Sales Orders");
        ResponseEntity<ExactResponseSalesOrder> result = restTemplate.exchange(
                ExactURL.getURL()
                        + "/salesorder/SalesOrders?$select=OrderID,Currency,Description,DeliverTo,DeliverToName,"
                        + "DeliveryAddress,DeliveryDate,DeliveryStatus,InvoiceStatus,InvoiceTo,InvoiceToName,OrderDate,OrderedBy,OrderedByName,OrderNumber,Remarks,Status,StatusDescription,WarehouseDescription",
                HttpMethod.GET, this.getHttpEntity(null), ExactResponseSalesOrder.class);

        return result.getBody();
    }

    @Override
    public ResponseEntity<ExactResponseSalesOrder> create(String description, String warehouseID, String orderedBy,
            List<SalesOrderLines> salesOrderLines) throws ExactException {
        SalesOrderDTO salesOrderDTO = new SalesOrderDTO();
        salesOrderDTO.setDescription(description);
        salesOrderDTO.setOrderedBy(orderedBy);
        salesOrderDTO.setWarehouseID(warehouseID);
        salesOrderDTO.setSalesOrderLines(salesOrderLines);
        LOGGER.info("Creating sales order to EXACT side.");
        ResponseEntity<ExactResponseSalesOrder> postResponse = restTemplate.exchange(
                ExactURL.getURL() + "/salesorder/SalesOrders", HttpMethod.POST, this.getHttpEntity(salesOrderDTO),
                ExactResponseSalesOrder.class);
		if(postResponse==null){
			throw new ExactException(ExactError.EXACT_ERROR_SALES_NOT_CREATED);
		}
        return postResponse;
    }

	@Override
	public ArrayList<CustomerOrder> getSalesOrderFromSD(int divisionId)
			throws ExactException, ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date todaysDate = new Date();
		// String dateInString ="2013-07-25T00:00:00";
		// String dateInString1 = "2018-07-25T00:00:00";

		SyncDetailsJSON syncDetails = new SyncDetailsJSON();
		syncDetails = fileOperationService.getSyncDetail(divisionId);
		PreferencesBean preferencesBean = fileOperationService
				.getPreferencesDetail(divisionId);

		String encryptedLogin = preferencesBean.getEncryptedLogin();
		String retailerId = preferencesBean.getRetailerID();

		GetCustomerOrders request = new GetCustomerOrders();
		request.setEncryptedLogin(encryptedLogin);
		request.setRetailerId(retailerId);
		request.setOrderType(OrderType.HISTORIC);
		request.setPageNumber(1);

		Date fromDate = sdf.parse(syncDetails.getSyncSalesOrder()
				.getSdToExact());
		Date toDate = sdf.parse(sdf.format(todaysDate));

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(fromDate);
		XMLGregorianCalendar gregorianFromDate;
		try {
			gregorianFromDate = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
			request.setFromDate(gregorianFromDate);
			c.setTime(toDate);
			XMLGregorianCalendar gregorianToDate = DatatypeFactory
					.newInstance().newXMLGregorianCalendar(c);
			request.setToDate(gregorianToDate);
		} catch (DatatypeConfigurationException e) {
			LOGGER.error("Something wrong while convert date in XMLGregorianCalendar format from getSalesOrderFromSD of SdSalesOrderserviceImpl");
		}
		LOGGER.info("GetCustomerOrders from Seller Dynamics.");
		ArrayList<CustomerOrder> totalCustomerOrders = new ArrayList<CustomerOrder>();
		GetCustomerOrdersResponse apiResponse = (GetCustomerOrdersResponse) this.sdApiClient
				.callApi(ConnectorConstants.SD_URL_GETCUSTOMERORDERS, request);

		totalCustomerOrders
				.addAll((Collection<? extends CustomerOrder>) apiResponse
						.getGetCustomerOrdersResult().getCustomers()
						.getCustomerOrder());
		int totalPages = apiResponse.getGetCustomerOrdersResult()
				.getPagination().getRecordsAffected() / 50;

		int page = 1;
		while (page <= totalPages) {
			page = page + 1;
			request.setPageNumber(page);
			GetCustomerOrdersResponse apiResponseForNextOrders = (GetCustomerOrdersResponse) this.sdApiClient
					.callApi(ConnectorConstants.SD_URL_GETCUSTOMERORDERS,
							request);
			totalCustomerOrders
					.addAll((Collection<? extends CustomerOrder>) apiResponseForNextOrders
							.getGetCustomerOrdersResult().getCustomers()
							.getCustomerOrder());
		}
		return totalCustomerOrders;
	}
}
