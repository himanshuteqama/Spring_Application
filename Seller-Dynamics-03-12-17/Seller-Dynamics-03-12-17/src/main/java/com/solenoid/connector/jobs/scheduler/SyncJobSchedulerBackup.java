/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.jobs.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import com.solenoid.connector.jobs.ItemSyncJob;

@Component
public class SyncJobSchedulerBackup {

	public void scheduleJob(int divistionId) {

		// todo: get provisioning details (preferences)
		int intervalInSeconds = 4;
		// define the job and tie it to our HelloJob class
		JobDetail itemSyncJob = newJob(ItemSyncJob.class).withIdentity(
				"ItemSyncJob-" + divistionId, "" + divistionId) // name "myJob",
																// group
																// "group1"
				.build();

		// Trigger the job to run now, and then every 40 seconds
		Trigger trigger = newTrigger()
				.withIdentity("ItemSyncJobTrigger-" + divistionId,
						"" + divistionId)
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(
								intervalInSeconds).repeatForever()).build();

		// TriggerBuilder tb = trigger.getTriggerBuilder();
		//
		// Trigger trigger2 = tb.withSchedule(
		// simpleSchedule().withIntervalInSeconds(2)
		// .repeatForever()).build();

		// Tell quartz to schedule the job using our trigger
		Scheduler sched;
		try {
			sched = new StdSchedulerFactory().getScheduler();
			sched.start();
			sched.scheduleJob(itemSyncJob, trigger);
		} catch (SchedulerException e) {
			// TODO throw exactexcpeption
			e.printStackTrace();
		}

		// Thread.sleep(10 * 1000);
		// System.out.println("Rescheduling...");
		// sched.rescheduleJob(trigger.getKey(), trigger2);

	}
}
