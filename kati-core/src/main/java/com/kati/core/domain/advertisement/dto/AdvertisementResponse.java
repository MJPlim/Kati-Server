package com.kati.core.domain.advertisement.dto;

import com.kati.core.domain.advertisement.domain.AdvertisementFood;
import com.kati.core.domain.food.dto.FoodResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdvertisementResponse {

    private final Long id;
    private final Long impression;
    private final FoodResponse food;

    public static AdvertisementResponse from(AdvertisementFood advertisementFood) {
        return new AdvertisementResponse(
                advertisementFood.getId(),
                advertisementFood.getImpression(),
                FoodResponse.from(advertisementFood.getFood())
        );
    }

}
