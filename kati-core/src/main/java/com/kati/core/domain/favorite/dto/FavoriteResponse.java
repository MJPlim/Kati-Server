package com.kati.core.domain.favorite.dto;

import com.kati.core.domain.food.dto.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FavoriteResponse {
    private final Long favoriteId;
    private final FoodResponse food;
}
