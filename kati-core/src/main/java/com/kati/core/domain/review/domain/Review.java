package com.kati.core.domain.review.domain;

import com.kati.core.domain.review.dto.CreateReviewRequest;
import com.kati.core.domain.food.domain.Food;
import com.kati.core.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Table(name = "review")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Review {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")				
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "food_id")				
	private Food food;
	
	@BatchSize(size = 200)
	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReviewLike> reviewLikeList = new ArrayList<>();
	
	@Column(name = "review_rating", nullable = false)
	private int reviewRating;
	
	@Lob
	@Column(name = "review_description")
	private String reviewDescription;
	
	@CreationTimestamp
	@Column(name = "review_created_date")
	private Timestamp reviewCreatedDate;
	
	@UpdateTimestamp
	@Column(name = "review_modified_date")
	private Timestamp reviewModifiedDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "review_state", nullable = false)
	private ReviewStateType state;
	
	@Builder(access = AccessLevel.PRIVATE)
	private Review(User user, Food food, int reviewRating, String reviewDescription, ReviewStateType state) {
		this.user = user;
		this.food = food;
		this.reviewRating = reviewRating;
		this.reviewDescription = reviewDescription;
		this.state = state;
	}

	public static Review of(User user, Food food, CreateReviewRequest dto) {
		return Review.builder()
				.user(user)
				.food(food)
				.reviewRating(dto.getReviewRating())
				.reviewDescription(dto.getReviewDescription())
				.state(ReviewStateType.NORMAL)
				.build();
	}
	
	public void reviewUpdate(int reviewRating, String reviewDescription) {
		this.reviewRating = reviewRating;
		this.reviewDescription = reviewDescription;
	}
	
	public void reviewStateUpdate(ReviewStateType state) {
		this.state = state;
	}
	
	public int reviewLikeCount() {
		return this.reviewLikeList.size();
	}
	public ReviewLike checkLike(Long reviewId, Long userId) {
		for(ReviewLike rl : getReviewLikeList()) {
			if (rl.getReview().getId().equals(reviewId) && rl.getUserId().equals(userId))
				return rl;	
		}
		return null;

	}

}
