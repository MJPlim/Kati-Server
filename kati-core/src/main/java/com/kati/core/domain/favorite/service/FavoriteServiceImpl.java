package com.kati.core.domain.favorite.service;

import com.kati.core.domain.favorite.domain.Favorite;
import com.kati.core.domain.favorite.dto.FavoriteResponse;
import com.kati.core.domain.user.exception.UserExceptionMessage;
import com.kati.core.domain.user.repository.UserRepository;
import com.kati.core.domain.favorite.repository.FavoriteRepository;
import com.kati.core.domain.food.domain.Food;
import com.kati.core.domain.food.dto.FoodResponse;
import com.kati.core.domain.food.exception.FoodExceptionMessage;
import com.kati.core.domain.food.exception.NoFoodException;
import com.kati.core.domain.food.repository.FoodRepository;
import com.kati.core.domain.user.domain.User;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FavoriteServiceImpl implements FavoriteService{
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public FavoriteServiceImpl(UserRepository userRepository, FavoriteRepository favoriteRepository, FoodRepository foodRepository) {
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    public List<FavoriteResponse> getFavoriteFoodList(PrincipalDetails principalDetails) {
        User user = this.userRepository.findByEmail(principalDetails.getUsername())
                                       .orElseThrow(() ->
                                               new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));
        List<Favorite> favoriteList = this.favoriteRepository.findAllByUser(user);

        List<FavoriteResponse> responses = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            FavoriteResponse response = FavoriteResponse.builder()
                                                        .favoriteId(favorite.getId())
                                                        .food(FoodResponse.from(favorite.getFood()))
                                                        .build();
            responses.add(response);
        }
        return responses;
    }

    @Transactional
    @Override
    public boolean addFavoriteFood(PrincipalDetails principalDetails, Long foodId) throws NoSuchElementException{
        Food food = this.foodRepository.findById(foodId).orElseThrow(NoSuchElementException::new);
        User user = this.userRepository.findByEmail(principalDetails.getUsername())
                                       .orElseThrow(NoSuchElementException::new);

        if(this.favoriteRepository.existsByUserAndFood(user, food)){
            return false;
        }else{
            this.favoriteRepository.save(Favorite.builder()
                                                 .user(user)
                                                 .food(food)
                                                 .build());
            return true;
        }
    }

    @Transactional
    @Override
    public void deleteFavoriteFood(PrincipalDetails principalDetails, Long foodId){
        Food food = this.foodRepository.findById(foodId).orElseThrow(() -> new NoFoodException(FoodExceptionMessage.NO_FOOD_EXCEPTION_MESSAGE));
        User user = this.userRepository.findByEmail(principalDetails.getUsername())
                                       .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));

        this.favoriteRepository.deleteByUserAndFood(user, food);
    }

    @Override
    public boolean getFavoriteStateForSpecificFood(PrincipalDetails principalDetails, Long foodId) {
        Food food = this.foodRepository.findById(foodId).orElseThrow(() -> new NoFoodException(FoodExceptionMessage.NO_FOOD_EXCEPTION_MESSAGE));
        User user = this.userRepository.findByEmail(principalDetails.getUsername())
                                       .orElseThrow(() -> new UsernameNotFoundException(UserExceptionMessage.USERNAME_NOT_FOUND_EXCEPTION_MESSAGE.getMessage()));

        return this.favoriteRepository.existsByUserAndFood(user, food);
    }

}
