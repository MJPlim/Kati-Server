package com.kati.core.domain.review.dto;

import com.kati.core.domain.review.domain.ReviewSummary;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadSummaryResponse {

    private Long foodId;
 
    private int oneCount;

    private int twoCount;

    private int threeCount;

    private int fourCount;

    private int fiveCount;

    private long sumRating;

    private float avgRating;
    
    private int reviewCount;
    
    private int reviewPageCount;
    
    public static ReadSummaryResponse of(ReviewSummary reviewSummary, int findReviewCount, int findReviewPageCount) {
    	return ReadSummaryResponse.builder()
    			.foodId(reviewSummary.getFood().getId())
    			.oneCount(reviewSummary.getOneCount())
    			.twoCount(reviewSummary.getTwoCount())
    			.threeCount(reviewSummary.getThreeCount())
    			.fourCount(reviewSummary.getFourCount())
    			.fiveCount(reviewSummary.getFiveCount())
    			.sumRating(reviewSummary.getSumRating())
    			.avgRating(reviewSummary.getAvgRating())
    			.reviewCount(findReviewCount)
    			.reviewPageCount(findReviewPageCount)
    			.build();
    }
    
    public static ReadSummaryResponse defaultSummary(Long foodId) {
    	return ReadSummaryResponse.builder()
    			.foodId(foodId)
    			.oneCount(0)
    			.twoCount(0)
    			.threeCount(0)
    			.fourCount(0)
    			.fiveCount(0)
    			.sumRating(0)
    			.avgRating(0)
    			.reviewCount(0)
    			.reviewPageCount(0)
    			.build();
    }
    
}
