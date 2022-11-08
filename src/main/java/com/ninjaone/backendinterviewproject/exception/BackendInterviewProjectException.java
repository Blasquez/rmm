package com.ninjaone.backendinterviewproject.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BackendInterviewProjectException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private final String message;
	
	private final HttpStatus status;
	
}
