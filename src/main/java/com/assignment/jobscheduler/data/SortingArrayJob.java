package com.assignment.jobscheduler.data;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sorting_array")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name = "findAllJobs", query = "select s from SortingArrayJob s")
public class SortingArrayJob {

	@Id
	@GeneratedValue
	@Column(name = "job_id")
	private Long jobId;

	@Column(name = "data", nullable = true)
	private Long[] data;

	@Column(name = "status")
	private String status;

	@JsonDeserialize(using= LocalDateTimeDeserializer.class)
	@JsonSerialize(using= LocalDateTimeSerializer.class)
	@Column(name = "created_time")
	private LocalDateTime createdTime;

	@JsonDeserialize(using= LocalDateTimeDeserializer.class)
	@JsonSerialize(using= LocalDateTimeSerializer.class)
	@Column(name = "updated_time")
	private LocalDateTime updatedTime;

	@Column(name = "duration_ms")
	private Long durationMilliseconds;

	@PrePersist
	private void onCreate() {
		updatedTime = createdTime = LocalDateTime.now();
	}

	@PreUpdate
	private void onUpdate() {
		updatedTime = LocalDateTime.now();
		durationMilliseconds = Duration.between(createdTime, updatedTime).toMillis();
	}

}
