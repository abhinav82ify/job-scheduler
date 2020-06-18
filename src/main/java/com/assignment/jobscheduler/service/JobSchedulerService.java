package com.assignment.jobscheduler.service;

import java.util.List;

import com.assignment.jobscheduler.beans.JobSchedulerRequest;
import com.assignment.jobscheduler.beans.JobSchedulerResponse;
import com.assignment.jobscheduler.data.Job;

public interface JobSchedulerService {

	/**
	 * @param request
	 * @return
	 */
	public Long createJob(JobSchedulerRequest request);

	/**
	 * @param job
	 */
	public void completeJobAndUpdate(Job job);

	/**
	 * @return
	 */
	public List<JobSchedulerResponse> fetchAllJobs();

	/**
	 * @param jobId
	 * @return
	 */
	public JobSchedulerResponse findJobDetails(Long jobId);
}
