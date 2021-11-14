package com.kati.core.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateReviewLikeRequest {
	
	private Long reviewId;
	
	private boolean likeCheck;
}
