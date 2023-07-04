package com.kantar.template.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kantar.template.response.ErrorResponse;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse noSuchElement(NoSuchElementException e) {
		return ErrorResponse.builder().message(e.getMessage()).build();
	}
}
