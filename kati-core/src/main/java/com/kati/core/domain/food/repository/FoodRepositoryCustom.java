package com.kati.core.domain.food.repository;

import com.kati.core.domain.food.domain.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodRepositoryCustom {
    Page<Food> search(String sortElement, String category, String foodName, String manufacturerName
            , List<String> allergyList, String order, Pageable pageable);

    Page<Food> findByWideCategory(String[] categories, Pageable pageable);
}
