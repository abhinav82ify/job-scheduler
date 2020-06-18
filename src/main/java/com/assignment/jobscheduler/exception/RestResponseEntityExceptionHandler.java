package com.assignment.jobscheduler.exception;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { JobSchedulerException.class })
	protected ResponseEntity<Object> handleJobSchedulerException(JobSchedulerException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE)
				.body(Collections.singletonMap("error", exception.getMessage()));
	}

	@ExceptionHandler(value = { InvalidJobException.class })
	protected ResponseEntity<Object> handleInvalidJobException(InvalidJobException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(Collections.singletonMap("error", exception.getMessage()));
	}

	@ExceptionHandler(value = { BadRequestException.class })
	protected ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Collections.singletonMap("error", exception.getMessage()));
	}

}
