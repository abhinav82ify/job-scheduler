package com.assignment.jobscheduler.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.assignment.jobscheduler.beans.JobSchedulerResponse;
import com.assignment.jobscheduler.beans.SortingArrayJobRequest;
import com.assignment.jobscheduler.beans.SortingArrayJobResponse;
import com.assignment.jobscheduler.service.JobSchedulerService;
import com.assignment.jobscheduler.utilities.JobSchedulerConstants;

@RunWith(SpringRunner.class)
@WebMvcTest(SortingArrayJobSchedulerController.class)
public class JobSchedulerControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	@Qualifier("sortingArrayJobSchedulerServiceImpl")
	private JobSchedulerService jobSchedulerService;

	@Test
	public void test_GetAllJobs() throws Exception {
		SortingArrayJobResponse job = SortingArrayJobResponse.builder().jobId(1l)
				.status(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED).createdTime(LocalDateTime.now()).build();
		List<JobSchedulerResponse> response = new ArrayList<JobSchedulerResponse>();
		response.add(job);

		when(jobSchedulerService.fetchAllJobs()).thenReturn(response);

		mvc.perform(get("/api/job-scheduler/sorting-jobs").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].jobId", is(job.getJobId().intValue())));
	}

	@Test
	public void test_CreateAJob() throws Exception {
		SortingArrayJobRequest wrapper = new SortingArrayJobRequest(Arrays.array(1l, 4311l, 632l));

		when(jobSchedulerService.createJob(wrapper)).thenReturn(0l);

		mvc.perform(post("/api/job-scheduler/sorting-job").contentType(MediaType.APPLICATION_JSON)
				.content("{\"data\": [1, 4311, 632]}")).andExpect(status().isOk()).andExpect(jsonPath("$", is(0)));
	}

	@Test
	public void test_GetJobDetails() throws Exception {

		SortingArrayJobResponse response = SortingArrayJobResponse.builder().jobId(1l)
				.data(Arrays.array(1l, 2l, 5l, 3l)).status(JobSchedulerConstants.SORTING_JOB_STATUS_COMPLETED)
				.createdTime(LocalDateTime.now()).updatedTime(LocalDateTime.now().plusSeconds(1l))
				.durationMilliseconds(1000l).build();

		when(jobSchedulerService.findJobDetails(1l)).thenReturn(response);

		mvc.perform(get("/api/job-scheduler/sorting-job/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.jobId", is(response.getJobId().intValue())))
				.andExpect(jsonPath("$.status", is(response.getStatus())));
	}
}
