package com.assignment.jobscheduler.exception;

public class InvalidJobException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidJobException(String message, Object... arguments) {
		super(String.format(message, arguments));
	}
}
