package com.assignment.jobscheduler.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortingArrayJobRepository extends JpaRepository<SortingArrayJob, Long>{

}
