/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.service.impl;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;

import com.solenoid.connector.contant.QuartzConstants;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.jobs.JobDetails;
import com.solenoid.connector.jobs.scheduler.SyncJobScheduler;
import com.solenoid.connector.oauth2.bean.OAuthToken;
import com.solenoid.connector.service.JobService;

/**
 * Job service to manage job schedular's operation.
 */
@Service
public class JobServiceImpl implements  JobService{

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ItemServiceImpl.class);
	
	@Autowired
    private SyncJobScheduler syncJobScheduler;
	
	@Autowired
    private OAuth2RestOperations restTemplate;
	
	//TODO: get authToken as parameter
	@Override
	public void startItemSchedular(int divisionId, String frequency, String frequencyDur, OAuthToken oAuthToken) throws ExactException {
			
		JobDetails jd = new JobDetails();
        jd.setGroupName(String.valueOf(divisionId)); 
        jd.setJobClass("com.solenoid.connector.jobs.ItemSyncJob");
        jd.setJobName(QuartzConstants.JOB_NAME_ITEM);
        jd.setDivisionId(divisionId);
        jd.setTriggerName(QuartzConstants.JOB_TRIGGER_ITEM);

        int convertedSeconds = this.convertInSeconds(frequency, frequencyDur);
        jd.setFixedInterval(convertedSeconds); 
        
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(QuartzConstants.DIVISION_ID, divisionId);
        jobDataMap.put(QuartzConstants.OAUTH_DETAILS, oAuthToken);
        LOGGER.info("Calling schedular for item sync.");
        syncJobScheduler.schedule(jd, jobDataMap);
		
	}

	@Override
	public void startSalesOrderSchedular(int divisionId, String frequency, String frequencyDur, OAuthToken oAuthToken) throws ExactException {
		
		JobDetails jd = new JobDetails();
		jd.setGroupName(String.valueOf(divisionId));
		jd.setJobClass("com.solenoid.connector.jobs.CustomerOrderSyncJob");
		jd.setJobName(QuartzConstants.JOB_NAME_SALES_ORDER);
		jd.setDivisionId(divisionId);
		jd.setTriggerName(QuartzConstants.JOB_TRIGGER_SALES_ORDER);
		
		int convertedSeconds = this.convertInSeconds(frequency, frequencyDur);
		jd.setFixedInterval(convertedSeconds); 
        
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(QuartzConstants.DIVISION_ID, divisionId);
        jobDataMap.put(QuartzConstants.OAUTH_DETAILS, oAuthToken);
        LOGGER.info("Calling schedular for sales order sync.");
        syncJobScheduler.schedule(jd, jobDataMap);
	}
	
	@Override
	public void stopSchedular(int divisionId) throws ExactException {
		LOGGER.info("Stopping all jobs for division " + divisionId);
		String groupName = String.valueOf(divisionId);
		String jobNameItem = QuartzConstants.JOB_NAME_ITEM;
		String triggerNameItem = QuartzConstants.JOB_TRIGGER_ITEM;
		syncJobScheduler.deleteScheduleJob(jobNameItem, groupName, triggerNameItem, divisionId);
		
		String jobNameSales = QuartzConstants.JOB_NAME_SALES_ORDER;
		String triggerNameSales = QuartzConstants.JOB_TRIGGER_SALES_ORDER;
		syncJobScheduler.deleteScheduleJob(jobNameSales, groupName, triggerNameSales, divisionId);
		LOGGER.info("Successfully stopped all jobs for division " + divisionId);
	}
	
	@Override
	public int convertInSeconds(String frequency, String frequencyDur){
		//code for convert frequency in seconds.
        int frequencyInt = Integer.parseInt(frequency);
		int convertInSeconds = 0;
        
		switch(frequencyDur){
		case "hour":
	        convertInSeconds = 3600*frequencyInt;
	        break;
		case "day":
			convertInSeconds = 86400*frequencyInt;
			break;
		}
		LOGGER.info("frequency in seconds : ",convertInSeconds);
		return convertInSeconds;
	}

}
