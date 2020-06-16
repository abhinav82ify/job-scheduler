package com.assignment.jobscheduler.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="sorting_array")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(name="findAllJobs", query="select s from SortingArrayJob s")
public class SortingArrayJob {
	
	@Id
	@GeneratedValue
	@Column(name="job_id")
	private Long jobId;
	
	@Column(name="data", nullable=true)
	private Integer[] data;
	
	@Column(name="status")
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;
    
    @Column(name="duration_ms")
	private long durationMilliseconds;

    @PrePersist
    protected void onCreate() {
    	updatedTime = createdTime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	updatedTime = new Date();
    	durationMilliseconds = updatedTime.getTime()-createdTime.getTime();
    }
    
}
