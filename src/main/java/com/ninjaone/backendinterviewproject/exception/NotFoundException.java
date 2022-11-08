package com.ninjaone.backendinterviewproject.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BackendInterviewProjectException{

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

}
