package com.kati.core.domain.user.exception;

public class NotLoginException extends IllegalArgumentException{
	
	public NotLoginException(UserExceptionMessage m) {
		super(m.getMessage());
	}
}
