package com.frederik.springboot.cruddemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UnterkategorieRestExceptionHandler {

    // add an exception handler for UnterkategorieNotFoundException
    @ExceptionHandler
    public ResponseEntity<UnterkategorieErrorResponse> handleException(UnterkategorieNotFoundException exc) {

	// create UnterkategorieErrorResponse
	UnterkategorieErrorResponse error = new UnterkategorieErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add another exception handler anything else
    @ExceptionHandler
    public ResponseEntity<UnterkategorieErrorResponse> handleException(Exception exc) {

	// create UnterkategorieErrorResponse
	UnterkategorieErrorResponse error = new UnterkategorieErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
