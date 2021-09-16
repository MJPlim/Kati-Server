package com.kati.core.domain.review.service;

import com.kati.core.domain.review.domain.Review;
import com.kati.core.domain.review.domain.ReviewLike;
import com.kati.core.domain.review.dto.*;
import com.kati.core.global.config.security.auth.PrincipalDetails;

import java.util.List;

public interface ReviewService {

	public void saveReview(PrincipalDetails principal, CreateReviewRequest dto);

	public List<ReadReviewResponse> findReview(Long foodId, Integer pageNum);

	public List<ReadReviewResponse> findReviewByUserId(PrincipalDetails principal, Integer pageNo);

	public List<ReadReviewResponse> findReviewByUserIdANDFoodID(PrincipalDetails principal, Long foodId, Integer pageNum);

	public Review changeReview(PrincipalDetails principal, UpdateReviewRequest dto);

	public ReviewLike changeReviewLike(PrincipalDetails principal, UpdateReviewLikeRequest dto);

	public Review removeReview(PrincipalDetails principal, DeleteReviewRequest dto);
	
	public List<ReviewRankingResponse> rankedReview();
	
	public int findUserReviewCount(PrincipalDetails principal);

	public ReadSummaryResponse findReviewSummary(Long foodId);

	public ReadReviewIdResponse findReviewByReviewId(PrincipalDetails principal, Long reviewId);

}
