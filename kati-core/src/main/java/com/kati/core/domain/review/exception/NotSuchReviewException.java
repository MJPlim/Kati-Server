package com.kati.core.domain.review.exception;

public class NotSuchReviewException extends IllegalArgumentException{

	public NotSuchReviewException(ReviewExceptionMessage m) {
		super(m.getMessage());
	}
}
