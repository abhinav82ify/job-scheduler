package com.assignment.jobscheduler.data;

import java.util.Date;

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
	private Integer[] data;
	private String status;
    private Date createdTime;
    private Date updatedTime;
	private long durationMilliseconds;
}
