package com.frederik.springboot.cruddemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdresseRestExceptionHandler {

    // add an exception handler for AdresseNotFoundException
    @ExceptionHandler
    public ResponseEntity<AdresseErrorResponse> handleException(AdresseNotFoundException exc) {

	// create AdresseErrorResponse
	AdresseErrorResponse error = new AdresseErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add another exception handler anything else
    @ExceptionHandler
    public ResponseEntity<AdresseErrorResponse> handleException(Exception exc) {

	// create AdresseErrorResponse
	AdresseErrorResponse error = new AdresseErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
