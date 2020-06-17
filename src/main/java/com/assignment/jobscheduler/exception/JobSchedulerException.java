package com.assignment.jobscheduler.exception;

public class JobSchedulerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobSchedulerException(String message, Object... arguments) {
		super(String.format(message, arguments));
	}
}
