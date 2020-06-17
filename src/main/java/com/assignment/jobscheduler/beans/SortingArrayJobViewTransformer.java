package com.assignment.jobscheduler.beans;

import org.springframework.stereotype.Component;

import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

@Component
public class SortingArrayJobViewTransformer {

	public SortingArrayJobView transform(SortingArrayJob job) {
		if (job.getStatus().equals(JobSchedulerConstants.SORTING_JOB_STATUS_PENDING)) {
			return jobStatus(job);
		} else if (job.getStatus().equals(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED)) {
			return SortingArrayJobView.builder().jobId(job.getJobId()).data(job.getData()).status(job.getStatus())
					.createdTime(job.getCreatedTime()).updatedTime(job.getUpdatedTime())
					.durationMilliseconds(job.getDurationMilliseconds()).build();
		}

		return new SortingArrayJobView();
	}

	public SortingArrayJobView jobStatus(SortingArrayJob job) {
		return SortingArrayJobView.builder().jobId(job.getJobId()).status(job.getStatus())
				.createdTime(job.getCreatedTime()).build();
	}
}
