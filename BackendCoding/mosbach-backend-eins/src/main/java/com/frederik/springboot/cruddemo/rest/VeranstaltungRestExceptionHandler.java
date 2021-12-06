package com.frederik.springboot.cruddemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class VeranstaltungRestExceptionHandler {

    // add an exception handler for VeranstaltungNotFoundException
    @ExceptionHandler
    public ResponseEntity<VeranstaltungErrorResponse> handleException(VeranstaltungNotFoundException exc) {

	// create VeranstaltungErrorResponse
	VeranstaltungErrorResponse error = new VeranstaltungErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add another exception handler anything else
    @ExceptionHandler
    public ResponseEntity<VeranstaltungErrorResponse> handleException(Exception exc) {

	// create VeranstaltungErrorResponse
	VeranstaltungErrorResponse error = new VeranstaltungErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
