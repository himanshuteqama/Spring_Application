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
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.service.ItemSyncService;

@Service
public class ItemSyncJob extends BaseQuartzJob {

    private final static Logger LOGGER = LoggerFactory.getLogger(ItemSyncJob.class);

    @Autowired
    private ItemSyncService itemSyncService;

    @Override
    protected void performJob(JobDataMap dataMap) throws ExactException {
        LOGGER.info("Starting item-sync-job.");
        int divisionId = (int) dataMap.get(QuartzConstants.DIVISION_ID);
        itemSyncService.itemSync(divisionId);
        LOGGER.info("item-sync - " + divisionId + " : " + new java.util.Date());
        LOGGER.info("Ending item-sync-job.");
    }
}
