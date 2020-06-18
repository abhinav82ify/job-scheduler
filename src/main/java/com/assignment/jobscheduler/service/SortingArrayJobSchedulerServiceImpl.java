package com.assignment.jobscheduler.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.jobscheduler.beans.JobSchedulerRequest;
import com.assignment.jobscheduler.beans.JobSchedulerResponse;
import com.assignment.jobscheduler.beans.SortingArrayJobRequest;
import com.assignment.jobscheduler.beans.SortingArrayJobResponseTransformer;
import com.assignment.jobscheduler.data.Job;
import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.data.SortingArrayJobRepository;
import com.assignment.jobscheduler.exception.BadRequestException;
import com.assignment.jobscheduler.exception.InvalidJobException;
import com.assignment.jobscheduler.exception.JobSchedulerException;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Abhinav Sharma
 *
 */
@Service
@Transactional
@Slf4j
public class SortingArrayJobSchedulerServiceImpl implements JobSchedulerService {

	@Autowired
	private SortingArrayJobRepository sortingArrayJobRepository;

	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	SortingArrayJobResponseTransformer sortingArrayJobViewTransformer;

	/**
	 * @param request
	 * @return
	 * 
	 *         This implementation of create job accepts the request, creates
	 *         Entity, persists in Database. After persisting in Database, the
	 *         entity is sent to a queue. If an exception occurs while writing to
	 *         the queue, the entity is removed from the database and an exception
	 *         is thrown.
	 */
	@Override
	public Long createJob(JobSchedulerRequest request) {
		SortingArrayJobRequest sortingRequest = (SortingArrayJobRequest) request;
		SortingArrayJob job = createEntity(sortingRequest);

		log.info("Input received: Persisting in database");

		SortingArrayJob savedJob = sortingArrayJobRepository.save(job);

		log.info("Job saved in database with jobId: " + savedJob.getJobId());

		try {
			log.info("Pushing job: " + savedJob.getJobId() + " to queue");

			jmsTemplate.convertAndSend(JobSchedulerConstants.SORTING_JOB_MQ_ENDPOINT, savedJob);

			log.info("Pushing job: " + savedJob.getJobId() + " to queue successful");
		} catch (JmsException e) {
			log.info("Exception occured while writing job to queue: Deleting entry from database");
			sortingArrayJobRepository.delete(savedJob);
			throw new JobSchedulerException("An unexpected error has occured while creating the job.");
		}

		return savedJob.getJobId();
	}

	/**
	 * @param job
	 * 
	 *            This implementation accepts the job to be completed. Here, the job
	 *            completion refers to sorting the data. After sorting the data, the
	 *            entity is updated in the databse.
	 */
	@Override
	public void completeJobAndUpdate(Job job) {
		SortingArrayJob sortingArrayJob = (SortingArrayJob) job;
		Arrays.sort(sortingArrayJob.getData());
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED);

		log.info("Job: " + sortingArrayJob.getJobId() + " completed and updating the database with status and data");

		sortingArrayJobRepository.save(sortingArrayJob);
	}

	/**
	 * @return
	 * 
	 *         This method is used to fetch all jobs from Database irrespective of
	 *         their status. The response includes jobId, status and creationTime.
	 */
	@Override
	public List<JobSchedulerResponse> fetchAllJobs() {
		List<SortingArrayJob> resultList = sortingArrayJobRepository.findAll();

		List<JobSchedulerResponse> response = resultList.stream().map(sortingArrayJobViewTransformer::jobStatus)
				.collect(Collectors.toList());

		return response;
	}

	/**
	 * @param jobId
	 * @return
	 * 
	 *         This method is used to find details of a specific job. An exception
	 *         is thrown if no entity is present in database with corresponding
	 *         jobId.
	 */
	@Override
	public JobSchedulerResponse findJobDetails(Long jobId) {
		Optional<SortingArrayJob> job = sortingArrayJobRepository.findById(jobId);
		if (!job.isPresent()) {
			throw new InvalidJobException("No job found with jobId: " + jobId);
		}

		JobSchedulerResponse response = sortingArrayJobViewTransformer.transform(job.get());

		return response;
	}

	/**
	 * @param sortingRequest
	 * @return
	 * 
	 *         The method is responsible to create entity from the request object.
	 *         If the request does not contain required parameters, an exception is
	 *         thrown.
	 */
	private SortingArrayJob createEntity(SortingArrayJobRequest sortingRequest) {
		Optional<Long[]> inputArray = Optional.ofNullable(sortingRequest.getData());
		if (!inputArray.isPresent() || inputArray.get().length == 0) {
			throw new BadRequestException("Either input is empty or is not provided in correct format.");
		}

		SortingArrayJob newJob = new SortingArrayJob();
		newJob.setData(inputArray.get());
		newJob.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_PENDING);

		return newJob;
	}
}
