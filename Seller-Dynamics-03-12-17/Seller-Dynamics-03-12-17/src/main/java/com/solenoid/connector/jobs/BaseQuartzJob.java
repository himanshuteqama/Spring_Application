
package com.solenoid.connector.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.solenoid.connector.config.QuartzApplicationContextProvider;
import com.solenoid.connector.contant.QuartzConstants;
import com.solenoid.connector.dto.PreferencesBean;
import com.solenoid.connector.exception.ExactException;
import com.solenoid.connector.oauth2.ExactOAuthHandler;
import com.solenoid.connector.oauth2.bean.OAuthToken;
import com.solenoid.connector.service.FileOperationService;
import com.solenoid.connector.util.RequestInfoHolder;

@Service
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BaseQuartzJob implements Job {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaseQuartzJob.class);

    private final static String COUNTER = "counter";

    @Autowired
    private ExactOAuthHandler exactOAuthHandler;
    
    @Autowired
    FileOperationService  fileOperationService; 

    @Override
    public final void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        BaseQuartzJob job = (BaseQuartzJob) QuartzApplicationContextProvider.getApplicationContext()
                .getBean(jobExecutionContext.getJobDetail().getJobClass());
        if (this.exactOAuthHandler == null) {
            this.exactOAuthHandler = QuartzApplicationContextProvider.getApplicationContext()
                    .getBean(ExactOAuthHandler.class);
        }
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        OAuthToken oAuthToken = (OAuthToken) dataMap.get(QuartzConstants.OAUTH_DETAILS);
        int divisionId = (int) dataMap.get(QuartzConstants.DIVISION_ID);
        int counter = 1;
        if (dataMap.get(COUNTER) != null) {
            counter = (int) dataMap.get(COUNTER);
        }
        try {
            RequestInfoHolder.setDivisionId(divisionId);
            // LOGGER.info("AT - " + oAuthToken.getAccessToken());
            // LOGGER.info("RT - " + oAuthToken.getRefreshToken());
            LOGGER.info("Job " + job.getClass().getName() + " : " + divisionId + " executing " + counter + " times.");
            RequestInfoHolder.setOAuthToken(oAuthToken);

            job.performJob(dataMap);
            jobExecutionContext.getJobDetail().getJobDataMap().put(COUNTER, ++counter);
        } catch (HttpClientErrorException e) {
            LOGGER.error("HTTP error while excuting job " + jobExecutionContext.getJobDetail().getKey().getName(), e);
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                oAuthToken = exactOAuthHandler.refreshAccessToken(oAuthToken.getRefreshToken());
                jobExecutionContext.getJobDetail().getJobDataMap().put(QuartzConstants.OAUTH_DETAILS, oAuthToken);
                //todo: update in preference json
                PreferencesBean preferencesBean = null;
				try {
					preferencesBean = fileOperationService.getPreferencesDetail(divisionId);
					fileOperationService.createAndUpdatePrefernces(preferencesBean, divisionId, oAuthToken);
				} catch (ExactException e1) {
					LOGGER.error("Something went wrong while read JSON file while loading.",e1);
				}  
                
            }
        } catch (Exception e) {
            LOGGER.error("Error in executing job " + jobExecutionContext.getJobDetail().getKey().getName(), e);
            throw new JobExecutionException(
                    "Error in executing job " + jobExecutionContext.getJobDetail().getKey().getName(), e);
        }
    }

    protected void performJob(JobDataMap dataMap) throws Exception {
    };
}
