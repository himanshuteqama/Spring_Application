/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.solenoid.connector.ConnectorConstants;
import com.solenoid.connector.config.ConnectorProperties;
import com.solenoid.connector.contant.QuartzConstants;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.dto.SyncDetailsJSON;
import com.solenoid.connector.dto.UserAndMarketplaceDTO;
import com.solenoid.connector.dto.UserDTO;
import com.solenoid.connector.error.ExactError;
import com.solenoid.connector.exact.response.CurrentDivision;
import com.solenoid.connector.exact.response.CurrentDivisionResponse;
import com.solenoid.connector.exact.response.Warehouse;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.exception.SDException;
import com.solenoid.connector.jobs.scheduler.SyncJobScheduler;
import com.solenoid.connector.oauth2.bean.OAuthToken;
import com.solenoid.connector.sd.beans.RetailerMarketplace;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.ItemSyncService;
import com.solenoid.connector.service.JobService;
import com.solenoid.connector.service.MarketplacesService;
import com.solenoid.connector.service.SalesOrderSyncService;
import com.solenoid.connector.service.UserService;
import com.solenoid.connector.service.WarehouseService;
import com.solenoid.connector.util.ExactURL;
import com.solenoid.connector.util.RequestInfoHolder;

/**
 * Controller for Connector application
 *
 */
@Controller
@RequestMapping("/connector")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZConnectorController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ZConnectorController.class);

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private FileOperationService fileOperationService;

    @Autowired
    private MarketplacesService marketplacesService;

    @Autowired
    private ItemSyncService itemSyncService;

    @Autowired
    private SalesOrderSyncService salesOrderSyncService;

    @Autowired
    private JobService jobService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
	private ConnectorProperties connectiorProperties;
    
    @RequestMapping(method = RequestMethod.GET)
    public String get(Map<String, Object> model) throws ExactException,
            IOException, SDException {
        model.put("message", "Connector Seller Dynamics");
        CurrentDivisionResponse response = restTemplate.getForObject(
                ExactURL.BASE_API_URL + "current/Me?$select=CurrentDivision,FullName", CurrentDivisionResponse.class);
        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(restTemplate.getAccessToken().getValue());
        oAuthToken.setRefreshToken(restTemplate.getAccessToken().getRefreshToken().getValue());
        RequestInfoHolder.setOAuthToken(oAuthToken);
        if (response != null && response.getDivision() != null && response.getDivision().getResults() != null
                && !response.getDivision().getResults().isEmpty()) {
            
        	CurrentDivision division = response.getDivision().getResults().get(0);
            RequestInfoHolder.setDivisionId(division.getDivision());
            List<Warehouse> ware = warehouseService.getAll();
            
//            List <RetailerMarketplace> marketPlaces =marketplacesService.getMarketplacesFromSD(division.getDivision());
//            List<UserDTO> users = userService.gerUsersFromExact();
            
            if (ware == null || ware.size() == 0) {
                throw new ExactException(ExactError.EXACT_ERROR_WAREHOUSE_NOT_FOUND);
            }
            model.put("division", division.getDivision());
            model.put("fullName", division.getFullName());
            model.put("ware", ware);
           // model.put("marketPlaces", marketPlaces);
           // model.put("users", users);
            LOGGER.info("" + restTemplate.getAccessToken());

            
            File file = new File(connectiorProperties.getFileDirectory() + division.getDivision() + "/"
                    + ConnectorConstants.FILE_NAME_PREFERENCES);

            if (!file.exists()) {
                LOGGER.error("Preferences file not found, provide credentials.");
                return "provisioning";
            }
        }
        SyncJobScheduler syncJobScheduler = new SyncJobScheduler();
    	boolean checkSchedularkStatus = syncJobScheduler.doesJobExist(QuartzConstants.JOB_NAME_ITEM);
    	model.put("checkSchedularkStatus", checkSchedularkStatus);
        
    	int divisionId = response.getDivision().getResults().get(0).getDivision();
        SyncDetailsJSON syncDetailsJSON = fileOperationService.getSyncDetail(divisionId);
        model.put("dateItemExactToSD", syncDetailsJSON.getSyncItem().getExactToSd());
        model.put("dateSalesSDToExact", syncDetailsJSON.getSyncSalesOrder().getSdToExact());
        model.put("dateCustomerSDToExact", syncDetailsJSON.getSyncCustomer().getSdToExact());
        model.put("dateDeliverySDToExact", syncDetailsJSON.getSyncDelivery().getSdToExact());
        model.put("lastSyncDate", syncDetailsJSON.getSyncItem().getExactToSd());
        
        PreferencesBean preferencesBean = fileOperationService.getPreferencesDetail(divisionId); 
        model.put("checkSchedularkStatus",preferencesBean.isSchedulerStarted());

        return "dashboard";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody String saveCredentials(@RequestBody PreferencesBean preferencesBean, Map<String, Object> model)
            throws org.json.simple.parser.ParseException, ExactException, ParseException {
        model.put("message", "Connector Seller Dynamics");
        
        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(restTemplate.getAccessToken().getValue());
        oAuthToken.setRefreshToken(restTemplate.getAccessToken().getRefreshToken().getValue());
        RequestInfoHolder.setOAuthToken(oAuthToken);
        
        CurrentDivisionResponse response = restTemplate.getForObject(
                ExactURL.BASE_API_URL + "current/Me?$select=CurrentDivision,FullName", CurrentDivisionResponse.class);
        if (response != null && response.getDivision() != null && response.getDivision().getResults() != null
                && !response.getDivision().getResults().isEmpty()) {
            CurrentDivision division = response.getDivision().getResults().get(0);
            RequestInfoHolder.setDivisionId(division.getDivision());
            List<Warehouse> ware = warehouseService.getAll();
            if (ware == null || ware.size() == 0) {
                throw new ExactException(ExactError.EXACT_ERROR_WAREHOUSE_NOT_FOUND);
            }
            model.put("division", division.getDivision());
            model.put("fullName", division.getFullName());
            model.put("ware", ware);
            LOGGER.info("" + restTemplate.getAccessToken());

            try {
                File preferencesFile = new File(connectiorProperties.getFileDirectory() + division.getDivision()
                        + ConnectorConstants.FILE_NAME_PREFERENCES);
                if (!preferencesFile.exists()) {
                    fileOperationService.createAndUpdatePrefernces(preferencesBean, division.getDivision(),oAuthToken);
                }
                File syncDetailsfile = new File(connectiorProperties.getFileDirectory() + division.getDivision()
                        + ConnectorConstants.FILE_NAME_SYNC_DETAILS);
                if (!syncDetailsfile.exists()) {
                    fileOperationService.createSyncDetails(division.getDivision(), preferencesBean.getStartSyncDate());
                }

            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("Something went wrong while generating JSON file.");
                throw new ExactException(ExactError.FILE_NAME_PREFERENCES_NOT_FOUND);
            }
        }
        return "true";
    }

    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    public @ResponseBody String performSyncOperation(@RequestBody PreferencesBean prefBean, String raisedBy,
            Model model) throws ExactException {
        RequestInfoHolder.setDivisionId(prefBean.getDivisionId());
        PreferencesBean preferencesBean = fileOperationService.getPreferencesDetail(prefBean.getDivisionId());
        
        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(restTemplate.getAccessToken().getValue());
        oAuthToken.setRefreshToken(restTemplate.getAccessToken().getRefreshToken().getValue());
        RequestInfoHolder.setOAuthToken(oAuthToken);
        
        if (preferencesBean.getSalesOrderCheckValue() == 1) {
            try {
                salesOrderSyncService.salesOrderSync(preferencesBean.getDivisionId(), preferencesBean.getWarehouse());
            } catch (Exception e) {
                 throw new ExactException(ExactError.EXACT_ERROR_PROBLEM_WITH_SALES_ORDER_SYNC);
            }
        }
        if (preferencesBean.getProductsCheckValue() == 1) {
            try {
                itemSyncService.itemSync(preferencesBean.getDivisionId());
            } catch (Exception e) {
            	e.printStackTrace();
                throw new ExactException(ExactError.EXACT_ERROR_PROBLEM_WITH_PRODUCT_SYNC);
            }
        }
        if (preferencesBean.getMarketplaceCheckValue() == 1) {
            // todo for MarketPlaces related change
             /*GetRetailerMarketplacesResponse marketPlaces =
             marketplacesService
             .getMarketplacesFromSD(preferencesBean.getDivisionId());*/
        }

        model.addAttribute("division", preferencesBean.getDivisionId());
        model.addAttribute("fullName", raisedBy);
        return "true";
    }

    @RequestMapping(value = "/getPreferences", method = RequestMethod.POST)
    public @ResponseBody PreferencesBean getPreferencesDetail(@RequestBody PreferencesBean quoteClient)
            throws ExactException {
        PreferencesBean prefBean = fileOperationService.getPreferencesDetail(quoteClient.getDivisionId());
        LOGGER.info("Get details of preferences.json for edit purpose.");
        return prefBean;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody String editCredentials(@RequestBody PreferencesBean quoteClient, Map<String, Object> model)
            throws ExactException {
        model.put("message", "Connector Seller Dynamics");
        OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(restTemplate.getAccessToken().getValue());
        oAuthToken.setRefreshToken(restTemplate.getAccessToken().getRefreshToken().getValue());
        RequestInfoHolder.setOAuthToken(oAuthToken);
        try {
            fileOperationService.createAndUpdatePrefernces(quoteClient, quoteClient.getDivisionId(), oAuthToken);
        } catch (Exception e) {
            LOGGER.error("Something went wrong while generating JSON file.");
            throw new ExactException(ExactError.PROBLEM_WHILE_CREATE_OR_UPDATE_PREFERENCES);
        }
        PreferencesBean preferencesBean = fileOperationService.getPreferencesDetail(quoteClient.getDivisionId()); 
    	jobService.startItemSchedular(quoteClient.getDivisionId(),preferencesBean.getFrequency(),preferencesBean.getFrequencyDur(), oAuthToken);
    	jobService.startSalesOrderSchedular(quoteClient.getDivisionId(),preferencesBean.getFrequency(),preferencesBean.getFrequencyDur(), oAuthToken);
        return "true";
    }
    
    @RequestMapping(value = "/schedular/start", method = RequestMethod.POST)
    public @ResponseBody String startSchedular(@RequestBody PreferencesBean prefBean, String raisedBy,
    		Map<String, Object> model) throws ExactException {
    	
    	CurrentDivisionResponse response = restTemplate.getForObject(
                ExactURL.BASE_API_URL + "current/Me?$select=CurrentDivision,FullName", CurrentDivisionResponse.class);
        
    	if (response != null && response.getDivision() != null && response.getDivision().getResults() != null
                && !response.getDivision().getResults().isEmpty()) {
        	CurrentDivision division = response.getDivision().getResults().get(0);
            RequestInfoHolder.setDivisionId(division.getDivision());
            
            OAuthToken oAuthToken = new OAuthToken();
            oAuthToken.setAccessToken(restTemplate.getAccessToken().getValue());
            oAuthToken.setRefreshToken(restTemplate.getAccessToken().getRefreshToken().getValue());
    	
	        PreferencesBean preferencesBean = fileOperationService.getPreferencesDetail(division.getDivision());
	    	preferencesBean.setSchedulerStarted(true);    	
	    	fileOperationService.createAndUpdatePrefernces(preferencesBean, division.getDivision(),oAuthToken);
	    	
	    	jobService.startItemSchedular(division.getDivision(),preferencesBean.getFrequency(),preferencesBean.getFrequencyDur(), oAuthToken);
	    	jobService.startSalesOrderSchedular(division.getDivision(),preferencesBean.getFrequency(),preferencesBean.getFrequencyDur(), oAuthToken);
    	
	    	model.put("checkSchedularkStatus",preferencesBean.isSchedulerStarted());
    	}
    	return "true";
    }
    
    @RequestMapping(value = "/schedular/stop", method = RequestMethod.POST)
    public @ResponseBody String stopSchedular(@RequestBody PreferencesBean prefBean, String raisedBy,
            Model model) throws ExactException {
    	CurrentDivisionResponse response = restTemplate.getForObject(
                ExactURL.BASE_API_URL + "current/Me?$select=CurrentDivision,FullName", CurrentDivisionResponse.class);
        
    	if (response != null && response.getDivision() != null && response.getDivision().getResults() != null
                && !response.getDivision().getResults().isEmpty()) {
        	CurrentDivision division = response.getDivision().getResults().get(0);
            RequestInfoHolder.setDivisionId(division.getDivision());
    	jobService.stopSchedular(division.getDivision());
      }
    	return "true";
    }
    
    
    
    
    @RequestMapping(value = "/getMarketplaces", method = RequestMethod.POST)
    public @ResponseBody UserAndMarketplaceDTO getMarketplaces(@RequestBody PreferencesBean preferencesBean, String raisedBy,
    		Map<String, Object> model) throws ExactException, SDException {
    	
    	OAuthToken oAuthToken = new OAuthToken();
        oAuthToken.setAccessToken(restTemplate.getAccessToken().getValue());
        oAuthToken.setRefreshToken(restTemplate.getAccessToken().getRefreshToken().getValue());
        RequestInfoHolder.setOAuthToken(oAuthToken);
    	RequestInfoHolder.setDivisionId(preferencesBean.getDivisionId());
    	List<RetailerMarketplace> marketPlaces =marketplacesService.getMarketplacesFromSD(preferencesBean);
        List<UserDTO> users = userService.gerUsersFromExact();
        
        UserAndMarketplaceDTO userAndMarketplaceDTO = new UserAndMarketplaceDTO();
        
        userAndMarketplaceDTO.setMarketPlaces(marketPlaces);
        userAndMarketplaceDTO.setUsers(users);
        
//        model.put("marketPlaces", marketPlaces);
//        model.put("users", users);
        
    	return userAndMarketplaceDTO;
    }
    
    
}