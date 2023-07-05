package com.kantar.template.exception;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

//	@ExceptionHandler(value = NoSuchElementException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	public ErrorResponse noSuchElement(NoSuchElementException e) {
//		return ErrorResponse.builder().message(e.getMessage()).errorCode(404).status(HttpStatus.NOT_FOUND).build();
//	}

	@ExceptionHandler(value = NoSuchElementException.class)
	public ProblemDetail onNoSuchElement(NoSuchElementException e) throws URISyntaxException {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		problemDetail.setInstance(new URI(request.getRequestURI()));
		problemDetail.setType(URI.create("http://api.employees.com/errors/not-found"));
		problemDetail.setTitle("Element Not Found");
		return problemDetail;
	}

	@ExceptionHandler(value = UsernameNotFoundException.class)
	public ProblemDetail onUsernameNotFoundException(UsernameNotFoundException e) throws URISyntaxException {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		problemDetail.setInstance(new URI(request.getRequestURI()));
		problemDetail.setType(URI.create("http://api.users.com/errors/username-not-found"));
		problemDetail.setTitle("User Not Found");
		return problemDetail;
	}
}
