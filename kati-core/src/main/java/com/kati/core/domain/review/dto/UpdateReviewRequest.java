package com.kati.core.domain.review.dto;

import lombok.Getter;

@Getter
public class UpdateReviewRequest {
	private Long reviewId;
	private int reviewRating;
	private String reviewDescription;
}
