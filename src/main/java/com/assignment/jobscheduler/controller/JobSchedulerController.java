package com.assignment.jobscheduler.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.jobscheduler.beans.SortingArrayJobRequestWrapper;
import com.assignment.jobscheduler.beans.SortingArrayJobView;
import com.assignment.jobscheduler.beans.SortingArrayJobViewTransformer;
import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.service.JobSchedulerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class JobSchedulerController {

	@Autowired
	private JobSchedulerService jobSchedulerService;

	@Autowired
	private SortingArrayJobViewTransformer sortingArrayJobViewTransformer;

	@GetMapping("/jobs")
	public List<SortingArrayJobView> getAllJobs() {
		log.info("Handling request: GET all jobs");
		
		List<SortingArrayJob> allJobs = jobSchedulerService.fetchAllJobs();
		List<SortingArrayJobView> jobsList = allJobs.stream().map(sortingArrayJobViewTransformer::jobStatus)
				.collect(Collectors.toList());
		
		log.info("Completed request: GET all jobs");
		return jobsList;
	}
	
	@PostMapping("/job")
	public Long createJob(@RequestBody SortingArrayJobRequestWrapper input) {
		log.info("Handling request: CREATE new job");
		Long jobId = jobSchedulerService.createJob(input);
		log.info("Completed request: CREATE new job");
		return jobId;
	}

	@GetMapping("/job/{jobId}")
	public SortingArrayJobView getJobDetails(@PathVariable("jobId") Long jobId) {
		log.info("Handling request: GET job details. JobId: "+jobId);
		SortingArrayJob job = jobSchedulerService.findJobDetails(jobId);
		SortingArrayJobView jobView =  sortingArrayJobViewTransformer.transform(job);
		log.info("Completed request: GET job details. JobId: "+jobId);
		return jobView;
	}
}
