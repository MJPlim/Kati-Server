package com.kati.core.domain.favorite.service;

import com.kati.core.domain.favorite.dto.FavoriteResponse;
import com.kati.core.global.config.security.auth.PrincipalDetails;

import java.util.List;
import java.util.NoSuchElementException;

public interface FavoriteService {

    List<FavoriteResponse> getFavoriteFoodList(PrincipalDetails principalDetails);
    boolean addFavoriteFood(PrincipalDetails principalDetails, Long foodId) throws NoSuchElementException;
    void deleteFavoriteFood(PrincipalDetails principalDetails, Long foodId);
    boolean getFavoriteStateForSpecificFood(PrincipalDetails principalDetails, Long foodId);

}
