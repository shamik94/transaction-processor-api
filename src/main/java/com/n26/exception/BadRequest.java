package com.n26.exception;

public class BadRequest extends RuntimeException{
	public BadRequest(String message) {
		super(message);
	}
}
