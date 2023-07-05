package com.kantar.template.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
	private String message;
	private HttpStatus status;
	private Integer errorCode;
}
