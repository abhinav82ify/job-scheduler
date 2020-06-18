package com.assignment.jobscheduler.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
public class SortingArrayJob extends Job {

	@Column(name = "data", nullable = true)
	private Long[] data;

}
