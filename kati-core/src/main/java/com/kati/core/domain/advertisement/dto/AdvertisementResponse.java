package com.kati.core.domain.advertisement.dto;

import com.kati.core.domain.food.dto.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdvertisementResponse {
    private final Long id;
    private final FoodResponse food;
}
