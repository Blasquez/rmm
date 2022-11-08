package com.ninjaone.backendinterviewproject.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyAddedException extends BackendInterviewProjectException{

	private static final long serialVersionUID = 1L;
	
	public EntityAlreadyAddedException(String message) {
		super(message, HttpStatus.CONFLICT);
	}
}
