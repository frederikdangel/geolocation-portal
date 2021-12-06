package com.frederik.springboot.cruddemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParkplatzRestExceptionHandler {

    // add an exception handler for ParkplatzNotFoundException
    @ExceptionHandler
    public ResponseEntity<ParkplatzErrorResponse> handleException(ParkplatzNotFoundException exc) {

	// create ParkplatzErrorResponse
	ParkplatzErrorResponse error = new ParkplatzErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add another exception handler anything else
    @ExceptionHandler
    public ResponseEntity<ParkplatzErrorResponse> handleException(Exception exc) {

	// create ParkplatzErrorResponse
	ParkplatzErrorResponse error = new ParkplatzErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
