package com.assignment.jobscheduler.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.service.JobSchedulerService;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

@Component
public class JobSchedulerMessageReciever {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JobSchedulerService jobSchedulerService;
	
	@JmsListener(destination = JobSchedulerConstants.SORTING_JOB_MQ_ENDPOINT)
	public void receiveMessage(SortingArrayJob job) {
		LOGGER.info("Received job: "+job.getJobId()+" to listener");
		jobSchedulerService.completeJobAndUpdate(job);
	}
}
