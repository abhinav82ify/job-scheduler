package com.assignment.jobscheduler.exception;

public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message, Object... arguments) {
		super(String.format(message, arguments));
	}
}
