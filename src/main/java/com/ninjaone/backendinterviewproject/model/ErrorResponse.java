package com.ninjaone.backendinterviewproject.model;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {

	private final String title;
	  
	private final String message;
	
	private final Set<String> validations;
	
	private final int statusCode;
}
