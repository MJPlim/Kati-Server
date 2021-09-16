package com.kati.core.domain.review.exception;

public class NotApproachReviewException extends IllegalArgumentException{
	public NotApproachReviewException(ReviewExceptionMessage m) {
		super(m.getMessage());
	}
}
