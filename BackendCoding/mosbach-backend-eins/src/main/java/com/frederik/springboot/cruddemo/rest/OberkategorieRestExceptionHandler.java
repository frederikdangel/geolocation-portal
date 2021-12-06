package com.frederik.springboot.cruddemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OberkategorieRestExceptionHandler {

    // add an exception handler for OberkategorieNotFoundException
    @ExceptionHandler
    public ResponseEntity<OberkategorieErrorResponse> handleException(OberkategorieNotFoundException exc) {

	// create OberkategorieErrorResponse
	OberkategorieErrorResponse error = new OberkategorieErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // add another exception handler anything else
    @ExceptionHandler
    public ResponseEntity<OberkategorieErrorResponse> handleException(Exception exc) {

	// create OberkategorieErrorResponse
	OberkategorieErrorResponse error = new OberkategorieErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
		System.currentTimeMillis());

	// return it
	return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
