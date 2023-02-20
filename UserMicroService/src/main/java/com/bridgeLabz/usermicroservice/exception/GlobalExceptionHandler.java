package com.bridgeLabz.usermicroservice.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bridgeLabz.usermicroservice.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = BookStoreException.class)
	public Response handleUserException(BookStoreException exception) {
		return new Response(exception.getMessage());
	}
}
