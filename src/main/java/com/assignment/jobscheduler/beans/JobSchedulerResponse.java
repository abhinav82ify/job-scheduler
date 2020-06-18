package com.assignment.jobscheduler.beans;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class JobSchedulerResponse {
	protected Long jobId;
	protected String status;
	protected LocalDateTime createdTime;
	protected LocalDateTime updatedTime;
	protected Long durationMilliseconds;
}
