package com.assignment.jobscheduler.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.jobscheduler.beans.SortingArrayJobRequestWrapper;
import com.assignment.jobscheduler.beans.SortingArrayJobViewTransformer;
import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.exception.BadRequestException;
import com.assignment.jobscheduler.exception.InvalidJobException;
import com.assignment.jobscheduler.exception.JobSchedulerException;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class JobSchedulerServiceImpl implements JobSchedulerService {

	@Autowired
	EntityManager em;

	@Autowired
	JmsTemplate jmsTemplate;

	@Autowired
	SortingArrayJobViewTransformer sortingArrayJobViewTransformer;

	@Override
	public Long createJob(SortingArrayJobRequestWrapper input) {
		SortingArrayJob job = createEntity(input);

		log.info("Input received: Persisting in database");

		em.persist(job);

		log.info("Job saved in database with jobId: " + job.getJobId());

		try {
			log.info("Pushing job: " + job.getJobId() + " to queue");

			jmsTemplate.convertAndSend(JobSchedulerConstants.SORTING_JOB_MQ_ENDPOINT, job);

			log.info("Pushing job: " + job.getJobId() + " to queue successful");
		} catch (Exception e) {
			log.info("Exception occured while writing job to queue: Deleting entry from database");
			em.remove(job);
			throw new JobSchedulerException("An unexpected error has occured while creating the job.");
		}

		return job.getJobId();
	}

	@Override
	public void completeJobAndUpdate(SortingArrayJob job) {
		Arrays.sort(job.getData());
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED);

		log.info("Job: " + job.getJobId() + " completed and updating the database with status and data");

		em.merge(job);
	}

	@Override
	public List<SortingArrayJob> fetchAllJobs() {
		TypedQuery<SortingArrayJob> createNamedQuery = em.createNamedQuery("findAllJobs", SortingArrayJob.class);
		return createNamedQuery.getResultList();
	}

	@Override
	public SortingArrayJob findJobDetails(Long jobId) {
		Optional<SortingArrayJob> job = Optional.ofNullable(em.find(SortingArrayJob.class, jobId));
		if (!job.isPresent()) {
			throw new InvalidJobException("Invalid jobId: " + jobId);
		}

		return job.get();
	}

	private SortingArrayJob createEntity(SortingArrayJobRequestWrapper input) {
		Optional<Long[]> inputArray = Optional.ofNullable(input.getData());
		if (!inputArray.isPresent() || inputArray.get().length == 0) {
			throw new BadRequestException("Either input is empty or is not provided in correct format.");
		}
		return new SortingArrayJob(null, inputArray.get(), JobSchedulerConstants.SORTING_JOB_STATUS_PENDING, null, null,
				null);
	}
}
