package com.assignment.jobscheduler.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.data.SortingArrayJobView;
import com.assignment.jobscheduler.data.SortingArrayJobViewTransformer;
import com.assignment.jobscheduler.service.JobSchedulerService;

@RestController
public class JobController {

	@Autowired
	private JobSchedulerService jobSchedulerService;

	@Autowired
	private SortingArrayJobViewTransformer sortingArrayJobViewTransformer;

	@PostMapping("/job")
	public Long createJob(@RequestBody Integer[] inputArray) {
		return jobSchedulerService.createJob(inputArray);
	}

	@GetMapping("/jobs")
	public List<SortingArrayJobView> getAllJobs() {
		List<SortingArrayJob> allJobs = jobSchedulerService.fetchAllJobs();
		List<SortingArrayJobView> jobsList = allJobs.stream()
												.map(sortingArrayJobViewTransformer::jobIdAndStatus)
												.collect(Collectors.toList());
		return jobsList;
	}

	@GetMapping("/job/{jobId}")
	public SortingArrayJobView getJobDetails(@PathVariable("jobId") Long jobId) {
		SortingArrayJob job = jobSchedulerService.findJobDetails(jobId);
		return sortingArrayJobViewTransformer.transform(job);
	}
}
