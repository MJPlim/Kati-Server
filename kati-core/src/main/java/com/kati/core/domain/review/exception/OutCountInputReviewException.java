package com.kati.core.domain.review.exception;

public class OutCountInputReviewException extends IllegalArgumentException{

	public OutCountInputReviewException(ReviewExceptionMessage m) {
		super(m.getMessage());
	}
}
