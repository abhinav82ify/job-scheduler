package com.assignment.jobscheduler.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.service.JobSchedulerService;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JobSchedulerMessageReciever {

	@Autowired
	@Qualifier("sortingArrayJobSchedulerServiceImpl")
	private JobSchedulerService jobSchedulerService;

	@JmsListener(destination = JobSchedulerConstants.SORTING_JOB_MQ_ENDPOINT)
	public void receiveMessage(SortingArrayJob job) {
		log.info("Received job: " + job.getJobId() + " to listener");
		jobSchedulerService.completeJobAndUpdate(job);
	}
}
