package com.assignment.jobscheduler.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.assignment.jobscheduler.beans.JobSchedulerResponse;
import com.assignment.jobscheduler.beans.SortingArrayJobRequest;
import com.assignment.jobscheduler.beans.SortingArrayJobResponse;
import com.assignment.jobscheduler.beans.SortingArrayJobResponseTransformer;
import com.assignment.jobscheduler.data.SortingArrayJob;
import com.assignment.jobscheduler.data.SortingArrayJobRepository;
import com.assignment.jobscheduler.exception.BadRequestException;
import com.assignment.jobscheduler.exception.InvalidJobException;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SortingArrayJobSchedulerServiceImpl.class })
public class SortingArrayJobSchedulerServiceImplTest {

	@MockBean
	private SortingArrayJobRepository sortingArrayJobRepository;

	@MockBean
	private JmsTemplate jmsTemplate;

	@MockBean
	private SortingArrayJobResponseTransformer sortingArrayJobViewTransformer;

	@Autowired
	private SortingArrayJobSchedulerServiceImpl serviceImpl;

	@Test
	public void test_createJob() {
		SortingArrayJobRequest request = new SortingArrayJobRequest();
		request.setData(Arrays.array(1l, 5l, 3l, 2l));

		SortingArrayJob savedJob = new SortingArrayJob();
		savedJob.setJobId(1l);

		Mockito.when(sortingArrayJobRepository.save(Mockito.any())).thenReturn(savedJob);

		doNothing().when(jmsTemplate).convertAndSend(any(SortingArrayJob.class));

		Long actualResult = serviceImpl.createJob(request);
		Long expectedResult = 1l;

		assertEquals(expectedResult, actualResult);
	}

	@Test(expected = BadRequestException.class)
	public void test_createJob_DataNull() {
		SortingArrayJobRequest request = new SortingArrayJobRequest();

		serviceImpl.createJob(request);
	}

	@Test(expected = BadRequestException.class)
	public void test_createJob_DataEmpty() {
		SortingArrayJobRequest request = new SortingArrayJobRequest();
		request.setData(Arrays.array());

		serviceImpl.createJob(request);
	}

	@Test
	public void test_completeJobAndUpdate() {
		SortingArrayJob job = new SortingArrayJob();
		job.setData(Arrays.array(1l, 5l, 3l, 2l));

		when(sortingArrayJobRepository.save(Mockito.any())).thenReturn(Mockito.any());

		serviceImpl.completeJobAndUpdate(job);
	}

	@Test
	public void test_fetchAllJobs() {
		SortingArrayJob job = new SortingArrayJob();
		job.setJobId(1l);
		job.setData(Arrays.array(1l, 2l, 3l, 21l));
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED);
		job.setCreatedTime(LocalDateTime.now());
		job.setUpdatedTime(LocalDateTime.now());
		job.setDurationMilliseconds(0l);

		List<SortingArrayJob> allJobs = new ArrayList<SortingArrayJob>();
		allJobs.add(job);

		SortingArrayJobResponse jobResponse = SortingArrayJobResponse.builder().jobId(job.getJobId())
				.data(job.getData()).status(job.getStatus()).createdTime(job.getCreatedTime())
				.updatedTime(job.getUpdatedTime()).durationMilliseconds(job.getDurationMilliseconds()).build();

		when(sortingArrayJobRepository.findAll()).thenReturn(allJobs);
		when(sortingArrayJobViewTransformer.transform(job)).thenReturn(jobResponse);

		serviceImpl.fetchAllJobs();
	}

	@Test
	public void test_findJobDetails() {
		SortingArrayJob job = new SortingArrayJob();
		job.setJobId(1l);
		job.setData(Arrays.array(1l, 2l, 3l, 21l));
		job.setStatus(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED);
		job.setCreatedTime(LocalDateTime.now());
		job.setUpdatedTime(LocalDateTime.now());
		job.setDurationMilliseconds(0l);

		SortingArrayJobResponse expectedResponse = SortingArrayJobResponse.builder().jobId(job.getJobId())
				.data(job.getData()).status(job.getStatus()).createdTime(job.getCreatedTime())
				.updatedTime(job.getUpdatedTime()).durationMilliseconds(job.getDurationMilliseconds()).build();

		when(sortingArrayJobRepository.findById(1L)).thenReturn(Optional.of(job));
		when(sortingArrayJobViewTransformer.transform(job)).thenReturn(expectedResponse);

		JobSchedulerResponse actualResponse = serviceImpl.findJobDetails(1L);

		assertEquals(expectedResponse, actualResponse);
	}

	@Test(expected = InvalidJobException.class)
	public void test_findJobDetails_NoData() {

		when(sortingArrayJobRepository.findById(1L)).thenReturn(Optional.empty());

		serviceImpl.findJobDetails(1L);
	}

}
