/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 

package com.solenoid.connector.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solenoid.connector.ConnectorConstants;
import com.solenoid.connector.contant.QuartzConstants;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.dto.SyncDetailsJSON;
import com.solenoid.connector.exact.response.CurrentDivision;
import com.solenoid.connector.exact.response.CurrentDivisionResponse;
import com.solenoid.connector.exact.response.Warehouse;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.jobs.JobDetails;
import com.solenoid.connector.jobs.scheduler.SyncJobScheduler;
import com.solenoid.connector.oauth2.bean.OAuthToken;
import com.solenoid.connector.sd.beans.GetRetailerMarketplacesResponse;
import com.solenoid.connector.service.ItemService;
import com.solenoid.connector.service.JSONFileService;
import com.solenoid.connector.service.MarketplacesService;
import com.solenoid.connector.service.SalesOrderService;
import com.solenoid.connector.service.WarehouseService;
import com.solenoid.connector.util.ExactURL;
import com.solenoid.connector.util.RequestInfoHolder;

*//**
 * Controller for Connector application
 *
 *//*
@Controller
@RequestMapping("/connector")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZConnectorController2 {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZConnectorController2.class);

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private JSONFileService jsonFileService;

    @Autowired
    private MarketplacesService marketplacesService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SyncJobScheduler syncJobScheduler;

    @SuppressWarnings("unused")
    @RequestMapping(method = RequestMethod.GET)
    public String get(Map<String, Object> model) throws org.json.simple.parser.ParseException, ExactException,
            JSONException, JsonParseException, JsonMappingException, IOException {
        model.put("message", "Connector Seller Dynamics");
        CurrentDivisionResponse response = restTemplate.getForObject(
                ExactURL.BASE_API_URL + "current/Me?$select=CurrentDivision,FullName", CurrentDivisionResponse.class);
        if (response != null && response.getDivision() != null && response.getDivision().getResults() != null
                && !response.getDivision().getResults().isEmpty()) {
            CurrentDivision division = response.getDivision().getResults().get(0);
            RequestInfoHolder.setDivisionId(division.getDivision());

            List<Warehouse> ware = warehouseService.getAll();

            model.put("division", division.getDivision());
            model.put("fullName", division.getFullName());
            model.put("ware", ware);
            LOGGER.info("" + restTemplate.getAccessToken());

            JSONParser parser = new JSONParser();
            org.json.simple.JSONObject jsonObject = null;

            try {
                Object obj1 = parser.parse(new FileReader(ConnectorConstants.DIR_PATH_PREFERENCES
                        + division.getDivision() + ConnectorConstants.FILE_NAME_PREFERENCES));
                jsonObject = (org.json.simple.JSONObject) obj1;
                LOGGER.info("PreferencesBean", jsonObject);
            } catch (Exception e) {
                LOGGER.error("Preferences file not found, provide credentials.");
                return "provisioning";
            }
        }
        String dateSalesSDToExact = "";
        String dateItemSDToExact = "";
        String dateItemExactToSD = "";
        String dateCustomerSDToExact = "";
        String dateDeliverySDToExact = "";

        int divisionId = response.getDivision().getResults().get(0).getDivision();
        File file = new File(
                ConnectorConstants.DIR_PATH_PREFERENCES + divisionId + "/" + ConnectorConstants.FILE_NAME_SYNC_DETAILS);
        String readContent = "";
        try {
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new FileReader(
                    ConnectorConstants.DIR_PATH_PREFERENCES + divisionId + ConnectorConstants.FILE_NAME_SYNC_DETAILS));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            readContent = sb.toString();
        } catch (Exception e) {
            LOGGER.error("Somethig wrong to read file SyncDetails.json");
        }
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = readContent.getBytes("UTF-8");
            SyncDetailsJSON syncDetailsJSON = objectMapper.readValue(bytes, SyncDetailsJSON.class);

            model.put("dateItemExactToSD", syncDetailsJSON.getSyncItem().getExactToSd());// dateItemExactToSD);
            model.put("dateSalesSDToExact", syncDetailsJSON.getSyncSalesOrder().getSdToExact());// dateSalesSDToExact);
            model.put("dateCustomerSDToExact", syncDetailsJSON.getSyncCustomer().getSdToExact());// dateCustomerSDToExact);
            model.put("dateDeliverySDToExact", syncDetailsJSON.getSyncDelivery().getSdToExact());// dateDeliverySDToExact);
            model.put("lastSyncDate", syncDetailsJSON.getSyncItem().getExactToSd());
        } catch (Exception e1) {
            LOGGER.error("Something went wrong to read JSON in get() method of ZConnectorController.");
        }
        return "dashboard";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody String saveCredentials(@RequestBody PreferencesBean quoteClient, Map<String, Object> model)
            throws org.json.simple.parser.ParseException, ExactException, ParseException {
        model.put("message", "Connector Seller Dynamics");
        CurrentDivisionResponse response = restTemplate.getForObject(
                ExactURL.BASE_API_URL + "current/Me?$select=CurrentDivision,FullName", CurrentDivisionResponse.class);
        if (response != null && response.getDivision() != null && response.getDivision().getResults() != null
                && !response.getDivision().getResults().isEmpty()) {
            CurrentDivision division = response.getDivision().getResults().get(0);
            RequestInfoHolder.setDivisionId(division.getDivision());
            List<Warehouse> ware = warehouseService.getAll();
            model.put("division", division.getDivision());
            model.put("fullName", division.getFullName());
            model.put("ware", ware);
            LOGGER.info("" + restTemplate.getAccessToken());

            try {
                File preferencesFile = new File(ConnectorConstants.DIR_PATH_PREFERENCES + division.getDivision()
                        + ConnectorConstants.FILE_NAME_PREFERENCES);
                if (!preferencesFile.exists()) {
                    jsonFileService.generatePreferncesFile(quoteClient, division.getDivision());
                }
                File syncDetailsfile = new File(ConnectorConstants.DIR_PATH_PREFERENCES + division.getDivision()
                        + ConnectorConstants.FILE_NAME_SYNC_DETAILS);
                if (!syncDetailsfile.exists()) {
                    jsonFileService.generateSyncDetailsFile(division.getDivision(), quoteClient.getSyncStartDate());
                }

            } catch (Exception e) {
                LOGGER.error("Something went wrong while generating JSON file.");
            }
        }
        return "true";
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public @ResponseBody String syncDetails(@RequestBody PreferencesBean quoteClient, String raisedBy, Model model)
            throws ParseException, ExactException, JSONException, org.json.simple.parser.ParseException,
            JsonParseException, JsonMappingException, IOException {

        RequestInfoHolder.setDivisionId(quoteClient.getDivisionId());

        String encryptedLogin = "";
        String retailerId = "";
        String frequencyDur = "";
        String warehouse = "";
        int customerCheckValue = 0;
        int marketplaceCheckValue = 0;
        int productsCheckValue = 0;
        int salesOrderCheckValue = 0;
        int frequency;

        JSONParser parser1 = new JSONParser();
        String readContent = "";
        try {
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new FileReader(ConnectorConstants.DIR_PATH_PREFERENCES
                    + quoteClient.getDivisionId() + ConnectorConstants.FILE_NAME_PREFERENCES));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            readContent = sb.toString();
        } catch (Exception e) {
            LOGGER.error(
                    "Something wrong while read preferences.json file in syncDetail method of ZConnectorController.");
        }
        JSONObject jobj = new JSONObject(readContent);
        salesOrderCheckValue = jobj.getInt("salesOrderCheckValue");
        productsCheckValue = jobj.getInt("productsCheckValue");
        marketplaceCheckValue = jobj.getInt("marketplaceCheckValue");
        warehouse = jobj.getString("warehouse");

        if (salesOrderCheckValue == 1) {
            String type = "salesOrder";
            String way = "sdToExact";
            salesOrderService.salesOrderSync(way, quoteClient.getDivisionId(), type, warehouse);
        }
        if (productsCheckValue == 1) {
            String type = "itemStock";
            String way = "exactToSd";
            itemService.itemSync(way, quoteClient.getDivisionId(), type);
        }
        if (marketplaceCheckValue == 1) {
            // todo for MarketPlaces related change
            GetRetailerMarketplacesResponse marketPlaces = marketplacesService
                    .getMarketplacesFromSD(quoteClient.getDivisionId());
        }

        model.addAttribute("division", quoteClient.getDivisionId());
        model.addAttribute("fullName", raisedBy);
        return "true";
    }

    @RequestMapping(value = "/getPreferences", method = RequestMethod.POST)
    public @ResponseBody PreferencesBean getPreferencesDetail(@RequestBody PreferencesBean quoteClient)
            throws org.json.simple.parser.ParseException, ExactException, JSONException, ParseException {
        LOGGER.info("" + restTemplate.getAccessToken());

        JSONObject readJSON = jsonFileService.getPreferencesDetail(quoteClient.getDivisionId());
        PreferencesBean prefBean = new PreferencesBean();
        prefBean.setCustomerCheckValue(readJSON.getInt("customerCheckValue"));
        prefBean.setProductsCheckValue(readJSON.getInt("productsCheckValue"));
        prefBean.setSalesOrderCheckValue(readJSON.getInt("salesOrderCheckValue"));
        prefBean.setMarketplaceCheckValue(readJSON.getInt("marketplaceCheckValue"));
        prefBean.setWarehouse(readJSON.getString("warehouse"));
        prefBean.setFrequency(readJSON.getString("frequency"));
        prefBean.setFrequencyDur(readJSON.getString("frequencyDur"));

        String dt = readJSON.getString("startSyncDate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date startSync = sdf.parse(dt);
        sdf.format(startSync);
        prefBean.setSyncStartDate(startSync);

        LOGGER.info("Get details of preferences.json for edit purpose.");
        return prefBean;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody String editCredentials(@RequestBody PreferencesBean quoteClient, Map<String, Object> model)
            throws org.json.simple.parser.ParseException, ExactException {
        model.put("message", "Connector Seller Dynamics");
        LOGGER.info("" + restTemplate.getAccessToken());
        try {
            jsonFileService.generatePreferncesFile(quoteClient, quoteClient.getDivisionId());
        } catch (Exception e) {
            LOGGER.error("Something went wrong while generating JSON file.");
        }
        return "true";
    }


}*/