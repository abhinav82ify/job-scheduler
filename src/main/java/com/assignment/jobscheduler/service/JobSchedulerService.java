package com.assignment.jobscheduler.service;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.data.SortingArrayJobViewTransformer;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

@Service
@Transactional
public class JobSchedulerService {
	
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	EntityManager em;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	SortingArrayJobViewTransformer sortingArrayJobViewTransformer;
	
	public Long createJob(Integer[] inputArray) {
		SortingArrayJob job = new SortingArrayJob();
		job.setData(inputArray);
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_PENDING);
		
		LOGGER.info("Input received: Persisting in database");
		em.persist(job);
		LOGGER.info("Job saved in database with jobId: "+job.getJobId());
		
		try {
			LOGGER.info("Pushing job: "+job.getJobId()+" to queue");
			jmsTemplate.convertAndSend(JobSchedulerConstants.SORTING_JOB_MQ_ENDPOINT, job);
			LOGGER.info("Pushing job: "+job.getJobId()+" to queue successful");
		} catch(Exception e) {
			LOGGER.error("Error occured while pushing the object to queue");
		}
		
		return job.getJobId();
	}
	
	public void completeJobAndUpdate(SortingArrayJob job) {
		Arrays.sort(job.getData());
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED);
		
		LOGGER.info("Job: "+job.getJobId()+" completed and updating the database with status and data");
		em.merge(job);
	}
	
	public List<SortingArrayJob> fetchAllJobs() {
		TypedQuery<SortingArrayJob> createNamedQuery = em.createNamedQuery("findAllJobs", SortingArrayJob.class);
		return createNamedQuery.getResultList();
	}
	
	public SortingArrayJob findJobDetails(Long jobId) {
		return em.find(SortingArrayJob.class, jobId);
	}
}
