package com.kati.core.domain.food.dto;

import com.kati.core.domain.review.domain.Review;
import com.kati.core.domain.review.domain.ReviewStateType;
import com.kati.core.domain.food.domain.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FoodResponse {

    private final Long foodId;
    private final String foodName;
    private final String category;
    private final String manufacturerName;
    private final String foodImageAddress;
    private final String foodMeteImageAddress;
    private final int reviewCount;
    private final String reviewRate;

    public static FoodResponse from(Food food) {
        return FoodResponse.builder()
                .foodId(food.getId())
                .foodName(food.getFoodName())
                .category(food.getCategory())
                .manufacturerName(food.getManufacturerName())
                .foodImageAddress(food.getFoodImage().getFoodImageAddress())
                .foodMeteImageAddress(food.getFoodImage().getFoodMeteImageAddress())
                .reviewCount((int)food.getReviewList().stream().filter(f -> f.getState().equals(ReviewStateType.NORMAL)).count())
                .reviewRate(String.format("%.2f",
                        food.getReviewList().stream().filter(f -> f.getState().equals(ReviewStateType.NORMAL))
                                .mapToInt(Review::getReviewRating).average().orElse(0)))
                .build();
    }

}
