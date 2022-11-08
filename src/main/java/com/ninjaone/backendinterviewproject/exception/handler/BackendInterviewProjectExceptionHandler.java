package com.ninjaone.backendinterviewproject.exception.handler;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ninjaone.backendinterviewproject.exception.BackendInterviewProjectException;
import com.ninjaone.backendinterviewproject.exception.EntityAlreadyAddedException;
import com.ninjaone.backendinterviewproject.exception.NotFoundException;
import com.ninjaone.backendinterviewproject.model.ErrorResponse;

@RestControllerAdvice
public class BackendInterviewProjectExceptionHandler {

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
		Set<String> validations = new HashSet<>();
		exception.getAllErrors().stream().forEach(error -> validations.add(error.getDefaultMessage()));
		return ErrorResponse.builder().validations(validations)
				                      .message("Required fields")
									  .title(HttpStatus.BAD_REQUEST.name())
				                      .statusCode(HttpStatus.BAD_REQUEST.value())
				                      .build();
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(EntityAlreadyAddedException.class)
	public ErrorResponse handleDeviceAlreadyAddedException(final BackendInterviewProjectException exception) {
		return ErrorResponse.builder().message(exception.getMessage())
				                      .title(exception.getStatus().name())
				                      .statusCode(exception.getStatus().value())
				                      .build();
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorResponse handleDeviceNotFoundException(final BackendInterviewProjectException exception) {
		return ErrorResponse.builder().message(exception.getMessage())
				                      .title(exception.getStatus().name())
				                      .statusCode(exception.getStatus().value())
				                      .build();
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(URISyntaxException.class)
	public ErrorResponse handleURISyntaxException(final URISyntaxException exception) {
		return ErrorResponse.builder().message(exception.getMessage())
				                      .title(HttpStatus.INTERNAL_SERVER_ERROR.name())
				                      .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				                      .build();
	}
	
}
