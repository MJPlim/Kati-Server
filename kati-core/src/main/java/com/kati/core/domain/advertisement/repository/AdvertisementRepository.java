package com.kati.core.domain.advertisement.repository;

import com.kati.core.domain.advertisement.domain.AdvertisementFood;
import com.kati.core.domain.food.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<AdvertisementFood, Long> {

    boolean existsByFood(Food food);

}
