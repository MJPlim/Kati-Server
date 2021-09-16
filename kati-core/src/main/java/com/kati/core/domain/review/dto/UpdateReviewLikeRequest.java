package com.kati.core.domain.review.dto;

import lombok.Getter;

@Getter
public class UpdateReviewLikeRequest {
	
	private Long reviewId;
	
	private boolean likeCheck;
}
