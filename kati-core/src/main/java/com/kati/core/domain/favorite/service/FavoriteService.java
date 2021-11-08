package com.kati.core.domain.favorite.service;

import com.kati.core.domain.favorite.dto.FavoriteResponse;
import com.kati.core.global.config.security.auth.PrincipalDetails;

import java.util.List;
import java.util.NoSuchElementException;

public interface FavoriteService {
    List<FavoriteResponse> findAll(PrincipalDetails principalDetails);
    void add(PrincipalDetails principalDetails, Long foodId);
    void delete(PrincipalDetails principalDetails, Long foodId);
    boolean getFavoriteStateForSpecificFood(PrincipalDetails principalDetails, Long foodId);
}
