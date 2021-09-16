package com.kati.core.domain.review.repository;

import com.kati.core.domain.review.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long>{
	@Query(value = "Select rl from ReviewLike rl where rl.review.id =:reviewId and rl.userId = :userId")
	public ReviewLike findByUserId(@Param("reviewId")Long reviewId, @Param("userId")Long userId);
	
	@Query(value = "Select count(rl) from ReviewLike rl where rl.review.id =:reviewId")
	public int findReviewLikeCountByReview(@Param("reviewId")Long reviewId);
	
	@Query(value = "Select rl from ReviewLike rl where rl.review.id =:reviewId and rl.userId = :userId")
	public ReviewLike checkLikeByReview(@Param("reviewId")Long reviewId, @Param("userId")Long userId);
}
