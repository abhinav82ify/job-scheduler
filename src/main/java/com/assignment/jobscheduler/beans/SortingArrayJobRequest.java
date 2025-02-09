package com.assignment.jobscheduler.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortingArrayJobRequest extends JobSchedulerRequest {
	private Long[] data;
}
