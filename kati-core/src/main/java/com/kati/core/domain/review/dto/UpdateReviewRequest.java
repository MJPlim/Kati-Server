package com.kati.core.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateReviewRequest {
	private Long reviewId;
	private int reviewRating;
	private String reviewDescription;
}
