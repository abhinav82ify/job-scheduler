package com.assignment.jobscheduler.beans;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class SortingArrayJobResponse extends JobSchedulerResponse {
	private Long[] data;

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends JobSchedulerResponse {
		private Long[] data;

		public Builder jobId(Long jobId) {
			this.jobId = jobId;
			return this;
		}

		public Builder data(Long[] data) {
			this.data = data;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public Builder createdTime(LocalDateTime createdTime) {
			this.createdTime = createdTime;
			return this;
		}

		public Builder updatedTime(LocalDateTime updatedTime) {
			this.updatedTime = updatedTime;
			return this;
		}

		public Builder durationMilliseconds(Long durationMilliseconds) {
			this.durationMilliseconds = durationMilliseconds;
			return this;
		}

		public SortingArrayJobResponse build() {
			SortingArrayJobResponse sortingArrayJobView = new SortingArrayJobResponse();
			sortingArrayJobView.jobId = this.jobId;
			sortingArrayJobView.data = this.data;
			sortingArrayJobView.status = this.status;
			sortingArrayJobView.createdTime = this.createdTime;
			sortingArrayJobView.updatedTime = this.updatedTime;
			sortingArrayJobView.durationMilliseconds = this.durationMilliseconds;
			return sortingArrayJobView;
		}
	}
}
