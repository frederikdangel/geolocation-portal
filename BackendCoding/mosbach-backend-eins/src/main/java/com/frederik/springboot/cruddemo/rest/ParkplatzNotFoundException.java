package com.frederik.springboot.cruddemo.rest;

public class ParkplatzNotFoundException extends RuntimeException {

    public ParkplatzNotFoundException() {
	// TODO Auto-generated constructor stub
    }

    public ParkplatzNotFoundException(String message) {
	super(message);
	// TODO Auto-generated constructor stub
    }

    public ParkplatzNotFoundException(Throwable cause) {
	super(cause);
	// TODO Auto-generated constructor stub
    }

    public ParkplatzNotFoundException(String message, Throwable cause) {
	super(message, cause);
	// TODO Auto-generated constructor stub
    }

    public ParkplatzNotFoundException(String message, Throwable cause, boolean enableSuppression,
	    boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
	// TODO Auto-generated constructor stub
    }

}
