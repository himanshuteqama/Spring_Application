/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.jobs.scheduler;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.TimeZone;

import com.solenoid.connector.config.ConnectorProperties;
import com.solenoid.connector.contant.QuartzConstants;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.jobs.JobDetails;
import com.solenoid.connector.oauth2.bean.OAuthToken;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.service.JobService;

@Component
public class SyncJobScheduler implements
		ApplicationListener<ContextRefreshedEvent> {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(SyncJobScheduler.class);

	private Scheduler scheduler = null;

	@Autowired
	FileOperationService fileOperationService;

	@Autowired
	private ConnectorProperties connectiorProperties;

	@Autowired
	private OAuth2RestOperations restTemplate;

	@Autowired
	private JobService jobService;

	@SuppressWarnings("unused")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		try {

			File[] directories = new File(
					connectiorProperties.getFileDirectory())
					.listFiles(File::isDirectory);
			ArrayList<Integer> divisionList = new ArrayList<Integer>();

			for (int i = 0; i < directories.length; i++) {
				String path = directories[i].getPath();
				int index = path.lastIndexOf('\\');
				String divisionStr = path.substring(index + 1, path.length());
				int division = Integer.parseInt(divisionStr);
				divisionList.add(division);

			}

			if (this.scheduler == null || !this.scheduler.isStarted()) {

				for (int i = 0; i < divisionList.size(); i++) {
					final StdSchedulerFactory factory = new StdSchedulerFactory();
					factory.initialize();
					this.scheduler = factory.getScheduler();
					this.scheduler.start();

					PreferencesBean preferencesBean = fileOperationService
							.getPreferencesDetail(divisionList.get(i)
									.intValue());

					if (preferencesBean.getDivisionId() != 0
							&& preferencesBean.isSchedulerStarted()) {

						String frequency = preferencesBean.getFrequency();
						String frequencyDur = preferencesBean.getFrequencyDur();

						JobDetails jd = new JobDetails();
						jd.setGroupName(String.valueOf(divisionList.get(i)
								.intValue()));
						jd.setJobClass("com.solenoid.connector.jobs.CustomerOrderSyncJob");
						jd.setJobName(QuartzConstants.JOB_NAME_SALES_ORDER);
						jd.setDivisionId(divisionList.get(i).intValue());
						jd.setTriggerName(QuartzConstants.JOB_TRIGGER_SALES_ORDER);

						// int convertedSeconds =
						// this.convertInSeconds(frequency, frequencyDur);
						jd.setFixedInterval(20);

						JobDataMap jobDataMap = new JobDataMap();
						jobDataMap.put(QuartzConstants.DIVISION_ID,
								divisionList.get(i).intValue());
						jobDataMap.put(QuartzConstants.OAUTH_DETAILS,
								preferencesBean.getoAuthToken());
						LOGGER.info("Calling schedular for sales order sync while load.");
						this.schedule(jd, jobDataMap);

						JobDetails jdItem = new JobDetails();
						jdItem.setGroupName(String.valueOf(divisionList.get(i)
								.intValue()));
						jdItem.setJobClass("com.solenoid.connector.jobs.ItemSyncJob");
						jdItem.setJobName(QuartzConstants.JOB_NAME_ITEM);
						jdItem.setDivisionId(divisionList.get(i).intValue());
						jdItem.setTriggerName(QuartzConstants.JOB_TRIGGER_ITEM);

						// int convertedSeconds =
						// this.convertInSeconds(frequency, frequencyDur);
						jdItem.setFixedInterval(20);
						LOGGER.info("Calling schedular for item sync while load.");
						this.schedule(jdItem, jobDataMap);
					}
					// List<JobDetails> cronJobDTOs = null;
					// JobDetails jd = new JobDetails();
					// jd.setFixedInterval(2);
					// jd.setGroupName("11111");
					// jd.setJobClass("com.solenoid.connector.jobs.ItemSyncJob");
					// jd.setJobName("item-sync");
					// jd.setDivisionId(1312);
					// jd.setTriggerName("item-sync-trigger");
					// cronJobDTOs.add(jd);
					/*
					 * if (cronJobDTOs != null && !cronJobDTOs.isEmpty()) { for
					 * (JobDetails cronJob : cronJobDTOs) { JobDataMap
					 * jobDataMap = null; if (cronJob.getDivisionId() > 0) {
					 * jobDataMap = new JobDataMap();
					 * jobDataMap.put(QuartzConstants.DIVISION_ID,
					 * cronJob.getDivisionId()); } if
					 * (this.scheduler.checkExists(new JobKey(cronJob
					 * .getJobName()))) { this.rescheduleJob(cronJob,
					 * jobDataMap); } else { this.scheduleJob(cronJob,
					 * jobDataMap); } } }
					 */

				} // end for
			}// end if
		} catch (SchedulerException e) {
			LOGGER.error("SchedulerException :" + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("Unknown exception :" + e.getMessage(), e);
		}
	}

	private Class<? extends Job> getJobClass(String jobName)
			throws ClassNotFoundException {
		return Class.forName(jobName).asSubclass(Job.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void rescheduleJob(JobDetails quartzJob, JobDataMap newJobDataMap) {
		LOGGER.info("Rescheduling the Job : " + quartzJob.getJobName());
		try {
			if (this.scheduler != null && this.scheduler.isStarted()) {
				Trigger trigger = null;
				Trigger oldTrigger = this.scheduler.getTrigger(new TriggerKey(
						quartzJob.getTriggerName(), quartzJob.getGroupName()));
				TriggerBuilder triggetBuilder = oldTrigger.getTriggerBuilder();
				TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
				trigger = triggetBuilder
						.withSchedule(
								SimpleScheduleBuilder
										.repeatSecondlyForever(quartzJob
												.getFixedInterval()))
						.startNow().build();
				if (trigger != null) {
					// reSchedule job
					this.scheduler.rescheduleJob(oldTrigger.getKey(), trigger);
				}
			}
		} catch (SchedulerException e) {
			LOGGER.error("SchedulerException :" + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("Unknown exception  :" + e.getMessage());
		}
	}

	private void scheduleJob(JobDetails cronJob, JobDataMap jobDataMap) {
		Trigger trigger = null;
		try {
			if (this.scheduler != null && this.scheduler.isStarted()) {
				LOGGER.info("Scheduling the Job : " + cronJob.getJobName());

				trigger = TriggerBuilder
						.newTrigger()
						.withIdentity(cronJob.getTriggerName(),
								cronJob.getGroupName())
						.withSchedule(
								SimpleScheduleBuilder
										.repeatSecondlyForever(
												cronJob.getFixedInterval())
										.withMisfireHandlingInstructionFireNow())
						.startNow().build();
				// Execute the job.
				JobDetail job = null;
				JobBuilder jobBuilder = JobBuilder.newJob(this
						.getJobClass(cronJob.getJobClass()));
				jobBuilder.requestRecovery(true);
				jobBuilder.withIdentity(cronJob.getJobName());
				if (jobDataMap == null) {
					jobDataMap = new JobDataMap();
				}
				jobBuilder.usingJobData(jobDataMap);
				job = jobBuilder.build();
				if (trigger != null) {
					this.scheduler.scheduleJob(job, trigger);
				}
			}
		} catch (SchedulerException e) {
			LOGGER.error("SchedulerException :" + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("Unknown exception  :" + e.getMessage());
		}
	}

	public void schedule(JobDetails cronJob, JobDataMap jobDataMap) {
		if (cronJob != null) {
			try {
				if (this.scheduler != null && this.scheduler.isStarted()) {
					if (this.scheduler.checkExists(new JobKey(cronJob
							.getJobName()))) {
						this.rescheduleJob(cronJob, jobDataMap);
					} else {
						this.scheduleJob(cronJob, jobDataMap);
					}
				}
			} catch (SchedulerException e) {
				LOGGER.error("SchedulerException :" + e.getMessage(), e);
			} catch (Exception e) {
				LOGGER.error("Unknown exception :" + e.getMessage());
			}
		}
	}

	public boolean deleteScheduleJob(String jobName, String groupName,
			String triggerName, int divisionId) {
		try {
			if (this.scheduler != null && this.scheduler.isStarted()) {
				boolean isJobDeleted = false;
				try {
					if (this.scheduler.checkExists(new JobKey(jobName))) {
						Trigger trigger = this.scheduler
								.getTrigger(new TriggerKey(triggerName,
										groupName));
						this.scheduler.deleteJob(trigger.getJobKey());
						isJobDeleted = true;
						OAuthToken oAuthToken = new OAuthToken();
						oAuthToken.setAccessToken(restTemplate.getAccessToken()
								.getValue());
						oAuthToken.setRefreshToken(restTemplate
								.getAccessToken().getRefreshToken().getValue());
						PreferencesBean preferencesBean = fileOperationService
								.getPreferencesDetail(divisionId);
						preferencesBean.setSchedulerStarted(false);
						fileOperationService.createAndUpdatePrefernces(
								preferencesBean, divisionId, oAuthToken);

					}
				} catch (Exception e) {
					LOGGER.error("Exception occured while deleting  jobName :"
							+ jobName + " Error : " + e.getMessage());
				}
				return isJobDeleted;
			}
		} catch (SchedulerException e) {
			LOGGER.error("SchedulerException :" + e.getMessage(), e);
		}
		return false;
	}

	public boolean doesJobExist(String jobName) {
		boolean isJobExists = false;
		try {
			if (this.scheduler != null && this.scheduler.isStarted()) {
				isJobExists = this.scheduler.checkExists(new JobKey(jobName));
			}
		} catch (SchedulerException e) {
			LOGGER.error("Exception occured while checking jobName exists :"
					+ jobName + " Error : " + e.getMessage());
		}
		return isJobExists;
	}
}
