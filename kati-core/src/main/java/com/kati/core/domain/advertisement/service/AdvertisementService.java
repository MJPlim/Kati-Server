package com.kati.core.domain.advertisement.service;

import com.kati.core.domain.advertisement.domain.AdvertisementFood;
import com.kati.core.global.config.security.auth.PrincipalDetails;

import java.util.Set;

public interface AdvertisementService {
    Set<AdvertisementFood> getRandomAdvertisementFoods(int size);

    AdvertisementFood saveByFoodId(PrincipalDetails principalDetails, Long foodId);

    AdvertisementFood findById(Long adId);
}
