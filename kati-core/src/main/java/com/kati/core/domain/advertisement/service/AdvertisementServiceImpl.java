package com.kati.core.domain.advertisement.service;

import com.kati.core.domain.advertisement.domain.AdvertisementFood;
import com.kati.core.domain.advertisement.exception.AlreadyExistsAdvertisementFoodException;
import com.kati.core.domain.advertisement.exception.NotFoundAdvertisementFoodException;
import com.kati.core.domain.advertisement.exception.SizeMoreThanListSizeException;
import com.kati.core.domain.advertisement.repository.AdvertisementRepository;
import com.kati.core.domain.food.domain.Food;
import com.kati.core.domain.food.service.FoodService;
import com.kati.core.domain.user.service.UserService;
import com.kati.core.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AdvertisementServiceImpl implements AdvertisementService {
    
    private final AdvertisementRepository advertisementRepository;
    private final FoodService foodService;

    @Transactional
    @Override
    public Set<AdvertisementFood> getRandomAdvertisementFoods(int size) {
        List<AdvertisementFood> advertisementFoods = this.advertisementRepository.findAll();
        HashSet<AdvertisementFood> randomAdvertisementFoods = new HashSet<>();
        this.getRandomIndices(size, advertisementFoods).forEach(i ->
                randomAdvertisementFoods.add(advertisementFoods.get(i).impressionUp()));
        return randomAdvertisementFoods;
    }

    private Set<Integer> getRandomIndices(int size, List<?> list) {
        if (size > list.size()) throw new SizeMoreThanListSizeException();
        Random random = new Random();
        Set<Integer> randomIndices = new HashSet<>();
        while (randomIndices.size() < size) randomIndices.add(random.nextInt(list.size()));
        return randomIndices;
    }

    @Transactional
    @Override
    public AdvertisementFood findById(Long id) {
        return this.advertisementRepository.findById(id)
                .orElseThrow(NotFoundAdvertisementFoodException::new)
                .impressionUp();
    }

    @Override
    public AdvertisementFood saveByFoodId(PrincipalDetails principalDetails, Long foodId) {
        Food food = this.foodService.findById(foodId);
        this.validateAlreadyExistsAdvertisementFood(food);
        return this.advertisementRepository.save(new AdvertisementFood(food));
    }

    private void validateAlreadyExistsAdvertisementFood(Food food) {
        if (this.advertisementRepository.existsByFood(food))
            throw new AlreadyExistsAdvertisementFoodException();
    }

}
