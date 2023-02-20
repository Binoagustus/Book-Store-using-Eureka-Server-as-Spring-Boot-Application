package com.bridgeLabz.cartmicroservice.exception;

public class BookStoreException extends RuntimeException{

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BookStoreException(String message) {
		this.message = message;
	}	
}
