package com.hexaware.usermicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the Asset Management System.
 * 
 * This class uses Spring's @ControllerAdvice to handle exceptions
 * thrown by controllers across the application in a centralized manner.
 * 
 * It intercepts various exceptions (custom and system) and returns
 * appropriate HTTP responses with meaningful error messages and status codes.
 * 
 * Improves maintainability by separating error handling logic from controller code.
 * 
 * Author: Yakesh
 * @version 1.0
 * @since 2025-05-28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<String> dataNotFoundExceptionHandler(DataNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataAlreadyExistException.class)
	public ResponseEntity<String> dataAlreadyExistExceptionHandler(DataAlreadyExistException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> dxceptionHandler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
