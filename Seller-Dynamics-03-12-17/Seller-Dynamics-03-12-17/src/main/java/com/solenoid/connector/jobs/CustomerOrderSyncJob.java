/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.jobs;

import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solenoid.connector.contant.QuartzConstants;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.SalesOrderSyncService;

@Service
public class CustomerOrderSyncJob extends BaseQuartzJob {

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerOrderSyncJob.class);

    @Autowired
    private SalesOrderSyncService salesOrderSyncService;
    
    @Autowired
    private FileOperationService fileOperationService;

    @Override
    protected void performJob(JobDataMap dataMap) throws ExactException {
        LOGGER.info("Starting sales-order-sync-job.");
        int divisionId = (int) dataMap.get(QuartzConstants.DIVISION_ID);
        PreferencesBean preferencesBean = fileOperationService.getPreferencesDetail(divisionId);
        salesOrderSyncService.salesOrderSync(divisionId, preferencesBean.getWarehouse());
        LOGGER.info("sales-order-sync - " + divisionId + " : " + new java.util.Date());
        LOGGER.info("Ending sales-order-sync.");
    }
}
