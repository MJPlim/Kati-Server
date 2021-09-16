package com.kati.core.domain.food.util;

import com.kati.core.domain.review.domain.Review;
import com.kati.core.domain.review.domain.ReviewStateType;
import com.kati.core.domain.food.domain.Food;

import java.util.Comparator;

public class SortByReviewRateAndDesc implements Comparator<Food> {
    @Override
    public int compare(Food o1, Food o2) {
        double o1Rate = o1.getReviewList().stream().filter(o -> o.getState().equals(ReviewStateType.NORMAL)).mapToInt(Review::getReviewRating).average().orElse(0);
        double o2Rate = o2.getReviewList().stream().filter(o -> o.getState().equals(ReviewStateType.NORMAL)).mapToInt(Review::getReviewRating).average().orElse(0);
        if (o1Rate > o2Rate) {
            return -1;
        } else if (o1Rate < o2Rate) {
            return 1;
        }
        return 0;
    }
}
