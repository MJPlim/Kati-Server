package com.kati.core.domain.favorite.service;

import com.kati.core.domain.favorite.domain.Favorite;
import com.kati.core.domain.favorite.dto.FavoriteResponse;
import com.kati.core.domain.favorite.exception.AlreadyExistsFavoriteException;
import com.kati.core.domain.favorite.repository.FavoriteRepository;
import com.kati.core.domain.food.domain.Food;
import com.kati.core.domain.food.dto.FoodResponse;
import com.kati.core.domain.food.exception.NotFoundFoodException;
import com.kati.core.domain.food.service.FoodService;
import com.kati.core.domain.user.domain.User;
import com.kati.core.domain.user.service.UserService;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final UserService userService;
    private final FoodService foodService;
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<FavoriteResponse> findAll(PrincipalDetails principalDetails) {
        return this.favoriteRepository
                .findAllByUser(this.userService.getUserByPrincipalDetails(principalDetails))
                .stream()
                .map(FavoriteResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void add(PrincipalDetails principalDetails, Long foodId) throws NoSuchElementException {
        Food food = this.foodService.findById(foodId);
        User user = this.userService.getUserByPrincipalDetails(principalDetails);
        validateAlreadyExistsFavorite(food, user);
        this.favoriteRepository.save(new Favorite(user, food));
    }

    private void validateAlreadyExistsFavorite(Food food, User user) {
        if (this.favoriteRepository.existsByUserAndFood(user, food)) throw new AlreadyExistsFavoriteException();
    }

    @Transactional
    @Override
    public void delete(PrincipalDetails principalDetails, Long foodId) {
        this.favoriteRepository.deleteByUserAndFood(
                this.userService.getUserByPrincipalDetails(principalDetails),
                this.foodService.findById(foodId));
    }

    @Override
    public boolean getFavoriteStateForSpecificFood(PrincipalDetails principalDetails, Long foodId) {
        return this.favoriteRepository.existsByUserAndFood(
                this.userService.getUserByPrincipalDetails(principalDetails),
                this.foodService.findById(foodId));
    }

}
