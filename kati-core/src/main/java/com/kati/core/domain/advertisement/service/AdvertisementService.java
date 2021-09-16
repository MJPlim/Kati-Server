package com.kati.core.domain.advertisement.service;

import com.kati.core.domain.advertisement.dto.AdvertisementResponse;
import com.kati.core.domain.food.dto.FoodDetailResponse;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface AdvertisementService {
    ArrayList<AdvertisementResponse> getAdvertisementFoodList();

    boolean selectAdvertisement(Long id1, Long id2, Long id3) throws NoSuchElementException;

    FoodDetailResponse getFoodDetailForAdvertisement(Long adId);
}
