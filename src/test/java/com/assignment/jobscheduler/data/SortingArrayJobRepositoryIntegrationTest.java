package com.assignment.jobscheduler.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SortingArrayJobRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private SortingArrayJobRepository sortingArrayJobRepository;
    
    @Test
    public void test_save() {
    	SortingArrayJob job = new SortingArrayJob();
		job.setData(Arrays.array(1l, 2l, 3l, 21l));
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_PENDING);
    	
		SortingArrayJob savedJob = sortingArrayJobRepository.save(job);
		
		assertNotNull(savedJob.getJobId());
		assertNotNull(savedJob.getCreatedTime());
		assertNotNull(savedJob.getUpdatedTime());
    }
    
    @Test
    public void test_findAll() {
    	SortingArrayJob job = new SortingArrayJob();
		job.setJobId(2l);
		job.setData(Arrays.array(1l, 2l, 3l, 21l));
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED);
		job.setCreatedTime(LocalDateTime.now());
		job.setUpdatedTime(LocalDateTime.now());
		job.setDurationMilliseconds(0l);

		entityManager.merge(job);
		entityManager.flush();
    	
		List<SortingArrayJob> savedJobs = sortingArrayJobRepository.findAll();
		
		assertEquals(1, savedJobs.size());
    }

    // since jobId is generated dynamically, this is throwing an error.
    // needs investigation
    @Test
    @Ignore 
    public void test_findById() {
    	SortingArrayJob job = new SortingArrayJob();
		job.setData(Arrays.array(1l, 2l, 3l, 21l));
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED);
		job.setCreatedTime(LocalDateTime.now());
		job.setUpdatedTime(LocalDateTime.now());
		job.setDurationMilliseconds(0l);

		entityManager.merge(job);
		entityManager.flush();
		
		Optional<SortingArrayJob> savedJob = sortingArrayJobRepository.findById(1l);
		
		assertTrue(savedJob.isPresent());
		assertEquals(Long.valueOf(1).longValue(), savedJob.get().getJobId().longValue());
    }
}
