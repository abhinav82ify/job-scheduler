package com.assignment.jobscheduler.service;

import java.util.List;

import com.assignment.jobscheduler.beans.SortingArrayJobRequestWrapper;
import com.assignment.jobscheduler.data.SortingArrayJob;

public interface JobSchedulerService {

	public Long createJob(SortingArrayJobRequestWrapper input);

	public void completeJobAndUpdate(SortingArrayJob job);

	public List<SortingArrayJob> fetchAllJobs();

	public SortingArrayJob findJobDetails(Long jobId);
}
