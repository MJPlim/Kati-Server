package com.kati.core.domain.advertisement.service;

import com.kati.core.domain.advertisement.domain.AdvertisementFood;

import java.util.Set;

public interface AdvertisementService {
    Set<AdvertisementFood> getRandomAdvertisementFoods(int size);

    AdvertisementFood saveByFoodId(Long foodId);

    AdvertisementFood findById(Long adId);
}
