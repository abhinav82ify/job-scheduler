package com.assignment.jobscheduler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.jobscheduler.beans.JobSchedulerResponse;
import com.assignment.jobscheduler.beans.SortingArrayJobRequest;
import com.assignment.jobscheduler.service.JobSchedulerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/job-scheduler")
public class SortingArrayJobSchedulerController {

	@Autowired
	@Qualifier("sortingArrayJobSchedulerServiceImpl")
	private JobSchedulerService jobSchedulerService;

	@GetMapping("/sorting-jobs")
	public ResponseEntity<List<JobSchedulerResponse>> getAllJobs() {
		log.info("Handling request: GET all jobs");
		List<JobSchedulerResponse> response = jobSchedulerService.fetchAllJobs();
		log.info("Completed request: GET all jobs");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/sorting-job")
	public ResponseEntity<Long> createJob(@RequestBody SortingArrayJobRequest request) {
		log.info("Handling request: CREATE new job");
		Long jobId = jobSchedulerService.createJob(request);
		log.info("Completed request: CREATE new job");
		return ResponseEntity.ok(jobId);
	}

	@GetMapping("/sorting-job/{jobId}")
	public ResponseEntity<JobSchedulerResponse> getJobDetails(@PathVariable("jobId") Long jobId) {
		log.info("Handling request: GET job details. JobId: " + jobId);
		JobSchedulerResponse response = jobSchedulerService.findJobDetails(jobId);
		log.info("Completed request: GET job details. JobId: " + jobId);
		return ResponseEntity.ok(response);
	}
}
