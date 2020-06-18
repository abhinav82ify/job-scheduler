package com.assignment.jobscheduler.data;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class Job {

	@Id
	@GeneratedValue
	@Column(name = "job_id")
	private Long jobId;

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
		LocalDateTime currentTime = LocalDateTime.now();
		this.setCreatedTime(currentTime);
		this.setUpdatedTime(currentTime);
	}

	@PreUpdate
	private void onUpdate() {
		this.setUpdatedTime(LocalDateTime.now());
		this.setDurationMilliseconds(Duration.between(this.getCreatedTime(), this.getUpdatedTime()).toMillis());
	}
}
