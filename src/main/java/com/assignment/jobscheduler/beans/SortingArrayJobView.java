package com.assignment.jobscheduler.beans;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class SortingArrayJobView {
	private Long jobId;
	private Long[] data;
	private String status;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private Long durationMilliseconds;
}
