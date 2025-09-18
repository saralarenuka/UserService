package com.api.exception;

public class UserNameNotFoundException extends RuntimeException {

	public UserNameNotFoundException(String msg) {
         super(msg);
	}
}
