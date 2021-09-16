package com.kati.core.domain.review.exception;

public class NotFoundPageException extends IllegalArgumentException {
	public NotFoundPageException(ReviewExceptionMessage m) {
		super(m.getMessage());
	}

}
